package control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * interface called logout that all controllers implement
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public interface LogoutInterface {
	
	default void logoutfnc(ActionEvent event) throws ClassNotFoundException {
    	Parent parent;
    	 
		try {
						
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			parent = (Parent) loader.load();
		
			Scene scene = new Scene(parent);
							
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();			             
			app_stage.setScene(scene);
			app_stage.show();  
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
         
	}
	
}
