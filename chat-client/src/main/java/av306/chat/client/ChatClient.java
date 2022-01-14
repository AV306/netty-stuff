package av306.chat.client;

import static av306.chat.client.Main.LOGGER;
import static av306.chat.client.Main.MESSAGE_LOGGER;
import av306.chat.client.handler.ChatClientHandler;

import java.util.Scanner;
 
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClient
{
	private String serverAddress;
	private int serverPort;

	private String clientName;

	public ChatClient( String serverAddress, int port )
	{
		this.serverAddress = serverAddress;
		this.serverPort = port;
	}


	public void run()
	{
		// Take Scanner input
		Scanner messageScanner = new Scanner( System.in );
		MESSAGE_LOGGER.info( "Enter username: " ); // System.out for client-only messages that aren't logs.
		if ( messageScanner.hasNext() ) this.clientName = messageScanner.nextLine();

		
		// This is the client, so it doesn't need boss/worker groups.
		EventLoopGroup group = new NioEventLoopGroup();

		try
		{
			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group( group );
			bootstrap.channel( NioSocketChannel.class );
			bootstrap.handler(
				new ChannelInitializer<SocketChannel>()
				{
					@Override
					public void initChannel( SocketChannel socketChannel ) throws Exception
					{
						ChannelPipeline pipeline = socketChannel.pipeline();

						pipeline.addLast( new StringDecoder() );
						pipeline.addLast( new StringEncoder() );

						pipeline.addLast( new ChatClientHandler() );
					}
				}
			);

			ChannelFuture channelFuture = bootstrap.connect( serverAddress, serverPort ).sync();

			// Iterate and take message input from user,
			// and send it to the server.
			while ( messageScanner.hasNext() )
			{
				String input = messageScanner.nextLine();

				Channel channel = channelFuture.sync().channel();
				channel.writeAndFlush( "<" + clientName + "> " + input );
				channel.flush();	
			}

			// Close the channel after the client has stopped input.
			channelFuture.channel().closeFuture().sync();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			group.shutdownGracefully();
		}
	}
} 