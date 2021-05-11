package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SampleController implements Initializable{

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button Start_Button;

	@FXML
	private Button Restart_Button;

	@FXML
	private Button Generate_Button;

	@FXML
	private Button Browse_Button;

	@FXML
	private TextField Path_TextField;

	@FXML
	private TextField LedNum_TextField;

	@FXML
	private TextField Order_TextField;

	@FXML
	private TextField LedONum_TextField;

	@FXML
	private TextField LedON_TextField;

	@FXML
	private TextArea Result_TextArea;
	@FXML
	private Button Exit_Button;


	// default file path  
	public static String file_path ="";

	Scanner in = null;

	//when the program start
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//		Path_TextField.setText(file_path); // the default path
		//
		//		File input = new File(file_path);
		//		try {
		//			in = new Scanner(input); // access and read the file
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		}
		//
	}

	@FXML
	void BrowseFile(ActionEvent event) {

		/*FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if(selectedFile == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("You didn't select a File!.");
			alert.show();
    	}
    	else {
    		Path_TextField.setText(selectedFile.getAbsolutePath());
    	}*/



		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		File defaultDirectory = new File(System.getProperty("user.dir")); //the default directory (project directory)
		fileChooser.setInitialDirectory(defaultDirectory);
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"); // allows only text files
		fileChooser.getExtensionFilters().add(extFilter); // allows only text files
		Stage stage = (Stage)anchorPane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);
		if(file != null && file.isFile()) {
			file_path = file.getAbsoluteFile().toString(); //change the default file to the new one
			Path_TextField.setText(file_path); // set the new file path

			try {
				in = new Scanner(file); // access and read the new file
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	// this functions open new scene to create a file
	@FXML
	void GenerateFile(ActionEvent event) throws IOException {

		AnchorPane show = (AnchorPane)FXMLLoader.load(getClass().getResource("FileGenerate.fxml"));
		FadeTransition ft = new FadeTransition(Duration.millis(200), show);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		Stage stage = new Stage();
		stage.setTitle("Generate File");
		stage.setScene(new Scene(show, 409, 191));
		stage.show();

	}



	// set all the default values again 
	@FXML
	void Restart(ActionEvent event) throws IOException {
		AnchorPane show = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample1.fxml"));
		FadeTransition ft = new FadeTransition(Duration.millis(500), show);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		Scene scene = new Scene(show);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		/*Path_TextField.clear();
    	LedNum_TextField.clear();
    	Order_TextField.clear();
    	LedONum_TextField.clear();
    	LedON_TextField.clear();
    	Result_TextArea.clear();*/

	}

	public static void Alert(String message) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.setTitle("Error!");
		alert.setHeaderText(null);
		alert.setResizable(false);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.show();
	}

	@FXML
	void Start(ActionEvent event) throws FileNotFoundException {
		File file = new File(file_path);
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			Alert("File not found!");
			return;
		}
		System.out.println("FILE ACCESSED");
		int number = in.nextInt(); //number of leds
		in.nextLine();
		String str = in.nextLine();
		System.out.println("LED number: " + number);
		System.out.println("LED order: "+str);
		int[] B = Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray(); // split the order in array B (and convert from string to integers)

		List<Integer> BList = new ArrayList<Integer>(B.length);
		for (int i : B)
		{
			BList.add(i); // fill the LEDs arraylist as integers (leds order)
		}

		//		int[] A = new int[number];
		//		for(int i=1;i<=number;i++) { 
		//			A[i-1]=i;
		//		}

		List<Integer> AList= new ArrayList<Integer>();
		for(int i=1; i<=number;i++)
			AList.add(i); // fill the source arraylist as the number of leds 

		in.close();

		LedNum_TextField.setText(Integer.toString(number)); // print on UI the # of LEDs 
		Order_TextField.setText(str); // print the LEDs order

		int m = AList.size();
		int n = BList.size();
		int[][] dp = new int[m+1][n+1]; //the dynamic table
		int[][] direction = new int[m+1][n+1]; 

		//		Set<Integer> hash_Set = new HashSet<Integer>(); //to not duplicate the values


		// base case is when one of the arrays is zero so we start from i=1
		for(int i = 1; i < m+1; ++i) {
			for(int j = 1; j < n+1; ++j) {
				if(AList.get(i-1) == BList.get(j-1)) {
					dp[i][j] = 1 + dp[i-1][j-1]; //previous value (diagonal) +1 if they are matching

					//					hash_Set.add(dp[i][j]); //add the LEDs which are on without duplicate

					direction[i][j]=0; // 0 when we take the diagonal value
				}
				else {
					dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]); //the max either from < or the ^
					if(dp[i][j] == dp[i-1][j]) // 1 if we take the above value ^
						direction[i][j]=1;
					else if(dp[i][j] == dp[i][j-1]) // 2 if we take the left value < 
						direction[i][j]=2;
				}
			}
		}


		System.out.println("Max numbers of LEDS ON = " + dp[m][n]);

		LedONum_TextField.setText("" + dp[m][n]); //print the max value of LEDs On (last cell in table)

		System.out.println("Solution Table: ");

		for(int i = 0; i <= m; i++) {
			for(int j = 0; j <= n; j++) {
				//			    System.out.print(dp[i][j] + "	");
				System.out.print(direction[i][j] + "	");

				Result_TextArea.appendText(dp[i][j] + "\t"); // print the dynamic table 
			}
			System.out.println();
			Result_TextArea.appendText("\n\n");
		}

		AList.add(0, 0); // add 0 in index 0
		BList.add(0, 0); // add 0 in index 0

		System.out.println("B SIZE: "+ BList.size() + "    " + "A SIZE: "+ AList.size());


		test =  new HashSet<Integer>();
		whichLedOn(direction, BList, n, m); //pass the direction table, the new Blist, n, m
		//System.out.println(test);
		System.out.println("LEDs ON are: " + test + "\n");
		LedON_TextField.setText("" + test); // print the LEDs that are on
	}
	public static Set<Integer> test;
	public void whichLedOn(int [][]direction, List<Integer> BList, int i, int j){
		//Set<Integer> hash_Set = new HashSet<Integer>();


		//		System.out.println(BList);

		if(i != 0 && j !=0) {
			if(direction [i][j]==0) { // we took the value diagonal

				//System.out.print(BList.get(j)+",");
				test.add(BList.get(j)); // add it the hash set 
				//				System.out.println(hash_Set);
				whichLedOn(direction, BList, i-1, j-1); // we move to the diagonal value and do the same
			}
			else if(direction [i][j]==1) // we took the value from above 
				whichLedOn(direction, BList, i-1, j); // move to above and do the same
			else 
				whichLedOn(direction, BList, i, j-1); // move to left and do the same
		}	

		//		for(int q=0;q<=B.length-1;q++)
		//		{System.out.println(B[q]);
		//		}
		//return hash_Set;

	}


	@FXML
	void Exit(ActionEvent event) {
		System.exit(0);
	}


}
