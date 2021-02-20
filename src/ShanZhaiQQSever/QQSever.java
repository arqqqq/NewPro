package ShanZhaiQQSever;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author ����
 * ɽկQQ������V1.0
 *1����Ҫ����һ��������ʵ�����������
 *ʵ��һ���ͻ�����һ���������໥������Ϣ������˵�������û�֮���໥������Ϣ
 */
public class QQSever extends JFrame{
    private sendTools tools;
    private JTextArea Textshow;
	//ͨ����������������
	public QQSever() {
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
				System.out.println("���͵���ϢΪ��"+Msg);
				if(!Msg.equals("")) {
					tools.sendMsg(Msg);
					Textshow.append("��������  "+Msg+"\r\n");
					System.out.println("��Ϣ���ͳɹ���");
					Textwrite.setText("");
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
			//�ȴ��ͻ����Ľ���
			Socket client=sever.accept();
			System.out.println("�ͻ�������ɹ���");
			//��ȡ�ͻ��������������
			tools =new sendTools(client,Textshow);
			//�߳�����
			tools.start();
		}catch(Exception e) {
			System.out.println("����������ʧ��");
			e.printStackTrace();
		}
		
		
	}
	
	
	//������
	public static void main(String[] args) {
		QQSever QQ=new QQSever();
		QQ.setSever(8888);
	}
}
