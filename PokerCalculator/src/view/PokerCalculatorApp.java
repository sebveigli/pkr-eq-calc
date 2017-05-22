package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PokerCalculatorApp extends Application {

	@Override
	public void start(Stage s) throws Exception {
		try {
			Parent p = FXMLLoader.load(getClass().getResource("/view/Client.fxml"));
			Scene sc = new Scene(p);
			
			s.setScene(sc);
			s.setTitle("Poker Calculator - Sebastian Veigli");
			s.setResizable(false);
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
