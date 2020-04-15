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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.services.gradeservice.ServicesGrade;
import tn.esprit.binder.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Assma
 */
public class GestionGradesController implements Initializable {

    @FXML
    private TableView<Grade> tableGrade;
    @FXML
    private TableColumn<Grade, Integer> Examname;
    @FXML
    private TableColumn<Grade, Integer> grade;
    @FXML
    private TableColumn<Grade, String> Teacher;
    @FXML
    private TableColumn<Grade, String> pupil;
    @FXML
    private Button btAjouter;
    @FXML
    private Button btSupprimer;
    @FXML
    private Button btModifier;
    @FXML
    private RadioButton rbTous;
    @FXML
    private TextField tfRechecher;
    @FXML
    private RadioButton rbsupdix;
    @FXML
    private RadioButton rbinfdix;

    public Grade gr = new Grade();
    public List<Grade> Grades;
    ServicesGrade ser = new ServicesGrade();

    private Stage stage;

    ToggleGroup group = new ToggleGroup();
    @FXML
    private Button btExam;
    @FXML
    private ToggleGroup ConsulterValidation;
    @FXML
    private ToggleGroup ConsulterValidation1;
    @FXML
    private ToggleGroup ConsulterValidation2;
    @FXML
    private Button exportToXL;

    private Connection cnx;
    private Statement ste;
    @FXML
    private Button btGetStatistics;

    public GestionGradesController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Examname.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("examname"));
            grade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("Grade"));
            Teacher.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher()));
            pupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPupil()));
            Grades = new ServicesGrade().readAll();
            tableGrade.setItems((ObservableList<Grade>) Grades);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        rbTous.setToggleGroup(group);
        rbinfdix.setToggleGroup(group);
        rbsupdix.setToggleGroup(group);
    }

@FXML
    public void btExportOnClick(ActionEvent event) {
       
            try {
                String query = "Select * from Grade";
                ste = cnx.prepareStatement(query);
                ResultSet rs = ste.executeQuery(query);
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("grade details");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("Id");
                header.createCell(1).setCellValue("Exam name");
                header.createCell(2).setCellValue("Grade");
                header.createCell(3).setCellValue("Teacher");
                header.createCell(4).setCellValue("Pupil");
                int index = 1;
                while (rs.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString("id"));
                    row.createCell(1).setCellValue(rs.getString("examname"));
                    row.createCell(2).setCellValue(rs.getString("grade"));
                    row.createCell(3).setCellValue(rs.getString("teacher"));
                    row.createCell(4).setCellValue(rs.getString("pupil"));
                    index++;
                }
                FileOutputStream fileout = new FileOutputStream("Grades.xlsx");
                wb.write(fileout);
                fileout.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Grade details exported in Excel sheet");
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

        ;
    }
    public void getStage(Stage stage) {
        this.stage = stage;
    }

    
    
    @FXML
    private void btAjouterOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutGrade.fxml"));
            Parent root;
            root = loader.load();
            AjouterGradeController afc = loader.getController();
            afc.getStage(this.stage);
            tableGrade.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btSupprimerOnClick(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting grade");
            alert.setHeaderText("Deleting" + tableGrade.getSelectionModel().getSelectedItem().getId());
            alert.setContentText("Are you sure you want to delete " + tableGrade.getSelectionModel().getSelectedItem().getPupil()+ "'s grade?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new ServicesGrade().deleteGrades(tableGrade.getSelectionModel().getSelectedItem().getIdInt());
                Grades = new ServicesGrade().readAll();
                actualiserTable();
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void actualiserTable() {
        tableGrade.getItems().clear();
        tableGrade.getItems().addAll(FXCollections.observableList(Grades));
    }

    @FXML
    private void btModifierOnClick(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update grade");
        alert.setHeaderText("Updating" + gr.getId());
        alert.setContentText("Would you like to update the grade " + tableGrade.getSelectionModel().getSelectedItem().getId()+ "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                gr = tableGrade.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateGrade.fxml"));
                Parent root = loader.load();
                UpdateGradeController ugc = loader.getController();
                ugc.setGrade(gr);
                tableGrade.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
  
    

    @FXML
    private void tfRechecherOnKeyReleased(KeyEvent event) {
        try {
            Grades = new ServicesGrade().search(tfRechecher.getText());
            actualiserTable();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void rbTousOnSelect(ActionEvent event) {
        if (rbTous.isSelected() && !rbinfdix.isSelected() && !rbsupdix.isSelected()) {
            try {
                Grades = new ServicesGrade().readAll();
                actualiserTable();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @FXML
    private void rbsupdixOnSelect(ActionEvent event) {
        if (!rbTous.isSelected() && !rbinfdix.isSelected() && rbsupdix.isSelected()) {
            Grades = new ServicesGrade().getsupdix();
            actualiserTable();
        }
    }

    @FXML
    private void rbinfdixOnSelect(ActionEvent event) {
        if (!rbTous.isSelected() && rbinfdix.isSelected() && !rbsupdix.isSelected()) {
            Grades = new ServicesGrade().getinfdix();
            actualiserTable();
        }
    }

    @FXML
    private void btExamOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionExams.fxml"));
            Parent root;
            root =(Parent) loader.load();
            GestionExamsController afc = loader.getController();
            afc.getStage(this.stage);
            tableGrade.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btGetStatisticsOnClick(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChart.fxml"));
            Parent root;
            root =(Parent) loader.load();
            PieChartController afc = loader.getController();
            afc.getStage(this.stage);
            tableGrade.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    
}
