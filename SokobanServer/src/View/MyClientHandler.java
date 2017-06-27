package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;



/** A Class that implements ClientHandler interface
 * it has String that refers to the client command, one string that refers the message to the user
 * 
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class MyClientHandler extends Observable implements ClientHandler {

	private PrintWriter pw;
	private BufferedReader br;
	
	

	/**
	 * The codes of the method from ClientHandler interface
	 */
	


	
		


	/**
	 * Default constructor
	 */
	public MyClientHandler(InputStream in,OutputStream out) {
		super();
		// TODO Auto-generated constructor stub
		br=new BufferedReader(new InputStreamReader(in));
		pw=new PrintWriter(out);
		

	}

	@Override
	public String getRequest() {
		// TODO Auto-generated method stub
		try {
			String s=br.readLine();
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setReturnedAnswer(String str) {
		// TODO Auto-generated method stub
		pw.println(str);
		pw.flush();
	}







}
