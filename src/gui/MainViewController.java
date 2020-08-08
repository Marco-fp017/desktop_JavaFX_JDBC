package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("menu item department");
	}
	
	@FXML
	public void onMenuItemSellertAction() {
		System.out.println("menu item seller");
	}	
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("menu item about");
	}	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
