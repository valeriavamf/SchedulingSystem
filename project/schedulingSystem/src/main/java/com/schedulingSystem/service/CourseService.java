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
public class CourseService
{
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CourseDto> getAllCourses()
    {
        final List<Course> all = courseRepository.findAll();
        return mapCourses(all);
    }

    public CourseDto getCourseByCode(String code)
    {
        if (code == null)
        {
            return null;
        }

        final Optional<Course> byId = courseRepository.findById(code);
        CourseDto courseDto = new CourseDto();
        if (byId.isPresent()){
            courseDto = mapCourse(byId.get());
        }

        return courseDto;
    }

    public List<CourseDto> getStudentByTitle(String title)
    {
        List<CourseDto> result = new ArrayList<>();
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
        final Optional<Course> byId = courseRepository.findById(courseDto.getCode());
        if (!byId.isPresent())
        {
            return NOK;
        }
        courseRepository.delete(byId.get());
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
        final Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent())
        {
            return NOK;
        }
        courseRepository.delete(byId.get());
        return OK;
    }

    private List<StudentDto> mapClassToDto(List<Student> students)
    {
        List<StudentDto> result = new ArrayList<>();

        if (students == null)
        {
            return result;
        }
        students.forEach( aStudent -> {
            result.add(modelMapper.map(aStudent, StudentDto.class));
        });

        return result;
    }

    private CourseDto mapCourse(Course course)
    {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        courseDto.setStudents(mapClassToDto(course.getStudents()));
        return courseDto;
    }


    private List<CourseDto> mapCourses(List<Course> courses)
    {
        List<CourseDto> result = new ArrayList<>();
        if (courses == null)
        {
            return result;
        }
        courses.forEach( course -> result.add(mapCourse(course)));
        return result;
    }

}
