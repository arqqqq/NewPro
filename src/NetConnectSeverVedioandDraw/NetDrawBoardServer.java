package NetConnectSeverVedioandDraw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 * ���续�������V1.0
 * ����������-->�ȴ��ͻ��˵�����-->������Ӧ�Ĳ���
 * ��Ƶ�����ջ�-->�ڴ�����ֵ������Ż�-->
 * ���̣�������ImageIO����BufferedImage����ת����jpg�ĸ�ʽ-->��ȡ���jpg��ʽ�ļ���byte����-->��������鷢��������
 * -->�����������������-->Ȼ����ImageIOinputstreamת����BufferImage����-->����ڷ����������ϻ�����
 * @author ����
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
			OutputStream out=client.getOutputStream();
			InputStream input=client.getInputStream();
         	Input =new DataInputStream(input);
         	Out =new DataOutputStream(out);
         	//��������
         	while(true) {
         		System.out.println("�ȴ����ݵ�����");
         		//���ȶ��ֽ������length-->Ȼ���ٶ��ֽ�����
         		int length=Input.readInt();
         		byte[] imagedata=new byte[length];
         		Input.read(imagedata);
         		//��������Imagedata�ֽ�����ת����BufferedImage����
         		BufferedImage img=getDecompressedImage(imagedata);
         		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
         	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	 public  BufferedImage getDecompressedImage( byte [] imageData) {
	        try {
	            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	            return ImageIO.read(bais);
	        } catch (IOException ex) {
	            return null;
	        }
	    }

	public static void main(String[] args) {
		NetDrawBoardServer sever=new NetDrawBoardServer();
		sever.setServer(6666);
	}
}
