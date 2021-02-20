package Sever;

import java.net.ServerSocket;

public class Sever {
	
	
	public void setSever(int port) {
		//
		try {
			ServerSocket sever=new ServerSocket(port);
			System.out.println("服务器创建成功！");
			//等待客户机的连接
			java.net.Socket client=sever.accept();
			System.out.println("客户机连接进来了");
			java.io.OutputStream ous =client.getOutputStream();
			java.io.InputStream ins=client.getInputStream();
			
			while(true) {
				System.out.println("等待客户机传入数值....");
				int i=ins.read();
				System.out.println("接收过来的值为"+i);
				
			}
		}catch(Exception e) {
			System.out.println("服务器创建失败");
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Sever sever=new Sever();
		sever.setSever(9999);
	}

}
