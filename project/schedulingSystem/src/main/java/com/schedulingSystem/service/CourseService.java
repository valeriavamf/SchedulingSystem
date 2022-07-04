package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.CourseRepository;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService extends AbstractService
{
    private static final String CODE = "code";

    private static final String DESCRIPTION = "description";

    private static final String TITLE = "title";

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity getAllCourses(Map<String, String> criteria)
    {
        Course course = new Course();
        if (criteria != null)
        {
            course.setCode(criteria.get(CODE));
            course.setDescription(criteria.get(DESCRIPTION));
            course.setTitle(criteria.get(TITLE));
        }

        try
        {
            List<Course> courses = courseRepository.findAll(Example.of(course));
            return new ResponseEntity(mapCourses(courses), HttpStatus.OK);
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
    }

    public ResponseEntity getCourseByCode(String code)
    {
        if (code == null)
        {
            return badRequestEntity;
        }

        try
        {
            final Optional<Course> course = courseRepository.findById(code);
            if (course.isPresent()){
                return new ResponseEntity(mapCourseToDto(course.get()), HttpStatus.OK);
            }
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }

        return ResponseEntity.of(Optional.of(new CourseDto()));
    }

    public ResponseEntity getStudentByTitle(String title)
    {
        if (title == null)
        {
            return badRequestEntity;
        }

        try
        {
            final List<Course> courses = courseRepository.findByTitle(title);
            return new ResponseEntity(mapCourses(courses), HttpStatus.OK) ;
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
    }


    public ResponseEntity getStudentByCourse(String code)
    {
        final ResponseEntity responseEntity = getCourseByCode(code);
        if (responseEntity.getStatusCode().value() != HttpStatus.OK.value())
        {
            return responseEntity;
        }
        CourseDto course = (CourseDto) responseEntity.getBody();
        return new ResponseEntity(course.getStudents(), HttpStatus.OK);

    }

    public ResponseEntity saveCourse(CourseDto courseDto)
    {
        if (courseDto == null)
        {
            return badRequestEntity;
        }
        try
        {
            courseRepository.save(modelMapper.map(courseDto, Course.class));
        }
        catch (JpaSystemException | IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
        return ResponseEntity.ok(OK);
    }

    public ResponseEntity updateCourse(CourseDto courseDto)
    {
        if (courseDto == null || courseDto.getCode() == null)
        {
            return badRequestEntity;
        }

        final ResponseEntity deleteEntity = deleteCourse(courseDto.getCode());
        if (deleteEntity.getStatusCode().value() == HttpStatus.OK.value())
        {
            return saveCourse(courseDto);
        }

        return ResponseEntity.ok(OK);
    }

    public ResponseEntity deleteCourse(String code)
    {
        if (code == null)
        {
            return badRequestEntity;
        }
        Optional<Course> course = courseRepository.findById(code);
        if (course.isPresent())
        {
            courseRepository.delete(course.get());
            return ResponseEntity.ok(OK);
        }
        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    protected CourseDto mapCourseToDto(Course course) throws IllegalArgumentException
    {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        courseDto.setStudents(mapperStudents(course.getStudents()));
        return courseDto;
    }

    @Override
    protected StudentDto mapStudentToDto(Student student) throws IllegalArgumentException
    {
        return modelMapper.map(student, StudentDto.class);
    }
}
