package netNIO_API;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class myNIOSever  extends Thread{
	

	
	private JFrame frame;
	private Selector sel ;
	private JTextArea text;
	private JTextArea dhtext;
	private SocketChannel socchanel;
	private myNIOSever() {
		frame=new JFrame();
		try {
			sel=Selector.open();
			//输出选择器加载成功
			System.out.println("Selector 已经启动！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Selector 启动失败！");
			e.printStackTrace();
		}
	}
	public void setNIOSever(int port) throws IOException {
				
		try {
			//创建sever通道
			ServerSocketChannel NIOSever=ServerSocketChannel.open();
			
			//设置通道为非阻塞
			NIOSever.configureBlocking(false);
			
			//设置固定端口
			NIOSever.bind(new InetSocketAddress(port));
			
			System.out.println("一个服务器通道已经创建好了！");
			
			//先在selector里面注册一个Accpet事件
			NIOSever.register(sel, SelectionKey.OP_ACCEPT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//设置界面
	public void setFrame() {
		frame.setTitle("基于NIO框架的文本聊天室服务器端");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		
		
		FlowLayout flow=new FlowLayout();
		frame.setLayout(flow);
		text=new JTextArea(11,40);
		frame.add(text);
	    dhtext=new JTextArea(11, 40);
		frame.add(dhtext);
		//给TextField添加监听器
		text.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int code=e.getKeyCode();
				//如果是按下Enter键，则将消息发送到客户端
				if(code==KeyEvent.VK_ENTER) {
					String msg=text.getText();
					//如果文本编辑框里面的消息不是空文本的话，将信息发送给客户端
					if(!("".equals(msg))) {
						text.setText("");
						dhtext.append("服务器： "+msg+"\n");
						//将消息发送到客户端
                        try {
							socchanel.write(ByteBuffer.wrap(msg.getBytes()));
							System.out.println("消息已发送！");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		//设置可见
		frame.setVisible(true);
		
	}
	//处理消息的机制放到一个线程里面进行
	public void run() {
		while(true) {
			try {
				sel.select();
				//获取sel的SelectionKey的迭代器
				Iterator<SelectionKey> itor=sel.selectedKeys().iterator();
				
				while(itor.hasNext()) {
					//拿到这个命令
					SelectionKey keys=itor.next();
					//移除这个SelectionKey
					
					//判断事件类型
					if(keys.isAcceptable()) {
						ServerSocketChannel NIOSever=(ServerSocketChannel)keys.channel();
						//已经接收事件
						socchanel=NIOSever.accept();
						//设置通道为非阻塞
						socchanel.configureBlocking(false);
						//输出连接结果
						System.out.println("客户端连接上来了");
						socchanel.write(ByteBuffer.wrap("客户端你好".getBytes()));
						//在Selector里注册一个read事件
						socchanel.register(sel, SelectionKey.OP_READ);
//						socchanel.register(sel, SelectionKey.OP_WRITE);
						
					}else if(keys.isReadable()) {
						System.out.println("进入read判断");
						//读取事件已经准备好
						SocketChannel soccha=(SocketChannel)keys.channel();
//						socchanel=soccha;
						//创建一个1024bit的缓冲区
						ByteBuffer buf=ByteBuffer.allocate(1024);
						
						int Style=soccha.read(buf);
						if(Style!=-1) {
							//有消息发送进来了：
							byte[] data=buf.array();
							String  msg=new String(data);
							System.out.println("客户端发来消息了");
							dhtext.append("客户端："+msg+"\n");
						}						
					}
//					else if(keys.isWritable()) {
//						if(!("".equals(commsg))) {
//							//将消息发送给客户端，并且置零
//							System.out.println("进到这里面来了");
//							SocketChannel soc =(SocketChannel)keys.channel();
//							soc.write(ByteBuffer.wrap(commsg.getBytes()));
//							soc.register(sel, SelectionKey.OP_READ);
//							commsg="";
//						}
//						
//					}
					itor.remove();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) throws IOException {
		myNIOSever se=new myNIOSever();
		se.setFrame();
		se.setNIOSever(8899);
		se.start();		
	}
}
