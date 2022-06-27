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
        List<CourseDto> result = new ArrayList<>();
        final List<Course> all = courseRepository.findAll();
        if (all == null)
        {
            return result;
        }
        all.forEach( course -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setCode(course.getCode());
            courseDto.setDescription(course.getDescription());
            courseDto.setTitle(course.getTitle());
            courseDto.setStudents(mapClassToDto(course.getStudents()));
            result.add(courseDto);
        });
        return result;
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
            courseDto = createStudentDto(byId.get());
            courseDto.setCourses(mapClassToDto(byId.get().getCourses()));
        }

        return courseDto;
    }

    public String saveCourse(CourseDto courseDto)
    {
        if (courseDto == null)
        {
            return NOK;
        }
        courseRepository.save(createCourse(courseDto));
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
        Course newCourse = createCourse(courseDto);
        newCourse.setStudents(mapDtoToClass(courseDto.getStudents()));
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

    private List<Student> mapDtoToClass(List<StudentDto> students)
    {
        List<Student> result = new ArrayList<>();

        if (students == null)
        {
            return result;
        }
        students.forEach( aStudent -> {
            Student student = new Student();
            student.setId(aStudent.getId());
            student.setFirstName(aStudent.getFirstName());
            student.setLastName(aStudent.getLastName());
            result.add(student);
        });

        return result;
    }

    private List<StudentDto> mapClassToDto(List<Student> students)
    {
        List<StudentDto> result = new ArrayList<>();

        if (students == null)
        {
            return result;
        }
        students.forEach( aStudent -> {
            StudentDto student = new StudentDto();
            student.setId(aStudent.getId());
            student.setFirstName(aStudent.getFirstName());
            student.setLastName(aStudent.getLastName());
            result.add(student);
        });

        return result;
    }

    private Course createCourse(CourseDto courseDto)
    {
        Course newCourse = new Course();
        newCourse.setCode(courseDto.getCode());
        newCourse.setDescription(courseDto.getDescription());
        newCourse.setTitle(courseDto.getTitle());
        return newCourse;
    }

}
