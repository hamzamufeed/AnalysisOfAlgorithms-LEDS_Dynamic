package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GenerateController {

	@FXML
	private TextField Name;

	@FXML
	private TextField led_number;

	@FXML
	private TextField leds_order;

	@FXML
	private Button generateFile;

	@FXML
	private Button back;

	@FXML
	void BackToMain(ActionEvent event) {
		Stage stage = (Stage) back.getScene().getWindow();
		FadeTransition ft = new FadeTransition(Duration.millis(500));
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		stage.close();
	}

	@FXML
	void GenerateFile(ActionEvent event) throws IOException {
		boolean flag = false;
		String[] str = null;
		if(Name.getText().isEmpty() || led_number.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("Check Text Feilds Input");
			alert.show();
			return;
		}
		if(!leds_order.getText().isEmpty()) {
			flag = true;
			str = leds_order.getText().split(" ");
			if(str.length != Integer.parseInt(led_number.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Error");
				alert.setContentText("Input Error");
				alert.show();
				return;
			}
		}
		File file = new File(Name.getText()+".txt");
		file.createNewFile();
		FileWriter out = new FileWriter(file);
		out.write(led_number.getText());
		out.write("\n");
		if(flag) {
			for(int i=0;i<str.length;i++)
				out.write(str[i]+" ");
		}
		else {
			int number = Integer.parseInt(led_number.getText());
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i=1; i<=number; i++) {
				list.add(new Integer(i));
			}
			Collections.shuffle(list);
			for(int i=0;i<list.size();i++)	
				out.write(""+list.get(i)+" ");
		}
		out.close();
		BackToMain(event);
	}

}
