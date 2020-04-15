/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rest.file.uploader.tn.FileUploader;
import tn.esprit.binder.entities.grades.Exams;
import tn.esprit.binder.services.ServicesPupils;
import tn.esprit.binder.services.ServicesTimeTable;
import tn.esprit.binder.services.gradeservice.ServicesExam;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class UpdateExamenController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private ComboBox<String> tfSubject;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btAnnuler;
    @FXML
    private Label error;
    @FXML
    private Label reussi;
    @FXML
    private Spinner<Integer> spDuration;
      private Stage stage;
    @FXML
    private DatePicker Datepick;

    ServicesExam ser = new ServicesExam();
    Exams exa;
    @FXML
    private TextField tfFile;
    @FXML
    private Button btImport;
      private String url_pdf;
              public ObservableList<String> subject;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

      public void getStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException, IOException {
        try {
            exa.setSubject(tfSubject.getSelectionModel().getSelectedItem());
            exa.setDuration(spDuration.getValue());
            exa.setFile(tfFile.getText());
            exa.setDate(Datepick.getValue().toString());
            ser.updateEams(exa);
            reussi.setText("Update successful.");
                                   FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setExam(exa);
            btUpdate.getScene().setRoot(root);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }
    
 

    @FXML
    private void btAnnulerOnClick(ActionEvent event) throws ParseException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return");
        alert.setHeaderText("Interrupt the update");
        alert.setContentText("Would you like to return to the homepage?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                       FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setExam(exa);
            btAnnuler.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    void setExam(Exams exa) throws SQLException {
        this.exa = exa;
         tfSubject.getSelectionModel().select(exa.getSubject());
        SpinnerValueFactory<Integer> valueFactoryExamen = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, exa.getDuration(), 1);
        this.spDuration.setValueFactory(valueFactoryExamen);
      //  Datepick.setValue(LocalDate.parse((CharSequence) this.exa.getDate()));
      
                  subject = new ServicesTimeTable().readAllSubject();
                        tfSubject.setItems(FXCollections.observableArrayList(subject));
        System.out.println(exa);
    }
@FXML
    private void btImportOnClick(ActionEvent event) throws IOException {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(this.stage);
        if (file.isFile() && file.getName().contains(".pdf")) {
                System.out.println(file);
                FileUploader fu = new FileUploader("localhost/ProjetPi");
                String fileNameInServer = fu.upload(file.toString());
                url_pdf =  fileNameInServer;
                tfFile.setText(url_pdf);
        }
    }
}
