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
 * 网络画板服务器V1.0
 * 创建服务器-->等待客户端的连接-->进行相应的操作
 * 视频性能诱惑-->在传输数值方向的优化-->
 * 流程：首先用ImageIO流将BufferedImage对象转换成jpg的格式-->获取这个jpg格式文件的byte数组-->将这个数组发给服务器
 * -->服务器接收这个数组-->然后用ImageIOinputstream转换成BufferImage对象-->最后在服务器界面上画出来
 * @author 邹仁
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
			OutputStream out=client.getOutputStream();
			InputStream input=client.getInputStream();
         	Input =new DataInputStream(input);
         	Out =new DataOutputStream(out);
         	//接受数据
         	while(true) {
         		System.out.println("等待数据的输入");
         		//首先读字节数组的length-->然后再读字节数组
         		int length=Input.readInt();
         		byte[] imagedata=new byte[length];
         		Input.read(imagedata);
         		//接下来把Imagedata字节数组转化成BufferedImage对象
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
