package av306.chat.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class Main
{
	public static final Logger LOGGER = LogManager.getLogger("ChatServer");
	
	public static void main( String[] args )
	{
		int port;
		
		if ( args.length == 1 )
		{
			port = Integer.parseInt( args[0] );
		}
		else port = 8007;

		LOGGER.info( "Starting ChatServer on port " + port );
		new ChatServer( port );
	}
}
