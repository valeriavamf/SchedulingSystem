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
public class StudentService
{
    public static final String OK = "OK";

    public static final String NOK = "NOK";

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDto> getAllStudents()
    {
        final List<Student> all = studentRepository.findAll();

        return mapperStudents(all);
    }

    public StudentDto getStudentById(Integer id)
    {
        if (id == null)
        {
            return null;
        }

        final Optional<Student> byId = studentRepository.findById(id);
        StudentDto studentDto = new StudentDto();
        if (byId.isPresent()){
            studentDto = modelMapper.map(byId.get(), StudentDto.class);
            studentDto.setCourses(mapClassToDto(byId.get().getCourses()));
        }
        return studentDto;
    }

    public List<StudentDto> getStudentByName(String name)
    {
        final List<Student> all = studentRepository.findByFirstName(name);

        return mapperStudents(all);
    }

    public List<StudentDto> getStudentByLastName(String lastname)
    {
        final List<Student> all = studentRepository.findByLastName(lastname);

        return mapperStudents(all);
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
        final Optional<Student> byId = studentRepository.findById(id);
        if (!byId.isPresent())
        {
            return NOK;
        }
        studentRepository.delete(byId.get());
        return OK;
    }

    private List<CourseDto> mapClassToDto(List<Course> courses)
    {
        List<CourseDto> result = new ArrayList<>();

        if (courses == null)
        {
            return result;
        }
        courses.forEach( aClass -> {
            result.add(modelMapper.map(aClass, CourseDto.class));
        });

        return result;
    }

    private List<StudentDto> mapperStudents(List<Student> students)
    {
        List<StudentDto> result = new ArrayList<>();

        if (students == null)
        {
            return result;
        }
        students.forEach( student -> {
            StudentDto studentDto = modelMapper.map(student, StudentDto.class);
            studentDto.setCourses(mapClassToDto(student.getCourses()));
            result.add(studentDto);
        });

        return result;
    }
}
