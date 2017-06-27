package ViewModel;

import View.ClientHandler;
/**
 * This interface is our interface of the ViewModel in sokoban server (MVVM) 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public interface ViewModelInterface {

	/**
	 * This method add a new client handler to our queue
	 * @param ch-new client handler
	 */
	void addClientHandler(ClientHandler ch);
	/**
	 * 
	 * @return the client handler 'clientNumber'
	 */
	int getFinishedIndex();
}
