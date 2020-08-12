package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private DepartmentService service;
	
	private Department entity;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) throw new IllegalStateException("Entity was null");
		if(service == null) throw new IllegalStateException("Service was null");
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			
			notifyDataChangeListeners();
			
			//fechando a janela apos salvar corretamente
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	private void notifyDataChangeListeners() {

		for(DataChangeListener dl : dataChangeListeners) {
			dl.onDataChanged();
		}
	}

	private Department getFormData() {
		Department obj = new Department();
	
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		//verificando se o campo nome for vazio
		if(txtName.getText() == null || txtName.getText().trim().equals("")) exception.addError("Name", "Field cant't be empty");
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size() > 0) throw exception;
		
		return obj;
	}

	@FXML
	public void onBtCacelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service){
		this.service = service;
	}
	
	//metodo responsavel por pegar o conteudo da variavel entity e popular a as caixas de texto do formulario
	public void updateFormData() {
		if (entity == null) throw new IllegalStateException("Entity was null");
		txtId.setText( entity.getId() == null ? "" : String.valueOf(entity.getId()));
		txtName.setText(entity.getName() == null ? "" : String.valueOf(entity.getName()));
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}	
	
	private void setErrorMessages(Map<String, String> errors) {
		Set <String> fields = errors.keySet();
		if(fields.contains("Name")) labelErrorName.setText(errors.get("Name"));
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 32);
	}
}
