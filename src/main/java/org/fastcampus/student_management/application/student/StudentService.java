package org.fastcampus.student_management.application.student;

import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.StudentRepository;

import java.util.Optional;

public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public void saveStudent(StudentInfoDto studentInfoDto) {
    Student student = new Student(studentInfoDto.getName(), studentInfoDto.getAge(), studentInfoDto.getAddress());
    studentRepository.save(student);
  }

  public Student getStudent(String name) {
    return studentRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다."));
  }

  public void activateStudent(String name) {
    // TODO: 과제 구현 부분
    toggleStudentActivation(name);
  }

  public void deactivateStudent(String name) {
    // TODO: 과제 구현 부분
    toggleStudentActivation(name);
  }

  private void toggleStudentActivation(String name) {
    Optional<Student> studentOptional = studentRepository.findByName(name);
    studentOptional.ifPresent(student ->
            studentRepository.save(new Student(student.getName(), student.getAge(), student.getAddress(), !student.isActivate()))
    );
  }
}
