package av306.chat.server;

import static av306.chat.server.Main.LOGGER;
import av306.chat.server.handler.*;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

// https://itsallbinary.com/netty-project-understanding-netty-using-simple-real-world-example-of-chat-server-client-good-for-beginners/
public class ChatServer
{
	private int port = 8007;

	
	public ChatServer( int port )
	{
		this.port = port;
	}

	
	public void run()
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup( 1 );
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		// Server bootstrap
		try
		{
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group( bossGroup, workerGroup );
			serverBootstrap.channel( NioServerSocketChannel.class );
			serverBootstrap.childHandler(
				new ChannelInitializer<SocketChannel>()
				{
					@Override
					public void initChannel( SocketChannel socketChannel ) throws Exception
					{
						ChannelPipeline pipeline = socketChannel.pipeline();

						// Socket channel communication happens
						// in byte streams.
						// String codecs help with conversion between byte and String.
						pipeline.addLast( new StringDecoder() );
						pipeline.addLast( new StringEncoder() );
						
						// Custom chat logic handler
						pipeline.addLast( new ChatServerHandler() );
					}
				}
			);

			// Start the server.
			LOGGER.info("ChatServer starting on port: " + port );
			ChannelFuture channelFuture = serverBootstrap.bind( port ).sync();

			// Wait until server channel is closed.
			channelFuture.channel().closeFuture().sync();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			// Shut down all event loop groups.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}