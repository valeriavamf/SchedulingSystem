package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.StudentRepository;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends AbstractService
{
    public static final String OK = "OK";

    public static final String NOK = "NOK";

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDto> getAllStudents()
    {
        final List<Student> students = studentRepository.findAll();

        return mapperStudents(students);
    }

    public StudentDto getStudentById(Integer id)
    {
        if (id == null)
        {
            return null;
        }

        final Optional<Student> student = studentRepository.findById(id);
        StudentDto studentDto = new StudentDto();
        if (student.isPresent()){
            studentDto = mapStudentToDto(student.get());
        }
        return studentDto;
    }

    public List<StudentDto> getStudentByName(String name)
    {
        final List<Student> students = studentRepository.findByFirstName(name);

        return mapperStudents(students);
    }

    public List<StudentDto> getStudentByLastName(String lastname)
    {
        final List<Student> students = studentRepository.findByLastName(lastname);

        return mapperStudents(students);
    }

    public List<CourseDto> getCoursesByStudent(Integer id)
    {
        final StudentDto studentById = getStudentById(id);
        if (studentById == null)
        {
            return new ArrayList<>();
        }
        else
        {
            return studentById.getCourses();
        }
    }

    public String saveStudent(StudentDto studentDto)
    {
        if (studentDto == null)
        {
            return NOK;
        }
        studentRepository.save(modelMapper.map(studentDto, Student.class));
        return OK;
    }

    public String updateStudent(StudentDto studentDto)
    {
        if (studentDto == null)
        {
            return NOK;
        }
        final Optional<Student> byId = studentRepository.findById(studentDto.getId());
        if (!byId.isPresent())
        {
            return NOK;
        }
        studentRepository.delete(byId.get());
        studentRepository.save(modelMapper.map(studentDto, Student.class));
        return OK;
    }

    public String deleteStudent(Integer id)
    {
        if (id == null)
        {
            return NOK;
        }
        final Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent())
        {
            return NOK;
        }
        studentRepository.delete(student.get());
        return OK;
    }

    protected StudentDto mapStudentToDto(Student student)
    {
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        studentDto.setCourses(mapCourses(student.getCourses()));
        return studentDto;
    }

    @Override
    protected CourseDto mapCourseToDto(Course course)
    {
        return modelMapper.map(course, CourseDto.class);
    }
}
