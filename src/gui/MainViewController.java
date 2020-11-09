package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{

	@FXML
	MenuItem menuItemSeller;
	
	@FXML
	MenuItem menuItemDepartment;
	
	@FXML
	MenuItem menuItemAbout;
	
	@FXML
	public void menuItemSellerAction() {
		System.out.println("menuItemSellerAction()");
	}
	
	@FXML
	public void menuItemDepartmentAction() {
		System.out.println("menuItemDepartmentAction()");
	}
	
	@FXML
	public void menuItemAboutAction() {
		System.out.println("menuItemAboutAction()");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}

}
