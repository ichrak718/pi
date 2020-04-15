/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.services.gradeservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.binder.Iservices.grade.IservicesExam;
import tn.esprit.binder.entities.grades.Exams;
import tn.esprit.binder.utils.MyConnection;

/**
 *
 * @author Assma
 */
public class ServicesExam implements IservicesExam<Exams>{
    
    private Connection cnx;
    private Statement ste;

    public ServicesExam() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public List<Exams> readAll() throws SQLException {
        ObservableList<Exams> arr = FXCollections.observableArrayList();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * from exam");
        while (rs.next()) {
            int id = rs.getInt(1);
            String subject = rs.getString("subject");
            String file = rs.getString("file");
            int duration = rs.getInt(4);
            String date = rs.getString(5);
            Exams ex = new Exams(id, subject, file, duration, date);
            arr.add(ex);
        }
        return arr;
    }

    @Override
    public void addExams(Exams ex) {
        try {
            ste = cnx.createStatement();
            String requeteInsert = "INSERT INTO exam VALUES (NULL, '"+ ex.getSubject()+ "', '"+ ex.getFile()+ "', '"+ ex.getDuration()+"', '"+ ex.getDate() + "')";
            ste.executeUpdate(requeteInsert);
        } catch (SQLException ex1) {
            System.out.println(ex1.getMessage());
        }
            
        
    }

    @Override
    public void updateEams(Exams ex) throws SQLException {
      ste = cnx.createStatement();
        String req = "update exam set subject = '"
                + ex.getSubject()
                + "',file='"
                + ex.getFile()
                + "',duration='"
                + ex.getDuration()
                + "',date='"
                + ex.getDate()
                + "' where id = '"
                + ex.getId() + "'";
        if (ste.executeUpdate(req) == 1) {
            System.out.println("modification effectué");
        }
    }

    @Override
    public void deleteExams(int i) {
        try {
            String req = "DELETE FROM Exam WHERE id=" + i;
            PreparedStatement pt;
            pt = cnx.prepareStatement(req);
            pt.executeUpdate(req);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Exams> search(String t) throws SQLException {
        List<Exams> Grades = new ArrayList<>();
        Grades = readAll();
        List<Exams> Gr = Grades.stream()
                .filter(a -> a.getSubject().contains(t)|| a.getFile().contains(t)|| a.getDate().contains(t))
                .collect(Collectors.toList());
        return Gr;
    }

}
