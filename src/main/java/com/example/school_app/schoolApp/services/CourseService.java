package com.example.school_app.schoolApp.services;

import com.example.school_app.schoolApp.exception.CourseAlreadyExistException;
import com.example.school_app.schoolApp.exception.CourseNotFoundException;
import com.example.school_app.schoolApp.model.Course;
import com.example.school_app.schoolApp.repository.CourseRepository;
import com.example.school_app.schoolApp.dto.CourseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseService implements CourseServiceInterface {
    @Autowired
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDto> getStudentCourse(){
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDto = new ArrayList<>();

        for (Course course : courses){
            CourseDto courseDto1 = new CourseDto();
            courseDto1.setCourseId(course.getId());
            courseDto1.setCourseName(course.getCourseName());
            courseDto1.setCourseCode(course.getCourseCode());

            courseDto.add(courseDto1);
        }

        return courseDto;
    }

    @Override
    public CourseDto addNewCourse(CourseDto courseDto){
        if (courseDto.getCourseName() == null || courseDto.getCourseName().isBlank()) {
            throw new CourseNotFoundException("Course name cannot be blank");
        }

        boolean exists = courseRepository.existsByCourseCode(courseDto.getCourseCode());
        if(exists){
            throw new CourseAlreadyExistException("Course already exist");
        }
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setCourseCode(courseDto.getCourseCode());
       Course savedCourse = courseRepository.save(course);

       CourseDto courseDto1 = new CourseDto();
       courseDto1.setCourseId(savedCourse.getId());
       courseDto1.setCourseName(savedCourse.getCourseName());
       courseDto1.setCourseCode(savedCourse.getCourseCode());

       return courseDto1;
    }

    @Transactional
    @Override
    public List<CourseDto> saveAllCourses(List<CourseDto> courseDtoList) {
        if (courseDtoList == null || courseDtoList.isEmpty()) {
            throw new CourseNotFoundException("Course list cannot be empty.");
        }

        List<Course> courses = new ArrayList<>();
        for (CourseDto dto : courseDtoList) {
            if(courseRepository.existsByCourseCode(dto.getCourseCode())){
                throw new CourseAlreadyExistException("course already exist");
            }
            Course course = new Course(dto.getCourseName(), dto.getCourseCode());
            courses.add(course);
        }

        List<Course> savedCourses = courseRepository.saveAll(courses);

        List<CourseDto> dtos = new ArrayList<>();
        for (Course savedCourse : savedCourses) {
            CourseDto dto = new CourseDto();
            dto.setCourseId(savedCourse.getId());
            dto.setCourseName(savedCourse.getCourseName());
            dto.setCourseCode(savedCourse.getCourseCode());
            dtos.add(dto);
        }

        return dtos;
    }


    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with Id " + id + " not found"));
    }

    public String findCodeById(Long id) {
        return getCourseById(id).getCourseCode();
    }

    @Override
    public List<CourseDto> getAllCoursesAsDto() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDto = new ArrayList<>();
        for (Course course : courses) {
            courseDto.add(new CourseDto(course.getCourseName(), course.getCourseCode()));
        }

        return courseDto;
    }
}


