package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.CourseRepository;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.schedulingSystem.service.StudentService.NOK;
import static com.schedulingSystem.service.StudentService.OK;

@Service
public class CourseService extends AbstractService
{
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CourseDto> getAllCourses()
    {
        final List<Course> courses = courseRepository.findAll();
        return mapCourses(courses);
    }

    public CourseDto getCourseByCode(String code)
    {
        if (code == null)
        {
            return null;
        }

        final Optional<Course> course = courseRepository.findById(code);
        CourseDto courseDto = new CourseDto();
        if (course.isPresent()){
            courseDto = mapCourseToDto(course.get());
        }

        return courseDto;
    }

    public List<CourseDto> getStudentByTitle(String title)
    {
        if (title == null)
        {
            return null;
        }

        final List<Course> courses = courseRepository.findByTitle(title);
        return mapCourses(courses);
    }


    public List<StudentDto> getStudentByCourse(String code)
    {
        final CourseDto courseByCode = getCourseByCode(code);
        if (courseByCode == null)
        {
            return new ArrayList<>();
        }
        else
        {
            return courseByCode.getStudents();
        }
    }

    public String saveCourse(CourseDto courseDto)
    {
        if (courseDto == null)
        {
            return NOK;
        }
        courseRepository.save(modelMapper.map(courseDto, Course.class));
        return OK;
    }

    public String updateCourse(CourseDto courseDto)
    {
        if (courseDto == null)
        {
            return NOK;
        }
        final Optional<Course> course = courseRepository.findById(courseDto.getCode());
        if (!course.isPresent())
        {
            return NOK;
        }
        courseRepository.delete(course.get());
        Course newCourse = modelMapper.map(courseDto, Course.class);
        courseRepository.save(newCourse);
        return OK;
    }

    public String deleteCourse(String id)
    {
        if (id == null)
        {
            return NOK;
        }
        final Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent())
        {
            return NOK;
        }
        courseRepository.delete(course.get());
        return OK;
    }

    @Override
    protected CourseDto mapCourseToDto(Course course)
    {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        courseDto.setStudents(mapperStudents(course.getStudents()));
        return courseDto;
    }

    @Override
    protected StudentDto mapStudentToDto(Student student)
    {
        return modelMapper.map(student, StudentDto.class);
    }
}
