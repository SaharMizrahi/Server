package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;



/** A Class that implements ClientHandler interface
 * 
 * This class communicate with the client
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class MyClientHandler extends Observable implements ClientHandler {

	private PrintWriter pw;
	private BufferedReader br;
	private int clientIndex;
	




	
		


	/**
	 * Default constructor
	 */
	public MyClientHandler(InputStream in,OutputStream out,int index) {
		super();
		// TODO Auto-generated constructor stub
		br=new BufferedReader(new InputStreamReader(in));
		pw=new PrintWriter(out);
		clientIndex=index;

	}
	/**************************/
	/****implemented methods***/
	/***************************/
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

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return this.clientIndex;
	}







}
