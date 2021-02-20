package NetConnectSever1;

import java.awt.Graphics;
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
public class NetDrawBoardServer extends JFrame{
	
	private Graphics g;
	private DataInputStream Input;
	private DataOutputStream Out;

	public NetDrawBoardServer() {
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
         		int x1=Input.readInt();
         		int y1=Input.readInt();
         		int x2=Input.readInt();
         		int y2=Input.readInt();
         		System.out.println("接受的数据为：x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
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
