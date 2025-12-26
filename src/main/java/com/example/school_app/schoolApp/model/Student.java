package com.example.school_app.schoolApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @NotBlank(message = "name must not be empty")
    @Size(min =2, max = 50, message = "name size must be between 2 and 50")
    private String name;

    @NotBlank(message = "email must not be empty")
    @Email(message = "Please provide a valid email address")
    @Size(min = 3, max = 40, message = "email size must  between 3 and 40")
    private String email;

    @NotBlank
    @Size(min = 3, max = 20, message = "class name must be between 3 and 20")
    private String className;
    private String profileImageUrl;


    public void setEmail(String email) {
        if (email != null ) {
            this.email = email.toLowerCase().trim();
        }else {
            this.email = null;
        }
    }

    public void setName(String name) {
        if(name != null) {
            this.name = name.toLowerCase().trim();
        }else {
            this.name = null;
        }
    }

    public void setClassName(String className) {
        if(className != null) {
            this.className = className.toLowerCase().trim();
        }else {
            this.className = null;
        }
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
