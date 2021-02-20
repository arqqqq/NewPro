package NetConnectSever2;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
/**
 * 网络画板服务器V2.0
 * @author 
 *
 */
public class NetDrawBoardServer1 extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;
    private static int color=0;
	public NetDrawBoardServer1() {
		//设置属性
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("网络画板服务器升级版V2.0");
		this.setLocationRelativeTo(null);
		
		
		this.setVisible(true);
		g=this.getGraphics();
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
         	java.io.OutputStream out=client.getOutputStream();
         	java.io.InputStream input=client.getInputStream();
         	Input =new DataInputStream(input);
         	Out =new DataOutputStream(out);
         	while(true){
         		System.out.println("等待客户端发送数据中.....");
         		//首先接受color值和tag值
         		color=Input.readInt();      		
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
                    g.clearRect(0, 0, this.getWidth(), this.getHeight());
                    System.out.println("命令类型：清空界面");
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
