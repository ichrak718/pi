/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.entities.grades;

import java.sql.Date;

/**
 *
 * @author Assma
 */
public class Grade {
private int id;
private int examname;
private Double grade;
private String teacher;
private String pupil;

    public Grade() {
    }


    public Grade(int examname, Double grade, String teacher, String pupil) {
        this.examname = examname;
        this.grade = grade;
        this.teacher = teacher;
        this.pupil = pupil;
    }

    public Grade(Double grade, String teacher, String pupil) {
        this.grade = grade;
        this.teacher = teacher;
        this.pupil = pupil;
    }

    public Grade(int id, int examname) {
        this.id = id;
        this.examname = examname;
    }

    
    
    public Grade(int id, int examname, Double grade, String teacher, String pupil) {
        this.id = id;
        this.examname = examname;
        this.grade = grade;
        this.teacher = teacher;
        this.pupil = pupil;
    }

    public Grade(int examname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return Integer.toString(id);
    }
    
    public int getIdInt() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExamname() {
        return examname;
    }

    public void setExamname(int examname) {
        this.examname = examname;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPupil() {
        return pupil;
    }

    public void setPupil(String pupil) {
        this.pupil = pupil;
    }

    @Override
    public String toString() {
        return "Grades{" + "id=" + id + ", examname=" + examname + ", grade=" + grade + ", teacher=" + teacher + ", pupil=" + pupil + '}';
    }


    
}
