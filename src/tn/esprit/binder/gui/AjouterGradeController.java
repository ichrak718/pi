/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.services.ServicesPupils;
import tn.esprit.binder.services.ServicesTimeTable;
import tn.esprit.binder.services.gradeservice.ServicesGrade;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class AjouterGradeController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private Button btEnvoyer;
    @FXML
    private Button btAnnuler;
    @FXML
    private Label reussi;
    private Stage stage;
    @FXML
    private TextField tfteacher;
    @FXML
    private ComboBox<String> tfpupil;
    @FXML
    private Spinner<Double> spGrade;
    @FXML
    private ComboBox<String> cbExam;
    public  List<String> listE;
    public ObservableList<String> pupil;

   
    ServicesGrade Gradus = new ServicesGrade();
    ServicesPupils servicesPupils =new ServicesPupils();
Grade g;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listE= Gradus.readAllE();
            cbExam.setItems(FXCollections.observableArrayList(listE));
            pupil = new ServicesPupils().readAllP();
                        tfpupil.setItems(FXCollections.observableArrayList(pupil));

            SpinnerValueFactory<Double> valueFactoryGrade = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 20, 0, 0.5);
            this.spGrade.setValueFactory(valueFactoryGrade);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /*
 @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        if(validateDuration()){
        
         String about = aboutAJ.getText();
        
        String duration =durationAJ.getText();
         String location =locationAJ.getText();
        
          
          LocalDate date =dp.getValue();
          // String name =clubAJ.getValue();
          String name = clubAJ.getValue();
         
         ActivityService sl = new ActivityService();
         //int clubId ;
         int clubId = sl.getClubId(name);
            System.out.println(name);
           
           activity l = new activity(clubId,about,duration,location,date.toString(),name);
           sl.addActivity(l);
             AfficherAll();*/
    @FXML
    private void btAjouterOnClick(ActionEvent event) throws IOException, ParseException {
        try {
            Grade g;
            
            String examname =  cbExam.getValue();
            
            String teacher = tfteacher.getText();
            Double grade = (double) spGrade.getValue();

            String pupil = tfpupil.getSelectionModel().getSelectedItem();
            ServicesGrade s1 = new ServicesGrade();
            int examId = s1.getExamId(examname);
            g = new Grade(examId, grade, teacher, pupil);
            s1.addGrades(g);
            reussi.setText("Add successful");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setGrade(g);
            btAnnuler.getScene().setRoot(root);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btAnnulerOnClick(ActionEvent event) throws ParseException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return");
            alert.setHeaderText("Interrupt adding");
            alert.setContentText("Would you like to go back to the homepage?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setGrade(g);
            btAnnuler.getScene().setRoot(root);
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void getStage(Stage stage) {
        this.stage = stage;
    }

    
}
