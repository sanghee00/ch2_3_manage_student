package org.fastcampus.student_management.application.course;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.domain.Course;
import org.fastcampus.student_management.domain.DayOfWeek;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.CourseRepository;

public class CourseService {
  private final CourseRepository courseRepository;
  private final StudentService studentService;

  public CourseService(CourseRepository courseRepository, StudentService studentService) {
    this.courseRepository = courseRepository;
    this.studentService = studentService;
  }

  public void registerCourse(CourseInfoDto courseInfoDto) {
    Student student = studentService.getStudent(courseInfoDto.getStudentName());
    Course course = new Course(student, courseInfoDto.getCourseName(), courseInfoDto.getFee(), courseInfoDto.getDayOfWeek(), courseInfoDto.getCourseTime());
    courseRepository.save(course);
  }

  public List<CourseInfoDto> getCourseDayOfWeek(DayOfWeek dayOfWeek) {
    // TODO: 과제 구현 부분
    List<Course> courseDayOfWeek = courseRepository.getCourseDayOfWeek(dayOfWeek);
    List<CourseInfoDto> results = new ArrayList<>();

    for (Course course : courseDayOfWeek) {
      results.add(new CourseInfoDto(course));
    }

    return new ArrayList<>(results);
  }

  // 학생, 가격
  public void changeFee(String studentName, int fee) {
    // TODO: 과제 구현 부분
    List<Course> courseListByStudent = courseRepository.getCourseListByStudent(studentName);

    for (Course course : courseListByStudent) {
      Student student = studentService.getStudent(course.getStudentName());
      courseRepository.save(new Course(student, course.getCourseName(), fee, course.getDayOfWeek(), course.getCourseTime()));
    }
  }
}
