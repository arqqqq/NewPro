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
 * 网络画板服务器V1.0
 * 创建服务器-->等待客户端的连接-->进行相应的操作
 * @author 
 *
 */
public class NetDrawBoardServer2 extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;

	public NetDrawBoardServer2() {
		//设置属性
		this.setSize(400, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
         		//首先获取视频的宽和高
         		int width =Input.readInt();
         		int hight=Input.readInt();
         		//创建一个有长和宽的BufferImage对象
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
