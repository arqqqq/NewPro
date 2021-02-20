package ShanZhaiQQSever;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author 邹仁
 * 山寨QQ服务器V1.0
 *1、需要创建一个方法来实现这个服务器
 *实现一个客户端与一个服务器相互发送信息，或者说是两个用户之间相互发送消息
 */
public class QQSever extends JFrame{
    private sendTools tools;
    private JTextArea Textshow;
	//通过构造器设置属性
	public QQSever() {
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("山寨QQ服务器端");
		FlowLayout flow=new FlowLayout();
		this.setLayout(flow);
		//添加组件
		JButton btn=new JButton("发送");
		//编辑单行文本框
		final JTextField Textwrite=new JTextField(40);
		//显示文本的多行文本窗口
		Textshow=new JTextArea(20,40);
		//添加
		this.add(btn);
		this.add(Textwrite);
		this.add(Textshow);
		//给JButton添加监听器
		btn.addActionListener(new ActionListener() {
			
			@Override
			//鼠标按下，将文本信息发送给客户端，并且清空文本框,并显示到多行文本框
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String Msg=Textwrite.getText();
				System.out.println("发送的信息为："+Msg);
				if(!Msg.equals("")) {
					tools.sendMsg(Msg);
					Textshow.append("服务器：  "+Msg+"\r\n");
					System.out.println("消息发送成功！");
					Textwrite.setText("");
				}
			}
		});
		this.setVisible(true);
	}
	
	
	
	public void setSever(int port) {
		 //创建服务器-->等待客户端的接入-->客户端接入后获取客户端的输入输出流-->存放到一个数组列表里 
		//一个客户端接入服务器，就是在服务器这边留了一个存根，或者说开辟了一条沟通的路径，可以指一个线程
		try {
			//创建一个服务器
			ServerSocket sever=new ServerSocket(port);
			System.out.println("QQ服务器创建成功！");
			//等待客户机的接入
			Socket client=sever.accept();
			System.out.println("客户机接入成功！");
			//获取客户机的输入输出流
			tools =new sendTools(client,Textshow);
			//线程启动
			tools.start();
		}catch(Exception e) {
			System.out.println("服务器创建失败");
			e.printStackTrace();
		}
		
		
	}
	
	
	//主函数
	public static void main(String[] args) {
		QQSever QQ=new QQSever();
		QQ.setSever(8888);
	}
}
