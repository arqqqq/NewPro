package Sever;

import java.net.ServerSocket;

public class Sever {
	
	
	public void setSever(int port) {
		//
		try {
			ServerSocket sever=new ServerSocket(port);
			System.out.println("�����������ɹ���");
			//�ȴ��ͻ���������
			java.net.Socket client=sever.accept();
			System.out.println("�ͻ������ӽ�����");
			java.io.OutputStream ous =client.getOutputStream();
			java.io.InputStream ins=client.getInputStream();
			
			while(true) {
				System.out.println("�ȴ��ͻ���������ֵ....");
				int i=ins.read();
				System.out.println("���չ�����ֵΪ"+i);
				
			}
		}catch(Exception e) {
			System.out.println("����������ʧ��");
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Sever sever=new Sever();
		sever.setSever(9999);
	}

}
