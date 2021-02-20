package ShanZhaiQQSeverV2;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class sendToolsV2 extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private JTextArea text;
	public sendToolsV2(Socket client,JTextArea textshow) {
		this.text=textshow;
		try {
			InputStream dins=client.getInputStream();
			OutputStream dous=client.getOutputStream();
			in=new DataInputStream(dins);
			out=new DataOutputStream(dous);
		}catch(Exception ed) {
			System.out.println("����ʧ��");
			ed.printStackTrace();
		}
	}
	
	//run��������ȥ��ȡ��Ϣ
	//�ı���Ϣ����Э�飺�Ƚ��ܱ���ͷ��byte���ͣ�-->���ݱ���ͷ�ж����ݰ�����-->�����String��Ӧ�ı���ͷ-->����String��ȡ��ʽ
	//(��-->���ȶ�ȡ�ֽ�����ĳ���length-->����length����byte[]-->��ȡbyte[]-->ת����String-->��ʾ��JTextArea)
	public void run() {
		//�ȴ���Ϣ����
		while(true){
			try {
				System.out.println("�ȴ��ͻ�������Ϣ.....");
				//��ȡ�ɹ��󣬽������Ϣ��ʾ����������
				byte type=in.readByte();
				if(type==3) {
					//��ȡString��Ϣ
					int length =in.readInt();
					byte[] msg=new byte[length];
					in.read(msg);
					String Msg=new String(msg);
					text.append("�ͻ���:"+Msg+"\r\n");
				}
			}catch(Exception ed){
				ed.printStackTrace();
			}
		}
	}
	//�����ı���Ϣ��Э�鲻�� ����ͷ-->����-->�ַ�����
	public void sendMsg(String msg) {
		try {
			byte[] Msg=msg.getBytes();
			int len=Msg.length;
			out.writeByte(3);
			out.writeInt(len);
			out.write(Msg);
			System.out.println("���ݷ��ͳɹ�");
		}catch(Exception ed) {
			System.out.println("���ݷ���ʧ��");
			ed.printStackTrace();
		}
	}
}
