package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	//Atributos referentes aos campos da interface
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemAbout;
	
	
	//metodos executados quando determinado campo da interface eh clicado
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemSellertAction() {
		System.out.println("menu item seller");
	}	
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}	
	
	//Metodo que carrega uma tela qualquer, recebendo o caminho do FXML referente a tela que ele irá abrir
	private synchronized void loadView (String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			/*recebe a cena da classe principal, para que a view recentemente aberta 
			 * contenha toda a sua estrutura basica. Isso garante uma certa padronizacao
			 * entre as telas.
			 * Abaixo estao os metodos que carregam de fato toda a estrutura de filhos 
			 * contida no FXML da cena principal.
			*/
			Scene mainScene = Main.getMainScene();
			
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {	//a excecao lancada eh uma alerta, ao inves de parar a execucao do programa
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		} 
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
