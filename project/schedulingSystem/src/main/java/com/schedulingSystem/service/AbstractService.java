package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService
{
    protected abstract StudentDto mapStudentToDto(Student student);

    protected abstract CourseDto mapCourseToDto(Course course);

    protected List<StudentDto> mapperStudents(List<Student> students)
    {
        List<StudentDto> result = new ArrayList<>();
        if (students == null)
        {
            return result;
        }
        students.forEach( student -> result.add(mapStudentToDto(student)));
        return result;
    }

    protected List<CourseDto> mapCourses(List<Course> courses)
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
