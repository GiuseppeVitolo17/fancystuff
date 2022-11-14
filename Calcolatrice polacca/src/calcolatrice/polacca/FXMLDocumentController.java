/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcolatrice.polacca;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author tony0
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button insertButton;
    @FXML
    private TextField textField;
    @FXML
    private Button removeButton;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    @FXML
    private Button multiplyButton;
    @FXML
    private Button divideButton;
    @FXML
    private TableColumn<Float, Float> stackClm;
    @FXML
    private TableView<Float> tableView;
    
    private ObservableList<Float> stack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stack = FXCollections.observableArrayList();
        
        stackClm.setCellValueFactory(new PropertyValueFactory<>("Stack"));
        
        tableView.setItems(stack);
    }    

    @FXML
    private void addNumber(ActionEvent event){
        stack.add(Float.parseFloat(textField.getText()));
    }

    @FXML
    private void removeNumber(ActionEvent event) {
        stack.remove(0);
    }

    @FXML
    private void plusAction(ActionEvent event) {
        if(stack.size()>=2){
            float op1 = stack.remove(stack.size()-1);
            float op2 = stack.remove(stack.size()-2);
            stack.add(op1+op2);
        } else {
            Label label = new Label("Inserire almeno 2 elementi nello stack!\n");
            VBox root = new VBox(label);
            Scene scena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scena);
            stage.show();
        }
    }

    @FXML
    private void minusAction(ActionEvent event) {
        if(stack.size()>=2){
            float op1 = stack.remove(stack.size()-1);
            float op2 = stack.remove(stack.size()-2);
            stack.add(op1-op2);
        } else {
            Label label = new Label("Inserire almeno 2 elementi nello stack!\n");
            VBox root = new VBox(label);
            Scene scena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scena);
            stage.show();
        }
    }

    @FXML
    private void multiplyAction(ActionEvent event) {
        if(stack.size()>=2){
            float op1 = stack.remove(stack.size()-1);
            float op2 = stack.remove(stack.size()-2);
            stack.add(op1*op2);
        } else {
            Label label = new Label("Inserire almeno 2 elementi nello stack!\n");
            VBox root = new VBox(label);
            Scene scena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scena);
            stage.show();
        }
    }

    @FXML
    private void divideAction(ActionEvent event) {
        if(stack.size()>=2){
            float op1 = stack.get(stack.size()-1);
            float op2 = stack.get(stack.size()-2);
            if(op1!=0){
                stack.remove(stack.size()-1);
                stack.remove(stack.size()-2);
                stack.add(op1/op2);
            }else{
                Label label = new Label("Impossibile dividere per 0\n");
                VBox root = new VBox(label);
                Scene scena = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scena);
                stage.show();
            }
                
        } else {
            Label label = new Label("Inserire almeno 2 elementi nello stack!\n");
            VBox root = new VBox(label);
            Scene scena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scena);
            stage.show();
        }
    }
    
}
