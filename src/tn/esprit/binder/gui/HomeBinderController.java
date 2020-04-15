/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.binder.entities.Classes;
import tn.esprit.binder.entities.TimeTable;
import tn.esprit.binder.entities.grades.Exams;
import tn.esprit.binder.entities.grades.Grade;
import tn.esprit.binder.services.ServicesClasses;
import tn.esprit.binder.services.gradeservice.ServicesExam;
import tn.esprit.binder.services.gradeservice.ServicesGrade;
import tn.esprit.binder.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class HomeBinderController implements Initializable {

    @FXML
    private Button btClass;
    @FXML
    private AnchorPane anchorClass;
    @FXML
    private AnchorPane anchorHome;
    @FXML
    private TableView<Classes> tableClass;
    @FXML
    private TableColumn<Classes, Integer> idClass;
    @FXML
    private TableColumn<Classes, String> nameClass;
    @FXML
    private TableColumn<Classes, String> session;
    @FXML
    private Button btAdd;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDelete;
    @FXML
    private Button btTimeTable;
    @FXML
    private Button btPupilTable;
    @FXML
    private AnchorPane anchorClassGestion;
    /**
     * Initializes the controller class.
     */
    
    
    
    
    //Declaration Ichrak 
    private Stage stage;
    public Classes classes;
    public List<Classes> classesL;
    public List<String> subject;
    TimeTable t = new TimeTable();
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.RED);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Classes gr;

    public TimeTable time = new TimeTable();

    public ObservableList<TimeTable> timeTableList;
    final List<TimeTable> timeL = new ArrayList<>();

    int id_class;
    
    // affichage Exam Asma
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
    private Button btGrade;
    @FXML
    private Button exportToXL;
    @FXML
    private TextField tfRechecher;
    @FXML
    private Button btExam;
    
    public Exams exa = new Exams();
 
    public List<Exams> Examss;
    ServicesExam exam = new ServicesExam();
    
    
    
    private Connection cnx;
    private Statement ste;
    @FXML
    private AnchorPane listexams;
    @FXML
    private AnchorPane listgrades;
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
    private Button btAjouter1;
    @FXML
    private Button btSupprimer1;
    @FXML
    private Button btModifier1;
    @FXML
    private Button exportToXL1;
    @FXML
    private RadioButton rbTous;
    @FXML
    private ToggleGroup ConsulterValidation;
    @FXML
    private RadioButton rbsupdix;
    @FXML
    private ToggleGroup ConsulterValidation1;
    @FXML
    private RadioButton rbinfdix;
    @FXML
    private ToggleGroup ConsulterValidation2;
    @FXML
    private TextField tfRechecher1;
    @FXML
    private Button btGetStatistics;
    
     ToggleGroup group = new ToggleGroup();
    public Grade gr1 = new Grade();
    public List<Grade> Grades;
    ServicesGrade ser = new ServicesGrade();

    public HomeBinderController() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //asma-----------------------------------------
        try {
            Subject.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubject()));
            file.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFile()));
            duration.setCellValueFactory(new PropertyValueFactory<Exams, Integer>("duration"));
            date.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate()));
            Examss = new ServicesExam().readAll();
            tableExams.setItems((ObservableList<Exams>) Examss);
            listexams.setVisible(false);
            
             Examname.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("examname"));
            grade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("Grade"));
            Teacher.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher()));
            pupil.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPupil()));
            Grades = new ServicesGrade().readAll();
            tableGrade.setItems((ObservableList<Grade>) Grades);
            listgrades.setVisible(false);

            
            
            
            
                            //ichrak----------------------------------------------------------------------
            idClass.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("id"));
            nameClass.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNameClass()));
            session.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSession()));

            classesL = new ServicesClasses().readAll();
            tableClass.setItems((ObservableList<Classes>) classesL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        rbTous.setToggleGroup(group);
        rbinfdix.setToggleGroup(group);
        rbsupdix.setToggleGroup(group);
 
        
        // TODO
    }    

    @FXML
    private void btClassOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(false);
        listgrades.setVisible(false);
        
    }

    @FXML
    private void btAddOnClick(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addClass.fxml"));
            Parent root;
            root = loader.load();
            AddClassController afc = loader.getController();
            afc.getStage(this.stage);
            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btUpdateOnClick(ActionEvent event) throws ParseException {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Class");
        alert.setHeaderText("Updating  "+tableClass.getSelectionModel().getSelectedItem().getNameClass());
        alert.setContentText("Would you like to update  " + tableClass.getSelectionModel().getSelectedItem().getNameClass() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                classes = tableClass.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateClass.fxml"));
                Parent root = loader.load();
                UpdateClassController classController = loader.getController();
                classController.setClass(classes);
                tableClass.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    @FXML
    private void btDeleteOnClick(ActionEvent event) {
                 try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Class");
            alert.setHeaderText("Delete  " + tableClass.getSelectionModel().getSelectedItem().getNameClass());
            alert.setContentText("Would you like to delete " + tableClass.getSelectionModel().getSelectedItem().getNameClass()+ "?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.get() == ButtonType.OK) {
                new ServicesClasses().deleteClass(tableClass.getSelectionModel().getSelectedItem().getId());
                classesL = new ServicesClasses().readAll();
                tableClass.getItems().clear();
                tableClass.getItems().addAll(classesL);
            }
            
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btTimeTableOnClick(ActionEvent event) throws ParseException, SQLException {
                       

     try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addtimeTable.fxml"));

            Parent root = loader.load();
            AddTimeTableController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


        
    }

    @FXML
    private void btPupilsOnClick(ActionEvent event) throws ParseException, SQLException {
                     try {

            classes = tableClass.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestionPupil.fxml"));

            Parent root = loader.load();
            GestionPupilController ugc = loader.getController();

            ugc.setGrade(classes);

            tableClass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    

    }

        public void getStage(Stage stage) {
        this.stage = stage;
    }

        
            
     public void setTimeTable(Classes c) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);
       
    }
          public void setPupil(Classes c) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(true);
        anchorHome.setVisible(false);
       
    }
          
          
          
          
          //------------------------------------Asma-------------------------------------------------------

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

    

    
    @FXML
    private void btExportOnClick(ActionEvent event) {
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

@FXML
    public void btExportOnClick1(ActionEvent event) {
       
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
    @FXML
    private void btExamOnClick(ActionEvent event) {
     
         anchorHome.setVisible(false);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(true);
        listgrades.setVisible(false);
    }
    
    @FXML
    private void btGradeOnClick(ActionEvent event) {
        anchorHome.setVisible(false);
        anchorClassGestion.setVisible(true);
        listexams.setVisible(false);
        listgrades.setVisible(true);
      
    }
     public void actualiserTable1() {
        tableGrade.getItems().clear();
        tableGrade.getItems().addAll(FXCollections.observableList(Grades));
    }
    public void actualiserTable() {
        tableExams.getItems().clear();
        tableExams.getItems().addAll(FXCollections.observableList(Examss));
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

    @FXML
    private void btAjouterOnClick1(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutGrade.fxml"));
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
    private void btSupprimerOnClick1(ActionEvent event) {
         try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting grade");
            alert.setHeaderText("Deleting" + tableGrade.getSelectionModel().getSelectedItem().getId());
            alert.setContentText("Are you sure you want to delete " + tableGrade.getSelectionModel().getSelectedItem().getPupil()+ "'s grade?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                new ServicesGrade().deleteGrades(tableGrade.getSelectionModel().getSelectedItem().getIdInt());
                Grades = new ServicesGrade().readAll();
                actualiserTable1();
            }
            if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btModifierOnClick1(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update grade");
        alert.setHeaderText("Updating" + gr1.getId());
        alert.setContentText("Would you like to update the grade " + tableGrade.getSelectionModel().getSelectedItem().getId()+ "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                gr1 = tableGrade.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateGrade.fxml"));
                Parent root = loader.load();
                UpdateGradeController ugc = loader.getController();
                ugc.setGrade(gr1);
                tableGrade.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
    
              public void setGrade(Grade g) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(false);
        anchorHome.setVisible(false);
        listexams.setVisible(false);
        listgrades.setVisible(true);
       
    }
                            public void setExam(Exams e) throws ParseException {

      
        anchorClass.setVisible(true);
        anchorClassGestion.setVisible(false);
        anchorHome.setVisible(false);
        listexams.setVisible(true);
        listgrades.setVisible(false);
       
    }
}
