package ViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import Model.Model.ModelInterface;
import View.ClientHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
/**
 * This class is our implementation to ViewModelInterface, it both observer and observable (MVVM)
 *
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class ServerViewModel extends Observable implements ViewModelInterface,Observer {

	private ModelInterface model;
	private BlockingQueue<ClientHandler> clientHandlersQueue;
	private boolean isStopped=false;
	private ClientHandler currentClientHandler;
	private Thread handlersThread;
	private int finishedIndex;
	private IntegerProperty limit;
	@FXML
	private Label limitLabel;
	@FXML
	private Slider limitSlider;
	
	
	//constructor
	public ServerViewModel(ModelInterface model) {
		super();
		// TODO Auto-generated constructor stub
		clientHandlersQueue=new LinkedBlockingQueue<>();
		this.model=model;
		isStopped=false;
		this.start();
		limitSlider=new Slider();
		limitSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                limitLabel.textProperty().setValue(String.valueOf((int)limitSlider.getValue()));
  } });
	}

	
	/****************************/
	/*****Implemented Methods****/
	/****************************/
	
	/*******ViewModelInterface***/
	@Override
	public void addClientHandler(ClientHandler ch) {
		// TODO Auto-generated method stub
		clientHandlersQueue.add(ch);
		
	}
	@Override
	public int getFinishedIndex() {
		// TODO Auto-generated method stub
		return finishedIndex;
	}

	/*******Observer*************/
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0==model)//model found solution or db data
		{
			if(model.isSolution())
			{
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setFinishedIndex(currentClientHandler.getIndex());
				currentClientHandler.setReturnedAnswer(model.getSolution());
				isStopped=false;
				start();
			}
			else if(model.isDB())
				{
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					this.setFinishedIndex(currentClientHandler.getIndex());
					currentClientHandler.setReturnedAnswer(model.getDBData());
					isStopped=false;
					start();
				}
		}
		
		
	}

	
	
	/*****************************/
	/******Private Methods********/
	/*****************************/
	/**
	 * talking with the conncting user using the client handler
	 * @param ch-user's client handler
	 */
	private void handleClientHandler(ClientHandler ch)
	{
		isStopped=true;//after pull client handler stop until finish handle
		String str=currentClientHandler.getRequest();
		if(str!=null)
		{
			String splittedStr[]=str.split("-");
			switch(splittedStr[0])
			{//resolve what client want
			case "solve":
				model.SearchForSolution(splittedStr[1]);
				break;
			case "post":
				model.postRecord(splittedStr[1]);
				break;
			case "get":
				model.SearchForDBData(splittedStr[1]);
				break;
				default:
				{
					currentClientHandler.setReturnedAnswer("invalid request");
					isStopped=false;
					start();
					
				}
			}
			
		}
	}
	/**
	 * This method runs the queue pulling thread
	 */
	private void start()
	{
		handlersThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!isStopped)//run in background
				{
					
						try {
							currentClientHandler=clientHandlersQueue.take();//when pulling one client handler stop the thread
							if(currentClientHandler!=null)
							{
								isStopped=true;
								handleClientHandler(currentClientHandler);
							}
							else
								System.out.println("fdfdfdf");

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				}
				
			}
		});
		
		handlersThread.start();
	}
	/**
	 * This method update the new client handler index and notify observers
	 * @param i-updated client handler index
	 */
	private void setFinishedIndex(int i)
	{
		finishedIndex=i;
		setChanged();
		notifyObservers();
	}
	
	
	/********************************/
	/*******getters and setters******/
	/********************************/
	/**
	 * 
	 * @return the current limit for users
	 */

	public int getLimit() {
		return limit.get();
	}

	/**
	 * update the limiter
	 * @param limit-new user's limit
	 */
	public void setLimit(int limit) {
		this.limit.set(limit);
		limitLabel.setText(""+limit);
	}
	

	




}
