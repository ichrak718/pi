/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.binder.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class PieChartController implements Initializable {

    @FXML
    private PieChart piechart;
        private Stage stage;
 private final ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
   
     private Connection cnx;
    private Statement ste;
    @FXML
    private Button btAnnuler;

    public PieChartController() {
        cnx = MyConnection.getInstance().getCnx();
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        piechart.setData(getGradeStatistics());
    }    

     public void getStage(Stage stage) {
        this.stage = stage;
    }
 public ObservableList<PieChart.Data> getGradeStatistics() {
 try {
            String q1 ="SELECT COUNT(*) FROM grade WHERE (grade<10);";
            String q2 ="SELECT COUNT(*) FROM grade WHERE (grade>=10);";
            ste = cnx.prepareStatement(q1);
            ResultSet rs = ste.executeQuery(q1);
           
            while (rs.next()){
                int count1 = rs.getInt(1);
                details.add(new PieChart.Data("Grade < 10", count1));
            }
            rs = ste.executeQuery(q2);
             while (rs.next()){
                int count2 = rs.getInt(1);
                details.add(new PieChart.Data("Grade > 10", count2));
            }
           
        } catch (SQLException ex) {
              System.out.println(ex.getMessage());
        }
 return details;
}   

    @FXML
    private void btAnnulerOnClick(ActionEvent event) {
      
                Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("homeBinder.fxml"));
           
                btAnnuler.getScene().setRoot(root);
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
            }
            }
       

}

