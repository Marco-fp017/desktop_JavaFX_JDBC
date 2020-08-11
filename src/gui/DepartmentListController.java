package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	private DepartmentService service;
	private ObservableList<Department> obsList;
	
	@FXML
	private TableView<Department> tableViewDepartment;

	//table column referente a coluna do table view de Id, por isso, o tipo Integer
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	//referente ao nome, por isso o tipo String
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	@FXML
	private void onBtAction() {
		System.out.println("On bt action");
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//fazendo o palco acompanhar a janela inteira
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	//Metodo responsavel por acessar o serviço, carregar os departamentos e mostra-los na interface
	public void updateTableView() {
		if(service == null) throw new IllegalStateException("Service was null");
		
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
}
