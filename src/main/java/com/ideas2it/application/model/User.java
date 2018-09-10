package com.ideas2it.application.model;

/**
 * <p>
 * User class is a POJO class which has getters and setters for
 * all necessary user details for login authentication
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class User {
    private Integer id;
    private String email;
    private String password;
    public User() {
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
}
