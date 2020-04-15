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
public class Teacher {
    private int idTeacher;
    private String fullname;
    private String email;
    private String password;
    private String phonenumber;
    private String specialty;

    public Teacher() {
    }

    public Teacher(int idTeacher, String fullname, String email, String password, String phonenumber, String specialty) {
        this.idTeacher = idTeacher;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.specialty = specialty;
    }

    public Teacher(String fullname, String email, String password, String phonenumber, String specialty) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.specialty = specialty;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Teacher{" + "idTeacher=" + idTeacher + ", fullname=" + fullname + ", email=" + email + ", password=" + password + ", phonenumber=" + phonenumber + ", specialty=" + specialty + '}';
    }
    
    
}
