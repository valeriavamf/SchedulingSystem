package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.dao.reposirory.StudentRepository;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
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
            studentDto = createStudentDto(byId.get());
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
        studentRepository.save(createStudent(studentDto));
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
        Student newStudent = new Student();
        newStudent.setId(studentDto.getId());
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setCourses(mapDtoToClass(studentDto.getCourses()));
        studentRepository.save(newStudent);
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

    private List<Course> mapDtoToClass(List<CourseDto> courseDtos)
    {
        List<Course> result = new ArrayList<>();

        if (courseDtos == null)
        {
            return result;
        }
        courseDtos.forEach( aCourse -> {
            Course newCourse = new Course();
            newCourse.setCode(aCourse.getCode());
            newCourse.setDescription(aCourse.getDescription());
            newCourse.setTitle(aCourse.getTitle());
            result.add(newCourse);
        });

        return result;
    }

    private List<CourseDto> mapClassToDto(List<Course> courses)
    {
        List<CourseDto> result = new ArrayList<>();

        if (courses == null)
        {
            return result;
        }
        courses.forEach( aClass -> {
            CourseDto newCourse = new CourseDto();
            newCourse.setCode(aClass.getCode());
            newCourse.setDescription(aClass.getDescription());
            newCourse.setTitle(aClass.getTitle());
            result.add(newCourse);
        });

        return result;
    }


    private Student createStudent(StudentDto studentDto)
    {
        Student newStudent = new Student();

        if (studentDto != null)
        {
            newStudent.setId(studentDto.getId());
            newStudent.setFirstName(studentDto.getFirstName());
            newStudent.setLastName(studentDto.getLastName());
        }

        return newStudent;
    }

    private StudentDto createStudentDto(Student student)
    {
        StudentDto newStudent = new StudentDto();

        if (student != null)
        {
            newStudent.setId(student.getId());
            newStudent.setFirstName(student.getFirstName());
            newStudent.setLastName(student.getLastName());
        }

        return newStudent;
    }

    private List<StudentDto> mapperStudents(List<Student> students)
    {
        List<StudentDto> result = new ArrayList<>();

        if (students == null)
        {
            return result;
        }
        students.forEach( student -> {
            StudentDto studentDto = createStudentDto(student);
            studentDto.setCourses(mapClassToDto(student.getCourses()));
            result.add(studentDto);
        });

        return result;
    }
}
