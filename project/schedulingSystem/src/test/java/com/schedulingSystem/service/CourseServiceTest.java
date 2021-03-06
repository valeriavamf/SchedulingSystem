package com.schedulingSystem.service;

import com.schedulingSystem.dao.entity.Course;
import com.schedulingSystem.dao.reposirory.CourseRepository;
import com.schedulingSystem.model.CourseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseServiceTest
{

    @Mock
    CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseService service;

    private Course entity = new Course();
    private CourseDto course = new CourseDto();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        entity.setCode("code1");
        entity.setDescription("desc");
        entity.setTitle("title");

        course.setCode("code1");
        course.setDescription("desc");
        course.setTitle("title");
    }

    @Test
    public void getAllCourses_CheckCoursesAreRetrived()
    {

        List<Course> courses = new ArrayList<>();
        courses.add(entity);

        when(courseRepository.findAll((Example<Course>) any())).thenReturn(courses);
        when(modelMapper.map(isA(Course.class), any())).thenReturn(course);

        final ResponseEntity responseEntity = service.getAllCourses(null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final List<CourseDto> allCourses = (List<CourseDto>) responseEntity.getBody();

        assertNotNull(allCourses);
        assertEquals(allCourses.size(), 1);

    }

    @Test
    public void saveCourse_CheckCourseIsSave()
    {

        when(modelMapper.map(isA(CourseDto.class), any())).thenReturn(entity);
        when(courseRepository.save(isA(Course.class))).thenReturn(entity);


        final ResponseEntity responseEntity = service.saveCourse(course);

        assertEquals(responseEntity.getStatusCode().value(),HttpStatus.OK.value());

    }

    @Test
    public void updateCourse_CheckCourseIsSave()
    {

        when(courseRepository.findById(isA(String.class))).thenReturn(Optional.of(entity));
        when(modelMapper.map(isA(CourseDto.class), any())).thenReturn(entity);
        when(courseRepository.save(isA(Course.class))).thenReturn(entity);


        final ResponseEntity responseEntity = service.updateCourse(course);

        verify(courseRepository, times(1)).delete(isA(Course.class));

        assertEquals(responseEntity.getStatusCode().value(),HttpStatus.OK.value());

    }

    @Test
    public void deleteCourse_CheckCourseIsSave()
    {

        when(courseRepository.findById(isA(String.class))).thenReturn(Optional.of(entity));

        final ResponseEntity responseEntity = service.deleteCourse("code1");

        verify(courseRepository, times(1)).delete(isA(Course.class));

        assertEquals(responseEntity.getStatusCode().value(),HttpStatus.OK.value());

    }
}