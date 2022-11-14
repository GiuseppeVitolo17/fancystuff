/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package seconda.prova.corretta;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author antonioferrigno
 */
public class FXMLMainViewController implements Initializable {
    
    @FXML
    private SplitPane splitPane;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private Button addContactButton;
    @FXML
    private TableColumn<Contact, String> nameClm;
    @FXML
    private TableColumn<Contact, String> surnameClm;
    @FXML
    private TableColumn<Contact, String> numberClm;
    @FXML
    private TextField OTPtextField;
    @FXML
    private Button unlockButton;
    @FXML
    private Label wrongPassLbl;
    @FXML
    private VBox OTPVBox;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfCognome;
    @FXML
    private TextField tfNumero;
    
    ObservableList<Contact> lista;
    @FXML
    private TableView<Contact> mainTable;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // def e iniz lista
        if(prendiDaFile()==null)
            lista = FXCollections.observableArrayList();
        else
            lista=prendiDaFile();
        
        
        // parte per la tabella
        
        nameClm.setCellValueFactory(new PropertyValueFactory("nome"));
        surnameClm.setCellValueFactory(new PropertyValueFactory("cognome"));
        numberClm.setCellValueFactory(new PropertyValueFactory("numero"));
        
        nameClm.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameClm.setCellFactory(TextFieldTableCell.forTableColumn());
        numberClm.setCellFactory(TextFieldTableCell.forTableColumn());
        
        mainTable.setItems(lista);
        
        // prima vediamo la parte della tabella
        
        //OTPVBox.setVisible(false);
        //splitPane.setVisible(true);
        
        //poi OTP
        
        unlockButton.disableProperty().bind(OTPtextField.textProperty().isEmpty());
        OTPThread t = new OTPThread();
        Thread OTP = new Thread(t);
        OTP.setDaemon(true);
        OTP.start();
        
        // Bindings tabella

        addContactButton.disableProperty().bind(Bindings.not(Bindings.and(tfNome.textProperty().isNotEmpty(), tfCognome.textProperty().isNotEmpty()).and(tfNumero.textProperty().isNotEmpty())));
        saveMenuItem.disableProperty().bind(mainTable.itemsProperty().isEqualTo(prendiDaFile()));
    }    
    
    public ObservableList<Contact> prendiDaFile(){
        ArrayList<Contact> list = null;
        try(ObjectInputStream i = new ObjectInputStream(new BufferedInputStream(new FileInputStream("saved.bin")))){
            list = (ArrayList<Contact>)i.readObject();
        }catch(FileNotFoundException ex){
            System.out.println(ex);
        }catch(IOException ex){
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(list!=null){
            ObservableList<Contact> l = FXCollections.observableArrayList(list);
            return l;
        }
        return null;
    }
    
    @FXML
    private void saveFile(ActionEvent event) {
        
        try(ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("saved.bin")))){
            o.writeObject(new ArrayList<Contact>(lista));
        }catch(FileNotFoundException ex){
            System.out.println(ex);
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    private void addContact(ActionEvent event) {
        if(!lista.contains(new Contact(tfNome.getText(),tfCognome.getText(),tfNumero.getText()))){
            lista.add(new Contact(tfNome.getText(),tfCognome.getText(),tfNumero.getText()));
            tfNome.textProperty().setValue("");
            tfCognome.textProperty().setValue("");
            tfNumero.textProperty().setValue("");
        }
        else{
            Alert alert = new Alert(AlertType.ERROR, "Contatto già presente");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteContact(ActionEvent event) {
        lista.remove(mainTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void copyContact(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(mainTable.getSelectionModel().getSelectedItem().toString());
        clipboard.setContent(content);
    }


    @FXML
    private void unlock(ActionEvent event) {
        int otp = 0;
        
        try(Scanner s = new Scanner(new BufferedReader(new FileReader("otp.txt")))){
            s.useDelimiter(": ");
            s.useLocale(Locale.US);
            while(s.hasNext()){
            s.next();
            otp=s.nextInt();
            }
            s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if((OTPtextField.textProperty().getValue().equals(String.valueOf(otp))) && !(OTPtextField.textProperty().getValue().isEmpty())){
            wrongPassLbl.visibleProperty().set(false);
            OTPVBox.visibleProperty().set(false);
            splitPane.visibleProperty().set(true);
        }else
            wrongPassLbl.visibleProperty().set(true);
    }

    @FXML
    private void changeName(TableColumn.CellEditEvent<Contact, String> event) {
        
        Contact c = mainTable.getSelectionModel().getSelectedItem();
        String newNome = event.getNewValue();
        Contact newContact = new Contact(newNome, c.getCognome(), c.getNumero());
        if(!lista.contains(newContact)) {
            c.setNome(newNome);
            lista.sort(null);
        } else {
            Alert a = new Alert(AlertType.ERROR, "Contatto già presente");
            a.showAndWait();
        }
        mainTable.refresh();
    }

    @FXML
    private void changeSurname(TableColumn.CellEditEvent<Contact, String> event) {
        /*
        Contact c = mainTable.getSelectionModel().getSelectedItem();
        String newCognome = event.getNewValue();
        Contact newContact = new Contact(c.getNome(), newCognome, c.getNumero());
        if(!lista.contains(newContact)) {
            c.setCognome(newCognome);
            lista.sort(null);
        }
        else{
            Alert alert = new Alert(AlertType.ERROR, "Contatto già presente");
            alert.showAndWait();
        }
        mainTable.refresh();
        */
    }

    @FXML
    private void changeNumber(TableColumn.CellEditEvent<Contact, String> event) {
        /*
        Contact c = mainTable.getSelectionModel().getSelectedItem();
        String newNumero = event.getNewValue();
        Contact newContact = new Contact(c.getNome(), c.getCognome(), newNumero);
        if(!lista.contains(newContact)) {
            c.setNumero(newNumero);
            lista.sort(null);
        }
        else{
            Alert alert = new Alert(AlertType.ERROR, "Contatto già presente");
            alert.showAndWait();
        }
        mainTable.refresh();
        */
    }
    
    
}
