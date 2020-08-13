package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartmentService;
import model.services.SellerService;

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
		//O loadView possui uma ArrowFunction (expressao lambda), para que cada especificacao possa ser feita diretamente na chamada do metodo.
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemSellertAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}	
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}	
	
	//Metodo que carrega uma tela qualquer, recebendo o caminho do FXML referente a tela que ele irá abrir
	private synchronized <T> void loadView (String absoluteName, Consumer <T> initializingAction) {
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

			//essas duas linhas executam o que foi passado como ArrowFunction no parametro do metodo 
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {	//a excecao lancada eh uma alerta, ao inves de parar a execucao do programa
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		} 
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
