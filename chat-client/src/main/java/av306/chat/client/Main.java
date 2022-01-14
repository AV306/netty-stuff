package av306.chat.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main
{
	public static final Logger LOGGER = LogManager.getLogger("ChatClient"); // Internal logger
	public static final Logger MESSAGE_LOGGER = LogManager.getLogger("Message"); // Mesage logger

	private static String server;
	private static int port;
	
	public static void main( String[] args )
	{
		if ( args.length == 2 )
		{
			server = args[0];
			
			port = Integer.parseInt( args[1] );
		}
		else
		{
			server = "localhost";
			port = 8007;
		}

		LOGGER.info( "Starting ChatClient on port " + port );
		new ChatClient( server, port );
	}
}