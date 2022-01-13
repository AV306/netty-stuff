package av306.project.client.handlers;

import static av306.project.client.Main.LOGGER;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<String>
{
	@Override
	public void channelActive( ChannelHandlerContext ctx )
	{
		ctx.writeAndFlush( Unpooled.copiedBuffer( "Netty rocks! :D", CharsetUtil.UTF_8 ) );
	}

	@Override
	public void channelRead0( ChannelHandlerContext ctx, String message ) throws Exception
	{
		LOGGER.info( "Client received: " + message );
	}

	@Override
	public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause )
	{
		cause.printStackTrace();
		ctx.close();
	}
}