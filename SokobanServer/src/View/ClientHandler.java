package View;

import java.io.InputStream;
import java.io.OutputStream;


/**This is an Interface that defined the Method that Handle the Server and the Client communication
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */

public interface ClientHandler  {

	/**
	 * 
	 * @return the client request
	 */
	String getRequest();
	/**
	 * 
	 * @param str-answer to the client
	 */
	void setReturnedAnswer(String str);
	/**
	 * 
	 * @return client's index
	 */
	int getIndex();
	
	

}
