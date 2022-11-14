/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package convertitoretemperatura;

import java.util.function.UnaryOperator;
import javafx.scene.control.CheckBox;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

/**
 *
 * @author antonioferrigno
 */
public class ConvertitoreTemperatura extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label lb = new Label();
        Label input = new Label("input:");
        TextField tf = new TextField("0");
        Label result = new Label("Result:");
        Label resultDouble = new Label();
        CheckBox check = new CheckBox();
        check.setText("<--->");
        
        HBox hbox1 = new HBox(8);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(input,tf);
        
        HBox hbox2 = new HBox(8);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(result,resultDouble);
        
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lb,hbox1,hbox2,check);
        
        Scene scena = new Scene(root,300,250);
        
        //Bindings 
        lb.textProperty().bind(Bindings.when(check.selectedProperty()).then("Fahr to Celsius").otherwise("Celsius to Farh"));
        // if(condition)then(consequence)else(other) --> when(condition)then(consequence)otherwhise(something else)
        
        DoubleProperty input_data = new SimpleDoubleProperty();
        StringConverter sc = new DoubleStringConverter();
        DoubleProperty output_data = new SimpleDoubleProperty();
        final double fn = (double)5/9;
        final double nf = (double)9/5;
        
        tf.setTextFormatter(new TextFormatter( (UnaryOperator<Change>) c -> (c.getControlNewText().matches("-?\\d*(\\.\\d*)?")) ? c : null));
        
        Bindings.bindBidirectional(tf.textProperty(), input_data, sc); // input_data = tf.textProperty() (trasf. in double con sc)
        
        NumberBinding fc = (input_data.subtract(32)).multiply(fn);
        NumberBinding cf = (input_data.multiply(nf)).add(32);
        
        output_data.bind(Bindings.when(check.selectedProperty()).then(fc).otherwise(cf));
        resultDouble.textProperty().bind(output_data.asString());
        
        primaryStage.setScene(scena);
        primaryStage.setTitle("MyInputConverter");
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

