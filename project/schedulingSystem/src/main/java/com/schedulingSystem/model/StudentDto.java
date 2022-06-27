package com.schedulingSystem.model;

import java.util.List;

public class StudentDto
{
    private Integer id;

    private String firstName;

    private String lastName;

    private List<CourseDto> courses;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public List<CourseDto> getCourses()
    {
        return courses;
    }

    public void setCourses(List<CourseDto> courses)
    {
        this.courses = courses;
    }
}
