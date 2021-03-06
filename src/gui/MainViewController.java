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

public class MainViewController implements Initializable {

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
		loadView("/gui/DepartmentList.fxml" , (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableViewData();
		});
	}

	@FXML
	public void menuItemAboutAction() {
		loadView("/gui/AboutView.fxml" , x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializeAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox vboxMain = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node menuItem = vboxMain.getChildren().get(0);
			vboxMain.getChildren().clear();
			vboxMain.getChildren().add(menuItem);
			vboxMain.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initializeAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", null, e.getMessage(), AlertType.ERROR);
		}
	}
}
