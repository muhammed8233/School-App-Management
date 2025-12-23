package com.example.school_app.schoolApp.model;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String className;
    private String profileImageUrl;

    public Student(){}

    public Student(Long id, String name, String email, String className) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public Student(String name, String email, String className) {
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                '}';
    }
}
