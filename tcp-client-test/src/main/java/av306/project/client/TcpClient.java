package av306.project.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient
{
	public void run()
	{
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try
		{
			Bootstrap clientBootstrap = new Bootstrap();

			clientBootstrap.group( eventLoopGroup );
			clientBootstrap.channel( NioSocketChannel.class );
			clientBootstrap.remoteAddress( new InetAddress( "localhost", 9999 ) ); // InetAddress of server
			clientBootstrap.handler(
				new ChannelInitializer<SocketChannel>()
				{
					protected void initChannel( SocketChannel socketChannel ) throws Exception
					{ socketChannel.pipeline().addLast( new ClientHandler() ); }
				}
			);

			ChannelFuture channelFuture = clientBootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();

			eventLoopGroup.shutdownGracefully().sync();
		}
		catch ( Exception e )
		{
			e.printStackTrace;
		}
	}
}