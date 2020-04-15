/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices.grade;

import java.sql.SQLException;
import java.util.List;
import tn.esprit.binder.entities.grades.Exams;

/**
 *
 * @author Assma
 */
public interface IservicesExam<ex> {
    public List<ex> readAll()throws SQLException;
    public void addExams(Exams ex);
    public void updateEams(Exams ex) throws SQLException;
    public void deleteExams(int i) throws SQLException;
    
}
