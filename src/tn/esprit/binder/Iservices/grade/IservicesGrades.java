/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.Iservices.grade;


import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import tn.esprit.binder.entities.grades.Grade;

/**
 *
 * @author Assma
 */
public interface IservicesGrades<Gr> {

    public List<Gr> readAll() throws SQLException;

    public void addGrades(Grade g);

    public void updateGrades(Grade g) throws SQLException;

    public void deleteGrades(int i) throws SQLException;

    public List<Gr> getsupdix();
    
    public List<Gr> getinfdix();
    
    public ObservableList<Integer> readexam() throws SQLException;
}
