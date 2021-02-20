package netNIO_API;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOBaseServer {
	//
	public static void main(String[] args)throws Exception {
		//������ͨ��
		ServerSocketChannel serverChannale = ServerSocketChannel.open();
		//���÷�����
		serverChannale.configureBlocking(false);
		//�󶨶˿�
		serverChannale.bind(new InetSocketAddress(8888));
		
		//
		Selector sel = Selector.open();
		SelectionKey selKey = serverChannale.register(sel, SelectionKey.OP_ACCEPT);
		
		while(true) {
			sel.select();
			
			//��ȡKey�ļ���
			Set<SelectionKey>  keySet = sel.selectedKeys();
			//��ȡ���ϵĵ�����
			Iterator<SelectionKey> keys = keySet.iterator();
			//��������
			while(keys.hasNext()) {
				SelectionKey key = keys.next();
				//�ж�����
				if(key.isAcceptable()) {
					System.out.println("�ͻ����ӽ�����");
					//��������
					SocketChannel channel = serverChannale.accept();
					
					//���ÿͻ�Ϊ������
					channel.configureBlocking(false);
					//������Ϣ
					channel.write(ByteBuffer.wrap("welcome\r\n".getBytes()));
					//
					channel.register(sel, SelectionKey.OP_READ);
					
				}else if(key.isReadable()) {
					//��������
					ByteBuffer buf =ByteBuffer.allocate(1024);
					//��ȡChannel
					SocketChannel channel = (SocketChannel)key.channel();
		            
					//��ȡ
					int count = channel.read(buf);
					if(count !=-1) {
						String msg = new String(buf.array());
						System.out.println("msg:"+msg);
					}
				}
				keys.remove();
			}
		}
		
	}

}
