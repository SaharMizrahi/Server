package ViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import Model.Model.ModelInterface;
import View.ClientHandler;

public class ServerViewModel extends Observable implements ViewModelInterface,Observer {

	private ModelInterface model;
	private BlockingQueue<ClientHandler> clientHandlersQueue;
	private boolean isStopped=false;
	private ClientHandler currentClientHandler;
	private Thread handlersThread;
	
	
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

	//runs the thread of the client handlers queue
	public void start()
	{
		handlersThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!isStopped)//run in background
				{
					
						try {
							currentClientHandler=clientHandlersQueue.poll(1, TimeUnit.SECONDS);//when pulling one client handler stop the thread
							if(currentClientHandler!=null)
							{
								isStopped=true;
								handleClientHandler(currentClientHandler);
							}

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				}
				
			}
		});
		
		handlersThread.start();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0==model)//model found solution or db data
		{
			if(model.isSolution())
			{
				
				currentClientHandler.setReturnedAnswer(model.getSolution());
				isStopped=false;
				start();
			}
			else if(model.isDB())
				{
					currentClientHandler.setReturnedAnswer(model.getDBData());
					isStopped=false;
					start();
				}
		}
		
		
	}


	@Override
	public void addClientHandler(ClientHandler ch) {
		// TODO Auto-generated method stub
		clientHandlersQueue.add(ch);
		
	}


	public ServerViewModel(ModelInterface model) {
		super();
		// TODO Auto-generated constructor stub
		clientHandlersQueue=new LinkedBlockingQueue<>();
		this.model=model;
		isStopped=false;
		this.start();
		
	}
	
	
	




}
