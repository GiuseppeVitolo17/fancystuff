/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filechooser;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author tony0
 */
public class Chooser extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        final FileChooser fileChooser = new FileChooser();
        
        final Button openButton = new Button("Open a Picture...");
        final Button openMultipleButton = new Button("Open Pictures...");
        final Label lb = new Label();
        final VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        openButton.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(final ActionEvent e){
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if(file != null)
                            lb.setText("Length: " + String.valueOf(openFile(file)));
                    }
                }
        );
        
        
        root.getChildren().addAll(openButton,openMultipleButton,lb);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("File Chooser Sample");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public long openFile(File file){
        return file.length();
    }
}
