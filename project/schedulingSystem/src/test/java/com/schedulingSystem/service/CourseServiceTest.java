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
    public void getAllCourses_CheckCoursesAreRetrived() throws Exception {

        List<Course> courses = new ArrayList<>();
        courses.add(entity);

        when(courseRepository.findAll()).thenReturn(courses);
        when(modelMapper.map(isA(Course.class), any())).thenReturn(course);

        final List<CourseDto> allCourses = service.getAllCourses();

        assertNotNull(allCourses);
        assertEquals(allCourses.size(), 1);

    }

    @Test
    public void saveCourse_CheckCourseIsSave() throws Exception {

        when(modelMapper.map(isA(CourseDto.class), any())).thenReturn(entity);
        when(courseRepository.save(isA(Course.class))).thenReturn(entity);


        final String saveCourse = service.saveCourse(course);

        assertEquals(saveCourse,"OK");

    }

    @Test
    public void updateCourse_CheckCourseIsSave() throws Exception {

        when(courseRepository.findById(isA(String.class))).thenReturn(Optional.of(entity));
        when(modelMapper.map(isA(CourseDto.class), any())).thenReturn(entity);
        when(courseRepository.save(isA(Course.class))).thenReturn(entity);


        final String saveCourse = service.updateCourse(course);

        verify(courseRepository, times(1)).delete(isA(Course.class));

        assertEquals(saveCourse,"OK");

    }

    @Test
    public void deleteCourse_CheckCourseIsSave() throws Exception {

        when(courseRepository.findById(isA(String.class))).thenReturn(Optional.of(entity));

        final String saveCourse = service.deleteCourse("code1");

        verify(courseRepository, times(1)).delete(isA(Course.class));

        assertEquals(saveCourse,"OK");

    }
}