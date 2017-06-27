package boot;
/*Gal Ezra and Sahar Mizrahi Sokoban project
 * */


//check123
import java.io.IOException;

import Model.Model.ModelInterface;
import Model.Model.ServerModel;
import View.ViewController;
import View.ViewInterface;
import ViewModel.ServerViewModel;
import ViewModel.ViewModelInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainWindowController;

//check1213212
public class Run extends Application {



	public void start(Stage primaryStage) {
	

			FXMLLoader loader=new FXMLLoader();

			BorderPane gameRoot;
			try {
				ServerModel model=new ServerModel();//MODEL
				ServerViewModel vm=new ServerViewModel(model);//VIEW MODEL
				model.addObserver(vm);
				gameRoot = loader.load(getClass().getResource("View.fxml").openStream());
				ViewController vc=loader.getController();//VIEW
				vc.setViewModel(vm);
				vm.addObserver(vc);


				Scene gameScene = new Scene(gameRoot,650,650);
				gameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(gameScene);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}




			

	public static void main(String[] args) {



		launch(args);
		
		
		



	}

	
}
