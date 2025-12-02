package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setIdStudent(1L);
        student.setFirstName("Ahmed");
        student.setLastName("Ben Ali");
        student.setEmail("ahmed.benali@example.com");
        student.setPhone("123456789");
        student.setDateOfBirth(LocalDate.of(2000, 1, 15));
        student.setAddress("Tunis");
    }

    @Test
    void testGetAllStudents() {
        System.out.println("=== Test: Get All Students ===");
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student, student));

        List<Student> result = studentService.getAllStudents();

        System.out.println("Nombre d'étudiants récupérés: " + result.size());
        assertEquals(2, result.size());
        verify(studentRepository).findAll();
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetStudentById_Success() {
        System.out.println("=== Test: Get Student By Id (Success) ===");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        System.out.println("Étudiant trouvé: " + result.getFirstName() + " " + result.getLastName());
        assertNotNull(result);
        assertEquals("Ahmed", result.getFirstName());
        verify(studentRepository).findById(1L);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetStudentById_NotFound() {
        System.out.println("=== Test: Get Student By Id (Not Found) ===");
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        System.out.println("Tentative de récupération d'un étudiant inexistant (ID: 999)");
        Student result = studentService.getStudentById(999L);

        System.out.println("Résultat: " + (result == null ? "null (attendu)" : "objet trouvé"));
        assertNull(result);
        verify(studentRepository).findById(999L);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testSaveStudent() {
        System.out.println("=== Test: Save Student ===");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        System.out.println("Sauvegarde de l'étudiant: " + student.getFirstName() + " " + student.getLastName());
        Student result = studentService.saveStudent(student);

        System.out.println("Étudiant sauvegardé avec ID: " + result.getIdStudent());
        assertNotNull(result);
        verify(studentRepository).save(student);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testDeleteStudent() {
        System.out.println("=== Test: Delete Student ===");
        System.out.println("Suppression de l'étudiant avec ID: 1");
        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
        System.out.println("Étudiant supprimé avec succès ✓");
    }
}

