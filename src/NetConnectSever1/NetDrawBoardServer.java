package NetConnectSever1;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
/**
 * ���续�������V1.0
 * ����������-->�ȴ��ͻ��˵�����-->������Ӧ�Ĳ���
 * @author 
 *
 */
public class NetDrawBoardServer extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;

	public NetDrawBoardServer() {
		//��������
		this.setSize(400, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		this.setVisible(true);
		g=this.getGraphics();
	}
	//����������
	public void setServer(int port) {
		try {
			//�������������ȴ��ͻ��˽���
			ServerSocket sever=new ServerSocket(port);
			System.out.println("�����������ɹ���");
			//�ȴ��ͻ�������
			Socket client=sever.accept();//clientΪ�ͻ��˶���
			System.out.println("�ͻ������ӳɹ���");			
		   //��ȡ�ͻ��˵����������
         	java.io.OutputStream out=client.getOutputStream();
         	java.io.InputStream input=client.getInputStream();
         	Input =new DataInputStream(input);
         	Out =new DataOutputStream(out);
         	while(true){
         		System.out.println("�ȴ��ͻ��˷���������.....");
         		int x1=Input.readInt();
         		int y1=Input.readInt();
         		int x2=Input.readInt();
         		int y2=Input.readInt();
         		System.out.println("���ܵ�����Ϊ��x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
         		g.drawLine(x1, y1, x2, y2);
         	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		NetDrawBoardServer sever=new NetDrawBoardServer();
		sever.setServer(8888);
	}
}
