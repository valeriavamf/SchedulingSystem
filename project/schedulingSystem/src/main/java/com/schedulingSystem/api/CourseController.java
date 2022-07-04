package com.schedulingSystem.api;

import com.schedulingSystem.model.CourseDto;
import com.schedulingSystem.service.CourseService;
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
@RequestMapping("/schedulingSystem/course")
public class CourseController
{
    @Autowired
    CourseService service;

    @GetMapping
    public ResponseEntity getAllCourses(@RequestParam Map<String, String> criteria)
    {
        return service.getAllCourses(criteria);
    }

    @GetMapping("/{code}")
    public ResponseEntity getCourseByCode(@PathVariable String code)
    {
        return service.getCourseByCode(code);
    }

   @GetMapping("/title/{title}")
    public ResponseEntity getCourseByTitle(@PathVariable String title)
   {
        return service.getStudentByTitle(title);
    }

    @GetMapping("/{code}/students")
    public ResponseEntity getStudentByCourse(@PathVariable String code)
    {
        return service.getStudentByCourse(code);
    }

    @PostMapping
    public ResponseEntity postCourse(@RequestBody CourseDto course)
    {
        return service.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable String id)
    {
        return service.deleteCourse(id);
    }

    @PutMapping
    public ResponseEntity putCourse(@RequestBody CourseDto course)
    {
        return service.updateCourse(course);
    }
}
