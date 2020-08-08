package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();
			Scene mainScene = new Scene(scrollPane);
			
			/* trocando um objeto da classe "Parent" por um da classe "ScrollPane",  
			 * podemos usar os métodos abaixo, que fazem a barra de menu acompanhar 
			 * o tamanho da tela independente do tamanho que ela possua
			*/
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true); 
			
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
