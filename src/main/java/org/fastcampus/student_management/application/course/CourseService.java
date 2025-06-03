package org.fastcampus.student_management.application.course;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.domain.Course;
import org.fastcampus.student_management.domain.CourseList;
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
    Course course = new Course(
      student,
      courseInfoDto.getCourseName(),
      courseInfoDto.getFee(),
      courseInfoDto.getDayOfWeek(),
      courseInfoDto.getCourseTime()
    );
    courseRepository.save(course);
  }

  public List<CourseInfoDto> getCourseDayOfWeek(DayOfWeek dayOfWeek) {
    // TODO: 과제 구현 부분
    List<Course> courseDayOfWeek = courseRepository.getCourseDayOfWeek(dayOfWeek);

    // 내코드
    /*List<CourseInfoDto> results = new ArrayList<>();

    for (Course course : courseDayOfWeek) {
      String studentName = course.getStudentName();
      Student student = studentService.getStudent(studentName);
      if (student.isActivate()) {
        results.add(new CourseInfoDto(course));
      }
    }

    return new ArrayList<>(results);*/

    // 람다의 이점: 코드를 짧게 쓸 수 있다.
    // 내 코드 같은 경우는 for을 이용해 여러줄로 했는데 람다식 같은 경우는 한줄로 할 수 있다.
    return courseDayOfWeek.stream().map(CourseInfoDto::new).toList();
  }

  // 학생, 가격
  public void changeFee(String studentName, int fee) {
    // TODO: 과제 구현 부분
    List<Course> courses = courseRepository.getCourseListByStudent(studentName);

    /*for (Course course : courses) {
      Student student = studentService.getStudent(course.getStudentName());
      courseRepository.save(new Course(student, course.getCourseName(), fee, course.getDayOfWeek(), course.getCourseTime()));
    }*/

    CourseList courseList = new CourseList(courses);
    courseList.changeAllCoursesFee(fee);
  }
}
