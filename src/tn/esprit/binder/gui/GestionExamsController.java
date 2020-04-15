/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.binder.entities.grades.Exams;
import tn.esprit.binder.services.gradeservice.ServicesExam;
import tn.esprit.binder.utils.MyConnection;


/**
 * FXML Controller class
 *
 * @author Assma
 */
public class GestionExamsController implements Initializable {

    @FXML
    private TableView<Exams> tableExams;
    @FXML
    private TableColumn<Exams, String> Subject;
    @FXML
    private TableColumn<Exams, String> file;
    @FXML
    private TableColumn<Exams, Integer> duration;
    @FXML
    private TableColumn<Exams, String> date;
    @FXML
    private Button btAjouter;
    @FXML
    private Button btSupprimer;
    @FXML
    private Button btModifier;
    @FXML
    private TextField tfRechecher;
    
    public Exams exa = new Exams();
 
    public List<Exams> Examss;
    ServicesExam exam = new ServicesExam();
    
    private Stage stage;
    @FXML
    private Button btGrade;
     @FXML
    private Button exportToXL;
    
    private Connection cnx;
    private Statement ste;

    public GestionExamsController() {
        cnx = MyConnection.getInstance().getCnx();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Subject.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubject()));
            file.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFile()));
            duration.setCellValueFactory(new PropertyValueFactory<Exams, Integer>("duration"));
            date.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate()));
            Examss = new ServicesExam().readAll();
            tableExams.setItems((ObservableList<Exams>) Examss);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void getStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void btAjouterOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutExam.fxml"));
            Parent root;
            root = loader.load();
            AjouterExamenController aec = loader.getController();
            aec.getStage(this.stage);
            tableExams.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btSupprimerOnClick(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Exam");
            alert.setHeaderText("Deleting " + tableExams.getSelectionModel().getSelectedItem().getId());
            alert.setContentText("Are you sure you want to delete the " + tableExams.getSelectionModel().getSelectedItem().getSubject()+ " exam?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new ServicesExam().deleteExams(tableExams.getSelectionModel().getSelectedItem().getIdint());
                Examss = new ServicesExam().readAll();
                actualiserTable();
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btModifierOnClick(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update grade");
        alert.setHeaderText("Updating" + exa.getId());
        alert.setContentText("Would you like to update the grade " + tableExams.getSelectionModel().getSelectedItem().getId()+ "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                exa = tableExams.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateExamen.fxml"));
                Parent root = loader.load();
                UpdateExamenController uec = loader.getController();
                uec.setExam(exa);
                tableExams.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }


@FXML
    public void btExportOnClick(ActionEvent event) {
       
            try {
                String query = "Select * from exam";
                ste = cnx.prepareStatement(query);
                ResultSet rs = ste.executeQuery(query);
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("exam details");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("Id");
                header.createCell(1).setCellValue("Subject");
                header.createCell(2).setCellValue("File");
                header.createCell(3).setCellValue("Duration");
                header.createCell(4).setCellValue("Date");
                
                int index = 1;
                while (rs.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString("id"));
                    row.createCell(1).setCellValue(rs.getString("subject"));
                    row.createCell(2).setCellValue(rs.getString("file"));
                    row.createCell(3).setCellValue(rs.getString("duration"));
                    row.createCell(4).setCellValue(rs.getString("date"));
                    index++;
                }
                FileOutputStream fileout = new FileOutputStream("Exams.xlsx");
                wb.write(fileout);
                fileout.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Exam details exported in Excel sheet");
                alert.showAndWait();
                ste.close();
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        
    }

    
    @FXML
    private void tfRechecherOnKeyReleased(KeyEvent event) {
        try {
            Examss = new ServicesExam().search(tfRechecher.getText());
            actualiserTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void actualiserTable() {
        tableExams.getItems().clear();
        tableExams.getItems().addAll(FXCollections.observableList(Examss));
    }

    @FXML
    private void btGradeOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionGrades.fxml"));
            Parent root;
            root =(Parent) loader.load();
            GestionGradesController afc = loader.getController();
            afc.getStage(this.stage);
            tableExams.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
