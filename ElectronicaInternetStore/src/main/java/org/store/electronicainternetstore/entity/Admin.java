package org.store.electronicainternetstore.entity;

public class Admin {
    private String email;
    private String password;
    private String surname;
    private String name;

    public Admin(String email, String password, String surname, String name) {
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
