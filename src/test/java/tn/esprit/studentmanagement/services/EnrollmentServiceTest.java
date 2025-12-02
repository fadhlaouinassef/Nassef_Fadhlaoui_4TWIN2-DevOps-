package tn.esprit.studentmanagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        enrollment = new Enrollment();
        enrollment.setIdEnrollment(1L);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setGrade(15.5);
        enrollment.setStatus(Status.ACTIVE);
    }

    @Test
    void testGetAllEnrollments() {
        System.out.println("=== Test: Get All Enrollments ===");
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment, enrollment));

        List<Enrollment> result = enrollmentService.getAllEnrollments();

        System.out.println("Nombre d'inscriptions récupérées: " + result.size());
        assertEquals(2, result.size());
        verify(enrollmentRepository).findAll();
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetEnrollmentById_Success() {
        System.out.println("=== Test: Get Enrollment By Id (Success) ===");
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Enrollment result = enrollmentService.getEnrollmentById(1L);

        System.out.println("Inscription trouvée avec note: " + result.getGrade());
        assertNotNull(result);
        assertEquals(15.5, result.getGrade());
        verify(enrollmentRepository).findById(1L);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetEnrollmentById_NotFound() {
        System.out.println("=== Test: Get Enrollment By Id (Not Found) ===");
        when(enrollmentRepository.findById(999L)).thenReturn(Optional.empty());

        System.out.println("Tentative de récupération d'une inscription inexistante (ID: 999)");
        assertThrows(EntityNotFoundException.class,
            () -> enrollmentService.getEnrollmentById(999L));
        verify(enrollmentRepository).findById(999L);
        System.out.println("Exception EntityNotFoundException levée comme attendu ✓");
    }

    @Test
    void testSaveEnrollment() {
        System.out.println("=== Test: Save Enrollment ===");
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        System.out.println("Sauvegarde d'une inscription avec note: " + enrollment.getGrade());
        Enrollment result = enrollmentService.saveEnrollment(enrollment);

        System.out.println("Inscription sauvegardée avec ID: " + result.getIdEnrollment());
        assertNotNull(result);
        verify(enrollmentRepository).save(enrollment);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testDeleteEnrollment_Success() {
        System.out.println("=== Test: Delete Enrollment (Success) ===");
        when(enrollmentRepository.existsById(1L)).thenReturn(true);

        System.out.println("Suppression de l'inscription avec ID: 1");
        enrollmentService.deleteEnrollment(1L);

        verify(enrollmentRepository).existsById(1L);
        verify(enrollmentRepository).deleteById(1L);
        System.out.println("Inscription supprimée avec succès ✓");
    }

    @Test
    void testDeleteEnrollment_NotFound() {
        System.out.println("=== Test: Delete Enrollment (Not Found) ===");
        when(enrollmentRepository.existsById(999L)).thenReturn(false);

        System.out.println("Tentative de suppression d'une inscription inexistante (ID: 999)");
        assertThrows(EntityNotFoundException.class,
            () -> enrollmentService.deleteEnrollment(999L));
        verify(enrollmentRepository, never()).deleteById(anyLong());
        System.out.println("Exception EntityNotFoundException levée comme attendu ✓");
    }
}

