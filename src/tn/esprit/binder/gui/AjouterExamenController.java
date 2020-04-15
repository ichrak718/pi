/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import tn.esprit.binder.services.ServicesTimeTable;
import tn.esprit.binder.services.gradeservice.ServicesExam;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class AjouterExamenController implements Initializable {

    @FXML
    private AnchorPane anchorAffiches;
    @FXML
    private ComboBox<String> tfSubject;
    @FXML
    private Button btEnvoyer;
    @FXML
    private Button btAnnuler;
    @FXML
    private Label error;
    @FXML
    private Label reussi;
    @FXML
    private Spinner<Integer> spduration;
    @FXML
    private DatePicker Datepick;

    private Stage stage;
    @FXML
    private TextField tfFile;
    @FXML
    private Button btImport;
    private String url_pdf;
        public List<String> subject;
        Exams exa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactoryDuration = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,4,0,1);
        this.spduration.setValueFactory(valueFactoryDuration);
        try {
            subject = new ServicesTimeTable().readAllSubject();
                        tfSubject.setItems(FXCollections.observableArrayList(subject));

        } catch (SQLException ex) {
            Logger.getLogger(AjouterExamenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }    

    @FXML
    private void btAjouterOnClick(ActionEvent event) throws ParseException, IOException {
        Exams exa;
        String subject = tfSubject.getSelectionModel().getSelectedItem();

        String file = tfFile.getText();
        Integer duration = (Integer) spduration.getValue();
        Date d = Date.valueOf(Datepick.getValue().toString());
        exa = new Exams(subject,file, duration, d.toString());
        new ServicesExam().addExams(exa);
         reussi.setText("Add successful");
                               FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setExam(exa);
            btEnvoyer.getScene().setRoot(root);
        
    }

    @FXML
    private void btAnnulerOnClick(ActionEvent event) throws ParseException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return");
            alert.setHeaderText("Interrupt adding");
            alert.setContentText("Would you like to go back to the homepage? ");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                       FXMLLoader loader = new FXMLLoader(getClass().getResource("homeBinder.fxml"));

            Parent root = loader.load();
            HomeBinderController ugc = loader.getController();

            ugc.setExam(exa);
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
