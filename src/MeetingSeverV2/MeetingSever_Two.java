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
 * @author 邹仁
 * @Title 网络视频会议和交互平台V2.0
 * @实现功能：它能够实现实时的视频传输和简单的涂鸦交互操作、在此基础上，可以实现一些特殊的滤镜比如反差色，熔铸，油画，黑白
 * @数据传输协议：数据传输端：首先传输一个数据包的标识信号即报文头（byte类型）-->然后根据不同的报文头写入不同的数据类型和数量
 *                                                -->执行相应的操作
 *                            数据接受端：首先读取一个报文头（Tag对象）（byte类型）-->然后根据这个Tag判断发送过来的数据类型-->然后执行
 *                                                相应的操作         
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
		//设置属性
		this.setSize(600, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("网络交互会议软件V2.0服务器端");
		
		this.setVisible(true);
		g=this.getGraphics();
		picdeal=new PictureDeal();
		Lis=new SeverListener(this);
	}
	//设置菜单以及下拉条
	public void setMenu() {
		//菜单条
		JMenuBar JMB=new JMenuBar();
		JMenu JM=new JMenu("滤镜效果");
		
		JMenuItem JMI=new JMenuItem("原画");
		JMenuItem JMI1=new JMenuItem("反差色");
		JMenuItem JMI2=new JMenuItem("黑白");		
		JMB.add(JM);
        JM.add(JMI);
        JM.add(JMI1);
        JM.add(JMI2);
		JMI.addActionListener(Lis);
		JMI1.addActionListener(Lis);
		JMI2.addActionListener(Lis);
		this.setJMenuBar(JMB);
	}
	//这个方法用于更改滤镜类型
	public void changeStyle(int STYLE) {
		PhotoStyle=STYLE;
	}
	//创建服务器
	public void setServer(int port) {
		try {
			//创建服务器，等待客户端接入
			ServerSocket sever=new ServerSocket(port);
			System.out.println("服务器创建成功！");
			//等待客户机接入
			Socket client=sever.accept();//client为客户端对象
			System.out.println("客户端连接成功！");			
		   //获取客户端的输入输出流
			OutputStream out=client.getOutputStream();
			InputStream input=client.getInputStream();
         	Input =new DataInputStream(input);
         	Out =new DataOutputStream(out);
         	//接受数据
         	while(true) {
         		System.out.println("等待数据的输入");
         		int color=Input.readInt();      	
         		int tag=Input.read();
         		System.out.println("标识符的值为"+tag);
         		switch (tag) {
         		   
         		case 1:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("命令类型：画直线-->接受的数据为：x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.drawLine(x1, y1, x2, y2);
         			break;
         		}
         		case 2:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("命令类型：画圆-->接受的数据为：x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.fillOval(x1, y1, x2-x1, y2-y1);
         			break;
         		}
         		case 3:{
         			int x1=Input.readInt();
             		int y1=Input.readInt();
             		int x2=Input.readInt();
             		int y2=Input.readInt();
             		System.out.println("命令类型：画矩形-->接受的数据为：x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
             		g.setColor(new Color(color));
             		g.fillRect(x1, y1, x2-x1, y2-y1);
         			break;
         		}
         		case 4:{
         			int x3=Input.readInt();
             		int y3=Input.readInt();
             		System.out.println("命令类型：画随笔画-->接受的数据为：x3:"+x3+"y3:"+y3);
             		g.setColor(new Color(color));
             		g.fillOval(x3, y3, 10,10);
         			break;
         		}
         		case 5:{
                    g.clearRect(0, 30, 2*this.getWidth()/3, this.getHeight());
                    System.out.println("命令类型：清空界面");
         			break;
         		}
         		case 6:{
         			//首先读字节数组的length-->然后再读字节数组
             		int length=Input.readInt();
             		byte[] imagedata=new byte[length];
             		Input.read(imagedata);
             		//接下来把Imagedata字节数组转化成BufferedImage对象
             		BufferedImage img=getDecompressedImage(imagedata);
             		BufferedImage img1=JudgeStyle(img);
             	
             		g.drawImage(img1, 400, 200, 200, 400, null);
             		System.out.println("成功！");
                    
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
