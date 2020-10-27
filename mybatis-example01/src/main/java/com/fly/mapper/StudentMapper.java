package com.fly.mapper;

import com.fly.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int addStudent(@Param("student") Student student);
    int delStudentById(@Param("id") int id);
    int upStudent(@Param("student")Student student);
    Student queryStudentById(@Param("id") int id);
    List<Student> queryStudent();
}
