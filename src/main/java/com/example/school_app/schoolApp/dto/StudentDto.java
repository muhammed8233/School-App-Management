package com.example.school_app.schoolApp.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {

    private Long studentId;
    private String name;
    private String email;
    private String className;
    private MultipartFile profileImage;
    private String profileImageUrl;

    @Override
    public String toString() {
        return "StudentDto{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                ", imageUrl='" + profileImageUrl + '\'' +
                '}';
    }
}
