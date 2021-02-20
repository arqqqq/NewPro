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
			//���ѡ�������سɹ�
			System.out.println("Selector �Ѿ�������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Selector ����ʧ�ܣ�");
			e.printStackTrace();
		}
	}
	public void setNIOSever(int port) throws IOException {
				
		try {
			//����severͨ��
			ServerSocketChannel NIOSever=ServerSocketChannel.open();
			
			//����ͨ��Ϊ������
			NIOSever.configureBlocking(false);
			
			//���ù̶��˿�
			NIOSever.bind(new InetSocketAddress(port));
			
			System.out.println("һ��������ͨ���Ѿ��������ˣ�");
			
			//����selector����ע��һ��Accpet�¼�
			NIOSever.register(sel, SelectionKey.OP_ACCEPT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//���ý���
	public void setFrame() {
		frame.setTitle("����NIO��ܵ��ı������ҷ�������");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		
		
		FlowLayout flow=new FlowLayout();
		frame.setLayout(flow);
		text=new JTextArea(11,40);
		frame.add(text);
	    dhtext=new JTextArea(11, 40);
		frame.add(dhtext);
		//��TextField��Ӽ�����
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
				//����ǰ���Enter��������Ϣ���͵��ͻ���
				if(code==KeyEvent.VK_ENTER) {
					String msg=text.getText();
					//����ı��༭���������Ϣ���ǿ��ı��Ļ�������Ϣ���͸��ͻ���
					if(!("".equals(msg))) {
						text.setText("");
						dhtext.append("�������� "+msg+"\n");
						//����Ϣ���͵��ͻ���
                        try {
							socchanel.write(ByteBuffer.wrap(msg.getBytes()));
							System.out.println("��Ϣ�ѷ��ͣ�");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		//���ÿɼ�
		frame.setVisible(true);
		
	}
	//������Ϣ�Ļ��Ʒŵ�һ���߳��������
	public void run() {
		while(true) {
			try {
				sel.select();
				//��ȡsel��SelectionKey�ĵ�����
				Iterator<SelectionKey> itor=sel.selectedKeys().iterator();
				
				while(itor.hasNext()) {
					//�õ��������
					SelectionKey keys=itor.next();
					//�Ƴ����SelectionKey
					
					//�ж��¼�����
					if(keys.isAcceptable()) {
						ServerSocketChannel NIOSever=(ServerSocketChannel)keys.channel();
						//�Ѿ������¼�
						socchanel=NIOSever.accept();
						//����ͨ��Ϊ������
						socchanel.configureBlocking(false);
						//������ӽ��
						System.out.println("�ͻ�������������");
						socchanel.write(ByteBuffer.wrap("�ͻ������".getBytes()));
						//��Selector��ע��һ��read�¼�
						socchanel.register(sel, SelectionKey.OP_READ);
//						socchanel.register(sel, SelectionKey.OP_WRITE);
						
					}else if(keys.isReadable()) {
						System.out.println("����read�ж�");
						//��ȡ�¼��Ѿ�׼����
						SocketChannel soccha=(SocketChannel)keys.channel();
//						socchanel=soccha;
						//����һ��1024bit�Ļ�����
						ByteBuffer buf=ByteBuffer.allocate(1024);
						
						int Style=soccha.read(buf);
						if(Style!=-1) {
							//����Ϣ���ͽ����ˣ�
							byte[] data=buf.array();
							String  msg=new String(data);
							System.out.println("�ͻ��˷�����Ϣ��");
							dhtext.append("�ͻ��ˣ�"+msg+"\n");
						}						
					}
//					else if(keys.isWritable()) {
//						if(!("".equals(commsg))) {
//							//����Ϣ���͸��ͻ��ˣ���������
//							System.out.println("��������������");
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
