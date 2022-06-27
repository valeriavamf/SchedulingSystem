package com.schedulingSystem.api;

import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.model.StudentDto;
import com.schedulingSystem.service.CourseService;
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
@RequestMapping("/schedulingSystem/course")
public class CourseController
{
    @Autowired
    CourseService service;

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return service.getAllCourses();
    }

    @GetMapping("/{code}")
    public CourseDto getCourseByCode(@PathVariable String code) {
        return service.getCourseByCode(code);
    }

   @GetMapping("/title/{title}")
    public List<CourseDto> getCourseByTitle(@PathVariable String title) {
        return service.getStudentByTitle(title);
    }

    @GetMapping("/{code}/students")
    public List<StudentDto> getStudentByCourse(@PathVariable String code) {
        return service.getStudentByCourse(code);
    }

    @PostMapping
    public String postCourse(@RequestBody CourseDto course) {
        return service.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable String id) {
        return service.deleteCourse(id);
    }

    @PutMapping
    public String putCourse(@RequestBody CourseDto course) {
        return service.updateCourse(course);
    }
}
