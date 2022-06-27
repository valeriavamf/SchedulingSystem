package com.schedulingSystem.api;

import com.schedulingSystem.dao.entity.Student;
import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import com.schedulingSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedulingSystem/student")
public class StudentController
{
    @Autowired
    StudentService service;

    @GetMapping
    public List<StudentDto> getStudent() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Integer id)
    {
        return service.getStudentById(id);
    }

    @GetMapping("/name/{name}")
    public List<StudentDto> getStudentByName(@PathVariable String name)
    {
        return service.getStudentByName(name);
    }

    @GetMapping("/lastname/{lastname}")
    public List<StudentDto> getStudentByLastName(@PathVariable String lastname)
    {
        return service.getStudentByLastName(lastname);
    }

    @GetMapping("/{id}/courses")
    public List<CourseDto> getCoursesByStudentId(@PathVariable Integer id)
    {
        return service.getCoursesByStudent(id);
    }

    @PostMapping
    public String postStudent(@RequestBody StudentDto student)
    {
        return service.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id)
    {
        return service.deleteStudent(id);
    }

    @PutMapping
    public String putStudent(@RequestBody StudentDto student)
    {
        return service.updateStudent(student);
    }
}
