package av306.chat.client.handler;

import static av306.chat.client.Main.LOGGER;
import static av306.chat.client.Main.MESSAGE_LOGGER;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String>
{
	@Override
	protected void channelRead0( ChannelHandlerContext ctx, String message ) throws Exception
	{
		MESSAGE_LOGGER.info( message );
	}
}