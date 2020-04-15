/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Assma
 */
public class HomePageExam extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) { 
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("GestionExams.fxml"));
            Parent n;
            n = (Parent) loader.load();
            GestionExamsController GE = loader.getController();
            GE.getStage(primaryStage);
            Scene scene = new Scene(n);
            primaryStage.setTitle("managing exams");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
