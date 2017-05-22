package view;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import model.GameRunUtil;
import model.HandParserUtil;
import model.InvalidHandException;

public class PokerCalculatorController implements Initializable {
	private static PokerCalculatorController pcc;
	
	private List<Button> storedP1Buttons;
	private List<Button> storedP2Buttons;
	
	private final String SALMON = "-fx-background-color: salmon;";
	private final String CLEAR = "";
	
	@FXML
	private Button evaluateButton;
	
	@FXML
	private Button clearButton;
	
	@FXML
	private TextArea textArea;
	
	@FXML
	private TextField p1Field;
	
	@FXML
	private TextField p2Field;
	
	@FXML
	private TextField boardField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pcc = this;	
		storedP1Buttons = new ArrayList<Button>();
		storedP2Buttons = new ArrayList<Button>();
		textArea.setEditable(false);
		
	}
	
	@FXML
	public void onButtonPressed(ActionEvent e) throws InvalidHandException {
		if (e.getSource().equals(evaluateButton)) {
			runSimulation();
			return;
		} else if (e.getSource().equals(clearButton)) {
			clearFields();
			return;
		}
		
		String btnId;
		Button b = (Button) e.getSource();
		
		btnId = b.getId();
		
		if (btnId.equals("p1")) {
			buttonToPlayer1TextBox(b);
		} else {
			buttonToPlayer2TextBox(b);
		}
	}
	
	private void buttonToPlayer1TextBox(Button b) {
		
		// check if button has been clicked
		// if it hasn't format it and then append the text to p1's field
		// else set it back to clear and remove the string from the field
		
		if (b.getStyle().equals(CLEAR)) {
			b.setStyle(SALMON);
			storedP1Buttons.add(b);
			
			if (p1Field.getText().equals("")) {
				p1Field.appendText(b.getText());
			} else {
				p1Field.appendText(", " + b.getText());
			}
		} else if (b.getStyle().equals(SALMON)) {
			b.setStyle(CLEAR);
			storedP1Buttons.remove(b);
			
			if (p1Field.getText().contains(", " + b.getText())) {
				p1Field.setText(p1Field.getText().replaceAll(", " + b.getText(), ""));
			} else if (p1Field.getText().contains(b.getText() + ", ")){
				p1Field.setText(p1Field.getText().replaceAll(b.getText() + ", ", ""));
			} else {
				p1Field.setText("");
			}
			
		}
	}

	
	private void buttonToPlayer2TextBox(Button b) {
		
		if (b.getStyle().equals(CLEAR)) {
			storedP2Buttons.add(b);
			b.setStyle(SALMON);
			
			if (p2Field.getText().equals("")) {
				p2Field.appendText(b.getText());
			} else {
				p2Field.appendText(", " + b.getText());
			}
		} else if (b.getStyle().equals(SALMON)) {
			storedP2Buttons.remove(b);
			b.setStyle(CLEAR);
			
			if (p2Field.getText().contains(", " + b.getText())) {
				p2Field.setText(p2Field.getText().replaceAll(", " + b.getText(), ""));
			} else if (p2Field.getText().contains(b.getText() + ", ")){
				p2Field.setText(p2Field.getText().replaceAll(b.getText() + ", ", ""));
			} else {
				p2Field.setText("");
			}
		}
	}
	
	private void runSimulation() {
		try {
			textArea.clear();
			GameRunUtil gru = new GameRunUtil(HandParserUtil.parseRange(p1Field.getText()), 
												HandParserUtil.parseRange(p2Field.getText()),
												HandParserUtil.parseBoard(boardField.getText()));
			
			gru.run();
			
			int p1Wins = gru.getPlayerOneWins();
			int p2Wins =  gru.getPlayerTwoWins();
			int ties = gru.getTies();
			
			DecimalFormat df = new DecimalFormat("0.00");

			double p1Percentage = ((double)(p1Wins)/((double)p1Wins + (double)p2Wins + (double)ties)) * 100.0;
			double p2Percentage = ((double)p2Wins)/((double)p1Wins + (double)p2Wins + (double)ties) * 100.0;
			double tiesPercentage = ((double)ties)/((double)p1Wins + (double)p2Wins + (double)ties) * 100.0;
			
			System.out.println(p1Percentage);
			
			textArea.setText("P1 Range: " + p1Field.getText() + "\n" +
								"P2 Range: " + p2Field.getText() + "\n\n" +
								"P1 Wins: " + gru.getPlayerOneWins() + " (" + df.format(p1Percentage) + "%)\n" +
								"P2 Wins: " + gru.getPlayerTwoWins() + " (" + df.format(p2Percentage) + "%)\n" +
								"Ties: " + gru.getTies() + " (" + df.format(tiesPercentage) + "%)\n" +
								"Total Simulations: " + (p1Wins + p2Wins + ties));
		} catch (InvalidHandException e) {
			System.out.println("Invalid Hand!");
		}
	}
	
	private void clearFields() {
		for (Button b : storedP1Buttons) {
			b.setStyle(CLEAR);
			
		}
		
		for (Button b : storedP2Buttons) {
			b.setStyle(CLEAR);
			
		}
		
		p1Field.clear();
		p2Field.clear();
		boardField.clear();
		textArea.clear();
	}
}
