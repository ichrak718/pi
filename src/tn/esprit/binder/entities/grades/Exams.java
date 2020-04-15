/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.binder.entities.grades;



/**
 *
 * @author Assma
 */
public class Exams {

    private int id;
    private String subject;
    private String file;
    private int duration;
    private String date;

    public Exams(int id, String subject, String file, int duration, String date) {
        this.id = id;
        this.subject = subject;
        this.file = file;
        this.duration = duration;
        this.date = date;
    }

    public Exams(String subject, String file, int duration, String date) {
        this.subject = subject;
        this.file = file;
        this.duration = duration;
        this.date = date;
    }

    

    

    public Exams() {
    }
    
    public String getId() {
        return Integer.toString(id);
    }

    public int getIdint() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Exams{" + "id=" + id + ", subject=" + subject + ", file=" + file + ", duration=" + duration + ", date=" + date + '}';
    }

    

  
    
    
}
