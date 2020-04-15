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
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.services.ServicesPupils;
import tn.esprit.binder.services.gradeservice.ServicesGrade;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class UpdateGradeController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btAnnuler;
    @FXML
    private Label error;
    @FXML
    private Label reussi;
    @FXML
    private TextField tfteacher;
    @FXML
    private ComboBox<String> tfpupil;
    @FXML
    private Spinner<Double> spGrade;
    @FXML
    private ComboBox<Integer> cbExam;
    private Grade gr;
    private ServicesGrade ser = new ServicesGrade();
        public ObservableList<String> pupil;
        ServicesPupils servicesPupils =new ServicesPupils();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException, IOException {
        try {
            gr.setExamname(cbExam.getValue());
            gr.setGrade(spGrade.getValue());
            gr.setTeacher(tfteacher.getText());
            String pupil = tfpupil.getSelectionModel().getSelectedItem();

            gr.setPupil(pupil);
            ser.updateGrades(gr);
            reussi.setText("Update successful.");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setGrade(gr);
            btUpdate.getScene().setRoot(root);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    @FXML
    private void btAnnulerOnClick(ActionEvent event) throws ParseException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return");
        alert.setHeaderText("Interrupt update");
        alert.setContentText("Would you like to go back to the previous page?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setGrade(gr);
            btAnnuler.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    public void setGrade(Grade gr) throws SQLException {
        this.gr = gr;
        cbExam.setValue(gr.getExamname());
        SpinnerValueFactory<Double> valueFactoryGrade = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 20, gr.getGrade(), 0.5);
        this.spGrade.setValueFactory(valueFactoryGrade);
        tfteacher.setText(gr.getTeacher());
                           tfpupil.getSelectionModel().select(gr.getPupil());
            pupil = new ServicesPupils().readAllP();
                        tfpupil.setItems(FXCollections.observableArrayList(pupil));
        System.out.println(gr);
    }

}
