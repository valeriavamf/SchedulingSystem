package com.schedulingSystem.api;

import com.schedulingSystem.model.StudentDto;
import com.schedulingSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/scheduling-system/students")
public class StudentController
{
    @Autowired
    StudentService service;

    @GetMapping
    public ResponseEntity getStudent(@RequestParam Map<String, String> criteria) {
        return service.getAllStudents(criteria);
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudentById(@PathVariable Integer id)
    {
        return service.getStudentById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getStudentByName(@PathVariable String name)
    {
        return service.getStudentByName(name);
    }

    @GetMapping("/last-name/{lastName}")
    public ResponseEntity getStudentByLastName(@PathVariable String lastName)
    {
        return service.getStudentByLastName(lastName);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity getCoursesByStudentId(@PathVariable Integer id)
    {
        return service.getCoursesByStudent(id);
    }

    @PostMapping
    public ResponseEntity postStudent(@RequestBody StudentDto student)
    {
        return service.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Integer id)
    {
        return service.deleteStudent(id);
    }

    @PutMapping
    public ResponseEntity putStudent(@RequestBody StudentDto student)
    {
        return service.updateStudent(student);
    }
}
