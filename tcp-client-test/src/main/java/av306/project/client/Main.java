package av306.project.client;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main
{
	public static Logger LOGGER = LogManager.getLogger( "Client" );
	
	public static void main( String[] args )
	{
		new TcpClient().run();
	}
}