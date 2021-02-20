package MeetingSeverV2;

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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * 
 * @author ����
 * @Title ������Ƶ����ͽ���ƽ̨V2.0
 * @ʵ�ֹ��ܣ����ܹ�ʵ��ʵʱ����Ƶ����ͼ򵥵�Ϳѻ�����������ڴ˻����ϣ�����ʵ��һЩ������˾����練��ɫ���������ͻ����ڰ�
 * @���ݴ���Э�飺���ݴ���ˣ����ȴ���һ�����ݰ��ı�ʶ�źż�����ͷ��byte���ͣ�-->Ȼ����ݲ�ͬ�ı���ͷд�벻ͬ���������ͺ�����
 *                                                -->ִ����Ӧ�Ĳ���
 *                            ���ݽ��ܶˣ����ȶ�ȡһ������ͷ��Tag���󣩣�byte���ͣ�-->Ȼ��������Tag�жϷ��͹�������������-->Ȼ��ִ��
 *                                                ��Ӧ�Ĳ���         
 *                                                           
 */
public class MeetingSever_Two extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;
	private SeverListener Lis;
	private PictureDeal picdeal;
    private  int PhotoStyle=0;
	public MeetingSever_Two() {
		//��������
		this.setSize(600, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("���罻���������V2.0��������");
		
		this.setVisible(true);
		g=this.getGraphics();
		picdeal=new PictureDeal();
		Lis=new SeverListener(this);
	}
	//���ò˵��Լ�������
	public void setMenu() {
		//�˵���
		JMenuBar JMB=new JMenuBar();
		JMenu JM=new JMenu("�˾�Ч��");
		
		JMenuItem JMI=new JMenuItem("ԭ��");
		JMenuItem JMI1=new JMenuItem("����ɫ");
		JMenuItem JMI2=new JMenuItem("�ڰ�");		
		JMB.add(JM);
        JM.add(JMI);
        JM.add(JMI1);
        JM.add(JMI2);
		JMI.addActionListener(Lis);
		JMI1.addActionListener(Lis);
		JMI2.addActionListener(Lis);
		this.setJMenuBar(JMB);
	}
	//����������ڸ����˾�����
	public void changeStyle(int STYLE) {
		PhotoStyle=STYLE;
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
             		BufferedImage img1=JudgeStyle(img);
             	
             		g.drawImage(img1, 400, 200, 200, 400, null);
             		System.out.println("�ɹ���");
                    
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
    public BufferedImage JudgeStyle(BufferedImage buff) {
    	BufferedImage newBuff = buff;
    	if(PhotoStyle==1) {
    		newBuff=picdeal.FanChaSe(buff);
    	}
    	return newBuff;
    }
	public static void main(String[] args) {
		MeetingSever_Two sever=new MeetingSever_Two();
		sever.setMenu();
		sever.setServer(6666);
	}
}
