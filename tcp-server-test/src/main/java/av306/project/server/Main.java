package av306.project.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main
{
	public static Logger LOGGER = LogManager.getLogger( "Server" );
	
	public static void main( String[] args )
	{
		LOGGER.info( "Starting server" );
		new TcpServer().run();
	}

}