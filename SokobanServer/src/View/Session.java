package View;

import javafx.beans.property.SimpleStringProperty;
/**
 * This class is the form of row in the table in the view GUI
 * @author Sahar Mizrahi and Gal ezra
 *
 */
public class Session {
	
	private SimpleStringProperty client;
	private SimpleStringProperty process;
	private SimpleStringProperty time;
	private SimpleStringProperty state;
	
	
	//constructor
	public Session(String clientNumber, String process, String time,
			String state) {
		super();
		this.client = new SimpleStringProperty(clientNumber);
		this.process = new SimpleStringProperty(process);
		this.time = new SimpleStringProperty(time);
		this.state = new SimpleStringProperty(state);
	}
	/************************/
	/***getters and setters**/
	/************************/
	public String getClient() {
		
		return client.get();
	}
	public void setClient(String client) {
		this.client.set(client);;
	}
	public String getProcess() {
		return process.get();
	}
	public void setProcess(String process) {
		this.process.set(process);
	}
	public String getTime() {
		return time.get();
	}
	public void setTime(String time) {
		this.time.set(time);
	}
	public String getState() {
		return state.get();
	}
	public void setState(String state) {
		this.state.set(state);
	}

	
	
	
	
	
	
	
	
	
	

}
