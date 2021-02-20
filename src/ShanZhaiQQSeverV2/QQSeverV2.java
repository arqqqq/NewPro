package ShanZhaiQQSeverV2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author ����
 * ɽկQQ������V2.0
 *ʵ�ֶ���ͻ�����������Ľ���
 *������һ���ͻ���Client-->������һ���̣߳����б�ʶ�����̣߳�-->�����߳�-->������̶߳�����������б���
 */
public class QQSeverV2 extends JFrame{
    private sendToolsV2 tools;
    private JTextArea Textshow;
    //����һ���̶߳���
    private ArrayList<sendToolsV2> clientMsg=new ArrayList<sendToolsV2>();
	//ͨ����������������
	public QQSeverV2() {
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("ɽկQQ��������");
		FlowLayout flow=new FlowLayout();
		this.setLayout(flow);
		//������
		JButton btn=new JButton("����");
		//�༭�����ı���
		final JTextField Textwrite=new JTextField(40);
		//��ʾ�ı��Ķ����ı�����
		Textshow=new JTextArea(20,40);
		//���
		this.add(btn);
		this.add(Textwrite);
		this.add(Textshow);
		//��JButton��Ӽ�����
		btn.addActionListener(new ActionListener() {
			
			@Override
			//��갴�£����ı���Ϣ���͸��ͻ��ˣ���������ı���,����ʾ�������ı���
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String Msg=Textwrite.getText();				
				if(!Msg.equals("")) {
					Textshow.append("��������  "+Msg+"\r\n");
					Textwrite.setText("");
					System.out.println("���͵���ϢΪ��"+Msg);
					for(int i=0;i<clientMsg.size();i++) {
						clientMsg.get(i).sendMsg(Msg);
						System.out.println("��Ϣ���ͳɹ���");						
					}
				}
			}
		});
		this.setVisible(true);
	}
	
	
	
	public void setSever(int port) {
		 //����������-->�ȴ��ͻ��˵Ľ���-->�ͻ��˽�����ȡ�ͻ��˵����������-->��ŵ�һ�������б��� 
		//һ���ͻ��˽���������������ڷ������������һ�����������˵������һ����ͨ��·��������ָһ���߳�
		try {
			//����һ��������
			ServerSocket sever=new ServerSocket(port);
			System.out.println("QQ�����������ɹ���");
			while(true) {
				//�ȴ��ͻ����Ľ���
				System.out.println("�ȴ��ͻ����Ľ���");
				Socket client=sever.accept();
				System.out.println("�ͻ�������ɹ���");
				//��ȡ�ͻ��������������
				tools =new sendToolsV2(client,Textshow);
				//�߳�����
				tools.start();
				clientMsg.add(tools);
			}
		}catch(Exception e) {
			System.out.println("����������ʧ��");
			e.printStackTrace();
		}
		
		
	}
	
	
	//������
	public static void main(String[] args) {
		QQSeverV2 QQ=new QQSeverV2();
		QQ.setSever(8888);
	}
}
