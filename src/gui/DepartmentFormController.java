package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel; 
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("clicou no save");
	}
	
	@FXML
	public void onBtCacelAction() {
		System.out.println("Clicou no cancel");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 32);
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	
	//metodo responsavel por pegar o conteudo da variavel entity e popular a as caixas de texto do formulario
	public void updateFormData() {
		if (entity == null) throw new IllegalStateException("Entity was null");
		txtId.setText( entity.getId() == null ? "" : String.valueOf(entity.getId()));
		txtName.setText(entity.getName() == null ? "" : String.valueOf(entity.getName()));
	}

}
