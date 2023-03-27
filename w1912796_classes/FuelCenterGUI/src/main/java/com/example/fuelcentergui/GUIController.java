package com.example.fuelcentergui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GUIController implements Initializable {
    public String [] data = new String[46];
    public String[] queue1Array = new String[7];
    public String[] queue2Array = new String[7];
    public String[] queue3Array = new String[7];
    public String[] queue4Array = new String[7];
    public String[] queue5Array = new String[7];
    public String[] wQueueArray = new String[8];
    public String[] queue1FirstNames = new String[6];
    public String[] queue2FirstNames = new String[6];
    public String[] queue3FirstNames = new String[6];
    public String[] queue4FirstNames = new String[6];
    public String[] queue5FirstNames = new String[6];
    public String[] wQueueFirstNames = new String[8];
    public String[][] queueArrays = new String[][]{queue1Array,queue2Array,queue3Array,queue4Array,queue5Array};
    public String[][] QueueFirstNames = new String[][]{queue1FirstNames,queue2FirstNames,queue3FirstNames,queue4FirstNames,queue5FirstNames};
    public ArrayList<String> result = new ArrayList<>();

    @FXML
    private ListView<String> queue1List;
    @FXML
    private ListView<String> queue2List;
    @FXML
    private ListView<String> queue3List;
    @FXML
    private ListView<String> queue4List;
    @FXML
    private ListView<String> queue5List;
    @FXML
    private ListView<String>wQueueList;
    @FXML
    private ListView<String>resultList;
    @FXML
    private Label displayData;
    @FXML
    private Label income1;
    @FXML
    private Label income2;
    @FXML
    private Label income3;
    @FXML
    private Label income4;
    @FXML
    private Label income5;
    @FXML
    private Label fuelLabel;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    String currentSelection;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDataArray();
        setFuel();
        queue1List.getItems().addAll(queue1FirstNames);
        queue2List.getItems().addAll(queue2FirstNames);
        queue3List.getItems().addAll(queue3FirstNames);
        queue4List.getItems().addAll(queue4FirstNames);
        queue5List.getItems().addAll(queue5FirstNames);
        wQueueList.getItems().addAll(wQueueFirstNames);

        queue1List.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = queue1List.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(queue1FirstNames).indexOf(currentSelection);
            displayData.setText(displayFormat(index,queue1Array));
        });
        queue2List.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = queue2List.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(queue2FirstNames).indexOf(currentSelection);
           displayData.setText(displayFormat(index,queue2Array));
        });
        queue3List.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = queue3List.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(queue3FirstNames).indexOf(currentSelection);
            displayData.setText(displayFormat(index,queue3Array));
        });
        queue4List.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = queue4List.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(queue4FirstNames).indexOf(currentSelection);
            displayData.setText(displayFormat(index,queue4Array));
        });
        queue5List.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = queue5List.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(queue5FirstNames).indexOf(currentSelection);
            displayData.setText(displayFormat(index,queue4Array));
        });
        wQueueList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            currentSelection = wQueueList.getSelectionModel().getSelectedItem();
            int index = Arrays.asList(wQueueFirstNames).indexOf(currentSelection);
            displayData.setText(displayFormat(index,queue5Array));
        });

    }
    private String displayFormat(int index, String[] array){
        String[] text = array[index].split(" ");
        return "First Name : " + text[0] + "\nLast Name : " + text[1] + "\nVehicle Number : " + text[2] + "\nNo of liters : " + text[3] + "\n";
    }

    private void setDataArray() {
        try {
            File fileObject = new File("gui.txt");
            Scanner fileReader = new Scanner(fileObject);

            while (fileReader.hasNextLine()) {
                for (int i = 0; i < data.length; i++){
                    data[i] = fileReader.nextLine();
                }
            }
            fileReader.close();
            System.out.println("The data has been successfully loaded from the file....\n");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file ");
        }
        setAllArrays();
    }

    private void setAllArrays(){
        System.arraycopy(data, 0, queue1Array, 0, 7);
        System.arraycopy(data, 7, queue2Array, 0, 7);
        System.arraycopy(data, 14, queue3Array, 0, 7);
        System.arraycopy(data, 21, queue4Array, 0, 7);
        System.arraycopy(data, 28, queue5Array, 0, 7);
        System.arraycopy(data,36,wQueueArray,0,8);
        setFirstNames();
    }

    private void setFirstNames(){
        for (int i = 0; i < 6; i++){
            String[] word = queue1Array[i].split(" ");
            queue1FirstNames[i] = (i+1) +") " + word[0];
            income1.setText("Income : " + queue1Array[6]);
        }
        for (int i = 0; i < 6; i++) {
            String[] word = queue2Array[i].split(" ");
            queue2FirstNames[i] = (i+1) +") " + word[0];
            income2.setText("Income : " + queue2Array[6]);
        }
        for (int i = 0; i < 6; i++) {
            String[] word = queue3Array[i].split(" ");
            queue3FirstNames[i] = (i+1) +") " + word[0];
            income3.setText("Income : " + queue3Array[6]);
        }
        for (int i = 0; i < 6; i++) {
            String[] word = queue4Array[i].split(" ");
            queue4FirstNames[i] = (i+1) +") " + word[0];
            income4.setText("Income : " + queue4Array[6]);
        }
        for (int i = 0; i < 6; i++) {
            String[] word = queue5Array[i].split(" ");
            queue5FirstNames[i] = (i+1) +") " + word[0];
            income5.setText("Income : " + queue1Array[6]);
        }
        for (int i = 0; i < 8; i++) {
            String[] word = wQueueArray[i].split(" ");
            wQueueFirstNames[i] = (i+1) +") " + word[0];
        }
    }
    private void setFuel(){
        fuelLabel.setText(data[35] + " Liters");
    }

    public void onSearchButtonClick(){
        result.clear();
        resultList.getItems().addAll(result);
        for(int i = 0; i < 5; i++){
            String[] queue = QueueFirstNames[i];
            for(int j = 0; j < 5; j++){
                if (queue[j].contains(searchField.getText().toUpperCase())){
                    result.add(displayFormat(j, queueArrays[j]));
                }
            }
        }
        if (result.isEmpty()){
            result.add("\nNo results found on that search...\n");
            resultList.getItems().addAll(result);
        }else {
            resultList.getItems().addAll(result);
        }
    }
}
