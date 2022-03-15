/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author r3nb0
 */
public class UserModel {
    private int id;
    private String email;
    private String username;
    private String role;
    private boolean logged;

    public UserModel(int id, String email, String username, String role, boolean logged) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.logged = logged;
    }

    @Override
    public String toString() {
        return "UserModel{" + "id=" + id + ", email=" + email + 
                ", username=" + username + ", role=" + role + ", islogged=" + logged + '}';
    }

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean islogged) {
        this.logged = islogged;
    }

    public boolean hasRole(String role) {
       return (this.role.toLowerCase().equals(role.toLowerCase()))?true:false;
    }
    
}
