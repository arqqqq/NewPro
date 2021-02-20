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
			System.out.println("创建失败");
			ed.printStackTrace();
		}
	}
	
	//run方法用于去收取信息
	//文本信息接收协议：先接受报文头（byte类型）-->根据报文头判断数据包类型-->如果是String对应的报文头-->则用String读取方式
	//(即-->首先读取字节数组的长度length-->根据length创建byte[]-->读取byte[]-->转换成String-->显示到JTextArea)
	public void run() {
		//等待信息传入
		while(true){
			try {
				System.out.println("等待客户机的消息.....");
				//读取成功后，将这个消息显示到服务器端
				byte type=in.readByte();
				if(type==3) {
					//读取String信息
					int length =in.readInt();
					byte[] msg=new byte[length];
					in.read(msg);
					String Msg=new String(msg);
					text.append("客户端:"+Msg+"\r\n");
				}
			}catch(Exception ed){
				ed.printStackTrace();
			}
		}
	}
	//发送文本消息，协议不变 报文头-->长度-->字符数组
	public void sendMsg(String msg) {
		try {
			byte[] Msg=msg.getBytes();
			int len=Msg.length;
			out.writeByte(3);
			out.writeInt(len);
			out.write(Msg);
			System.out.println("数据发送成功");
		}catch(Exception ed) {
			System.out.println("数据发送失败");
			ed.printStackTrace();
		}
	}
}
