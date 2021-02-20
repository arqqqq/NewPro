package MeetingSever;

import java.awt.Color;
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
 * 
 * @author ����
 * @Title ������Ƶ����ͽ���ƽ̨V1.0
 * @ʵ�ֹ��ܣ����ܹ�ʵ��ʵʱ����Ƶ����ͼ򵥵�Ϳѻ��������
 * @���ݴ���Э�飺���ݴ���ˣ����ȴ���һ�����ݰ��ı�ʶ�źż�����ͷ��byte���ͣ�-->Ȼ����ݲ�ͬ�ı���ͷд�벻ͬ���������ͺ�����
 *                                                -->ִ����Ӧ�Ĳ���
 *                            ���ݽ��ܶˣ����ȶ�ȡһ������ͷ��Tag���󣩣�byte���ͣ�-->Ȼ��������Tag�жϷ��͹�������������-->Ȼ��ִ��
 *                                                ��Ӧ�Ĳ���                    
 */
public class MeetingSever_One extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;

	public MeetingSever_One() {
		//��������
		this.setSize(600, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("���罻���������V1.0��������");
		
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
         		int color=Input.readInt();      		
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
                    g.clearRect(0, 30, 2*this.getWidth()/3, this.getHeight());
                    System.out.println("�������ͣ���ս���");
         			break;
         		}
         		case 6:{
         			//���ȶ��ֽ������length-->Ȼ���ٶ��ֽ�����
             		int length=Input.readInt();
             		byte[] imagedata=new byte[length];
             		Input.read(imagedata);
             		//��������Imagedata�ֽ�����ת����BufferedImage����
             		BufferedImage img=getDecompressedImage(imagedata);
             		g.drawImage(img, 400, 200, 200, 400, null);
                    
                    
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
	 public  BufferedImage getDecompressedImage( byte [] imageData) {
	        try {
	            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	            return ImageIO.read(bais);
	        } catch (IOException ex) {
	            return null;
	        }
	    }

	public static void main(String[] args) {
		MeetingSever_One sever=new MeetingSever_One();
		sever.setServer(6666);
	}
}
