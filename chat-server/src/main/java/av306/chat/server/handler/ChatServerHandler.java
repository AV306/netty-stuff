package av306.chat.server.handler;

import static av306.chat.server.Main.LOGGER;

import java.util.ArrayList;
import java.util.List;
 
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles the chat server logic.
 * NOTE: Expects data in String form - convert byte streams to String first!
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String>
{
	private static List<Channel> connectedChannels = new ArrayList<Channel>();

	// Whenever a client connects to the server via this channel,
	// add him to the list of connected clients.
	@Override
	public void channelActive( final ChannelHandlerContext ctx )
	{
		LOGGER.info( "Client " + ctx + " connected to the server." );
		connectedChannels.add( ctx.channel() );

		for ( Channel client : connectedChannels )
		{
			client.writeAndFlush( ctx + " joined the chat!\n" );
		}
	}

	@Override
	public void channelInactive( final ChannelHandlerContext ctx ) throws Exception
	{
		LOGGER.info( "Client " + ctx + " disconnected from the server." );
		connectedChannels.remove( ctx.channel() );

		for ( Channel client : connectedChannels )
		{
			client.writeAndFlush( ctx + " left the chat.\n" );
		}
	}

	// When a message is received,
	// broadcast it to all channels.
	// TODO: Implement Direct Message system
	@Override
	public void channelRead0( ChannelHandlerContext ctx, String message )
	{
		LOGGER.info( "Client " + ctx + " sent message " + message );

		for ( Channel channel : connectedChannels )
		{
			channel.writeAndFlush( message  + "\n" ); // message already contains client name
		}
	}

	// In case of an exception, close the channel.
	@Override
	public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause )
	{
		cause.printStackTrace();
		ctx.close();
		LOGGER.info( "Client " + ctx + " lost connection due to an exception." );
	}
}
