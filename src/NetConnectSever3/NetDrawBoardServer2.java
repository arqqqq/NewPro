package NetConnectSever3;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
public class NetDrawBoardServer2 extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;

	public NetDrawBoardServer2() {
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
         		//���Ȼ�ȡ��Ƶ�Ŀ�͸�
         		int width =Input.readInt();
         		int hight=Input.readInt();
         		//����һ���г��Ϳ��BufferImage����
         		BufferedImage buff=new BufferedImage(width, hight, BufferedImage.TYPE_4BYTE_ABGR);
         		for(int row=0;row<width;row++) {
         			for(int lie=0;lie<hight;lie++) {
         				int rgb=Input.readInt();
         				buff.setRGB(row, lie, rgb);
         			}
         		}

         		g.drawImage(buff, 0, 0, this.getWidth(), this.getHeight(), null);
         	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		NetDrawBoardServer2 sever=new NetDrawBoardServer2();
		sever.setServer(8888);
	}
}
