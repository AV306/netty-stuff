package av306.project.server;

import av306.project.server.handlers.HelloServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class TcpServer
{
	public TcpServer() {}


	public void run()
	{
		// Create new NIO EventLoopGroup
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try
		{
			// Bootstrap the server
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			// Apply stuff
			serverBootstrap.group( eventLoopGroup );
			serverBootstrap.channel( NioServerSocketChannel.class );
			serverBootstrap.localAddress( new InetSocketAddress( "localhost", 9999 ) );

			serverBootstrap.childHandler(
				new ChannelInitializer<SocketChannel>()
				{
					protected void initChannel( SocketChannel socketChannel ) throws Exception
					{ socketChannel.pipeline().addLast( new HelloServerHandler() ); }
				}
			);

			// Start server
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			channelFuture.channel().closeFuture().sync();

			eventLoopGroup.shutdownGracefully().sync();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		/*finally
		{
			eventLoopGroup.shutdownGracefully().sync();
		}*/
	}

}