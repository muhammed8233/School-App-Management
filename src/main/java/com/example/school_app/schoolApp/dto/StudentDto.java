package com.example.school_app.schoolApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class StudentDto {
    private Long studentId;

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

    private MultipartFile profileImage;
    private String profileImageUrl;

    public StudentDto() {}
    public StudentDto(String name, String email, String className, MultipartFile profileImage) {
        this.name = name;
        this.email = email;
        this.className = className;
        this.profileImage = profileImage;
    }

    public StudentDto(String name, String email, String className) {
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null ) {
            this.email = email.toLowerCase().trim();
        }else {
            this.email = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null) {
            this.name = name.toLowerCase().trim();
        }else {
            this.name = null;
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if(className != null) {
            this.className = className.toLowerCase().trim();
        }else {
            this.className = null;
        }
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                ", hasFile=" + (profileImage != null) +
                ", imageUrl='" + profileImageUrl + '\'' +
                '}';
    }


}
