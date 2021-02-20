package NetConnectSever2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
/**
 * ���续�������V2.0
 * @author 
 *
 */
public class NetDrawBoardServer1 extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;
    private static int color=0;
	public NetDrawBoardServer1() {
		//��������
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("���续�������������V2.0");
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
         		//���Ƚ���colorֵ��tagֵ
         		color=Input.readInt();      		
         		int tag=Input.read();
         		System.out.println("��ʶ����ֵΪ"+tag);
         		switch (tag) {
         		   
         		case 1:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("�������ͣ���ֱ��-->���ܵ�����Ϊ��x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.drawLine(x1, y1, x2, y2);
         			break;
         		}
         		case 2:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("�������ͣ���Բ-->���ܵ�����Ϊ��x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.fillOval(x1, y1, x2-x1, y2-y1);
         			break;
         		}
         		case 3:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("�������ͣ�������-->���ܵ�����Ϊ��x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.fillRect(x1, y1, x2-x1, y2-y1);
         			break;
         		}
         		case 4:{
         			int x3=Input.readInt();
             		int y3=Input.readInt();
             		System.out.println("�������ͣ�����ʻ�-->���ܵ�����Ϊ��x3:"+x3+"y3:"+y3);
             		g.setColor(new Color(color));
             		g.fillOval(x3, y3, 10,10);
         			break;
         		}
         		case 5:{
                    g.clearRect(0, 0, this.getWidth(), this.getHeight());
                    System.out.println("�������ͣ���ս���");
         			break;
         		}
				default:
					   break;
				}
         		
         	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		NetDrawBoardServer1 sever=new NetDrawBoardServer1();
		sever.setServer(9999);
	}
}
