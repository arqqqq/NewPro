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
		//服务器通道
		ServerSocketChannel serverChannale = ServerSocketChannel.open();
		//配置非阻塞
		serverChannale.configureBlocking(false);
		//绑定端口
		serverChannale.bind(new InetSocketAddress(8888));
		
		//
		Selector sel = Selector.open();
		SelectionKey selKey = serverChannale.register(sel, SelectionKey.OP_ACCEPT);
		
		while(true) {
			sel.select();
			
			//获取Key的集合
			Set<SelectionKey>  keySet = sel.selectedKeys();
			//获取集合的迭代器
			Iterator<SelectionKey> keys = keySet.iterator();
			//遍历集合
			while(keys.hasNext()) {
				SelectionKey key = keys.next();
				//判断类型
				if(key.isAcceptable()) {
					System.out.println("客户连接进来！");
					//接收连接
					SocketChannel channel = serverChannale.accept();
					
					//配置客户为非阻塞
					channel.configureBlocking(false);
					//发送消息
					channel.write(ByteBuffer.wrap("welcome\r\n".getBytes()));
					//
					channel.register(sel, SelectionKey.OP_READ);
					
				}else if(key.isReadable()) {
					//创建缓存
					ByteBuffer buf =ByteBuffer.allocate(1024);
					//获取Channel
					SocketChannel channel = (SocketChannel)key.channel();
		            
					//读取
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
