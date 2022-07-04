package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.StudentRepository;
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
public class StudentService extends AbstractService
{
    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String LAST_NAME = "lastname";

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity getAllStudents(Map<String, String> criteria)
    {
        Student student = new Student();
        if (criteria != null)
        {
            student.setId(criteria.containsKey(ID) ? Integer.getInteger(criteria.get(ID)) : null);
            student.setLastName(criteria.get(NAME));
            student.setFirstName(criteria.get(LAST_NAME));
        }
        try
        {
            List<Student> students = studentRepository.findAll(Example.of(student));
            return new ResponseEntity(mapperStudents(students), HttpStatus.OK);
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
    }

    public ResponseEntity getStudentById(Integer id)
    {
        if (id == null)
        {
            return badRequestEntity;
        }

        try
        {
            final Optional<Student> student = studentRepository.findById(id);
            if (student.isPresent()){
                return new ResponseEntity(mapStudentToDto(student.get()), HttpStatus.OK);
            }
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
        return ResponseEntity.of(Optional.of(new StudentDto()));
    }

    public ResponseEntity getStudentByName(String name)
    {
        if (name == null)
        {
            return badRequestEntity;
        }

        try
        {
            final List<Student> students = studentRepository.findByFirstName(name);
            return new ResponseEntity(mapperStudents(students), HttpStatus.OK) ;
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
    }

    public ResponseEntity getStudentByLastName(String lastname)
    {
        if (lastname == null)
        {
            return badRequestEntity;
        }

        try
        {
            final List<Student> students = studentRepository.findByLastName(lastname);
            return new ResponseEntity(mapperStudents(students), HttpStatus.OK) ;
        }
        catch (IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }

    }

    public ResponseEntity getCoursesByStudent(Integer id)
    {
        final ResponseEntity responseEntity = getStudentById(id);
        if (responseEntity.getStatusCode().value() != HttpStatus.OK.value())
        {
            return responseEntity;
        }
        StudentDto students = (StudentDto) responseEntity.getBody();
        return new ResponseEntity(students.getCourses(), HttpStatus.OK);
    }

    public ResponseEntity saveStudent(StudentDto studentDto)
    {
        if (studentDto == null)
        {
            return badRequestEntity;
        }
        try
        {
            studentRepository.save(modelMapper.map(studentDto, Student.class));
        }
        catch (JpaSystemException | IllegalArgumentException ex)
        {
            return internalErrorEntity;
        }
        return ResponseEntity.ok(OK);
    }

    public ResponseEntity updateStudent(StudentDto studentDto)
    {
        if (studentDto == null || studentDto.getId() == null)
        {
            return badRequestEntity;
        }
        final ResponseEntity deleteEntity = deleteStudent(studentDto.getId());
        if (deleteEntity.getStatusCode().value() == HttpStatus.OK.value())
        {
            return saveStudent(studentDto);
        }

        return ResponseEntity.ok(OK);
    }

    public ResponseEntity deleteStudent(Integer id)
    {
        if (id == null)
        {
            return badRequestEntity;
        }
        final Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent())
        {
            studentRepository.delete(student.get());
            return ResponseEntity.ok(OK);
        }
        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    protected StudentDto mapStudentToDto(Student student) throws IllegalArgumentException
    {
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        studentDto.setCourses(mapCourses(student.getCourses()));
        return studentDto;
    }

    @Override
    protected CourseDto mapCourseToDto(Course course) throws IllegalArgumentException
    {
        return modelMapper.map(course, CourseDto.class);
    }
}
