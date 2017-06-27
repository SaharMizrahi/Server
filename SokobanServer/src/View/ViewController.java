package View;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;import javax.naming.LimitExceededException;

import ViewModel.ServerViewModel;
import ViewModel.ViewModelInterface;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ViewController implements Initializable, ViewInterface,Observer {
	private int numOfSessions=0;
	private ViewModelInterface viewModel;
	private MyClientHandler ch;
	private int port;
	private boolean stop=true;
	private ThreadPoolExecutor pool;
	private ServerSocket server;
	private Socket aClient=null;
	private Thread serverthread;
	private ObservableList<Session> list;
	private SimpleDateFormat sdf;
	private Date date;
	/***GUI Arguments*****/
	@FXML
	private Button serverButton;
	@FXML
	private Button shutDownButton;
	@FXML
	private Circle stateFlag;
	@FXML
	private TableView<Session> table;
	@FXML
	private TableColumn<Session, String> clientCol;
	@FXML
	private TableColumn<Session, String> processCol;
	@FXML
	private TableColumn<Session, String> timeCol;
	@FXML
	private TableColumn<Session, String> stateCol;
	private int limit;
	@FXML
	private Slider limitSlider;
	@FXML
	private Label limitLabel;
	
	/***************************/
	/***implemented Methods*****/
	/***************************/
	
	@FXML 


	public void runServer()
	{
		
		restart();
		stop=false;
		stateFlag.setFill(Color.LIMEGREEN);
		serverButton.setDisable(true);
		shutDownButton.setDisable(false);
		try {
			if(server!=null)
			{
				if(server.isClosed())
					server=new ServerSocket(port);
			}
			else
			{
				server=new ServerSocket(port);

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverthread=new Thread(()->{
			
			
			while(!stop)
			{
				try {
					aClient=server.accept();
					pool.execute(()->{
						try {
							numOfSessions++;
							date=new Date();
							list.add(new Session(""+numOfSessions, "Sokoban game", ""+sdf.format(date), "waiting"));
							
							//resetTable();
							ch=new MyClientHandler(aClient.getInputStream(),aClient.getOutputStream(),numOfSessions);
							viewModel.addClientHandler(ch);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		serverthread.start();
		
	

	}
	@FXML
	public void shutDownServer()
	{
		this.stop=true;
		serverthread.interrupt();
		serverButton.setDisable(false);
		shutDownButton.setDisable(true);
		stateFlag.setFill(Color.RED);
		if(pool!=null)
			pool.shutdown();
		
			


		
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0==this.viewModel)
		{
			int num=((ServerViewModel)viewModel).getFinishedIndex();
			Session s=list.get(num-1);
			s.setState("done");
			list.remove(num-1);
			list.add(s);
		}
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		list=FXCollections.observableArrayList();
		resetTable();
		date=new Date();
		sdf=new SimpleDateFormat("HH:mm:ss");
		limitLabel.setText("10");
		limit=10;
		limitSlider.valueProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				limitLabel.textProperty().setValue(""+(int)limitSlider.getValue());
				limit=(int)limitSlider.getValue();
				pool.setCorePoolSize(limit);
			}
		});;
		restart();

	}

	/******************************/
	/*****getters and setters******/
	/******************************/
	public void setViewModel(ViewModelInterface vm)
	{
		this.viewModel=vm;
	}
	
	/*****************************/
	/*******Private Methods*******/
	/*****************************/

	/**
	 * reatart the socket thread
	 */
	private void restart()
	{
		
		BlockingQueue<Runnable> bq=new ArrayBlockingQueue<>(limit);
		this.port=8888;
		pool=new ThreadPoolExecutor(1, 5, 2, TimeUnit.SECONDS, bq);
		serverButton.setDisable(false);
		shutDownButton.setDisable(true);
		stateFlag.setFill(Color.RED);
	}
	/**
	 * reset the table in the GUI
	 */
	private void resetTable()
	{
		table.setEditable(true);


		table.getColumns().clear();
		table.setItems(list);
		table.getColumns().add(this.clientCol);
		table.getColumns().add(this.processCol);
		table.getColumns().add(this.timeCol);
		table.getColumns().add(this.stateCol);
		clientCol.setCellValueFactory(new PropertyValueFactory<>("Client"));
		processCol.setCellValueFactory(new PropertyValueFactory<>("Process"));
		timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
		stateCol.setCellValueFactory(new PropertyValueFactory<>("State"));
	}
	

	

	


	
}
