package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService
{

    protected static final ResponseEntity internalErrorEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    protected static final ResponseEntity badRequestEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);

    protected static final String OK = "OK";

    protected abstract StudentDto mapStudentToDto(Student student) throws IllegalArgumentException;

    protected abstract CourseDto mapCourseToDto(Course course) throws IllegalArgumentException;

    protected List<StudentDto> mapperStudents(List<Student> students) throws IllegalArgumentException
    {
        List<StudentDto> result = new ArrayList<>();
        if (students == null)
        {
            return result;
        }
        students.forEach( student -> result.add(mapStudentToDto(student)));
        return result;
    }

    protected List<CourseDto> mapCourses(List<Course> courses) throws IllegalArgumentException
    {
        List<CourseDto> result = new ArrayList<>();
        if (courses == null)
        {
            return result;
        }
        courses.forEach( course -> result.add(mapCourseToDto(course)));
        return result;
    }
}
