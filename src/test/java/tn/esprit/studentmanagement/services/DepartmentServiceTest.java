package tn.esprit.studentmanagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setIdDepartment(1L);
        department.setName("Informatique");
        department.setLocation("Building A");
        department.setPhone("71234567");
        department.setHead("Dr. Mohamed Salah");
    }

    @Test
    void testGetAllDepartments() {
        System.out.println("=== Test: Get All Departments ===");
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department, department));

        List<Department> result = departmentService.getAllDepartments();

        System.out.println("Nombre de départements récupérés: " + result.size());
        assertEquals(2, result.size());
        verify(departmentRepository).findAll();
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetDepartmentById_Success() {
        System.out.println("=== Test: Get Department By Id (Success) ===");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1L);

        System.out.println("Département trouvé: " + result.getName());
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
        verify(departmentRepository).findById(1L);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testGetDepartmentById_NotFound() {
        System.out.println("=== Test: Get Department By Id (Not Found) ===");
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        System.out.println("Tentative de récupération d'un département inexistant (ID: 999)");
        assertThrows(EntityNotFoundException.class,
            () -> departmentService.getDepartmentById(999L));
        verify(departmentRepository).findById(999L);
        System.out.println("Exception EntityNotFoundException levée comme attendu ✓");
    }

    @Test
    void testSaveDepartment() {
        System.out.println("=== Test: Save Department ===");
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        System.out.println("Sauvegarde du département: " + department.getName());
        Department result = departmentService.saveDepartment(department);

        System.out.println("Département sauvegardé avec ID: " + result.getIdDepartment());
        assertNotNull(result);
        verify(departmentRepository).save(department);
        System.out.println("Test réussi ✓");
    }

    @Test
    void testDeleteDepartment() {
        System.out.println("=== Test: Delete Department ===");
        System.out.println("Suppression du département avec ID: 1");
        departmentService.deleteDepartment(1L);

        verify(departmentRepository).deleteById(1L);
        System.out.println("Département supprimé avec succès ✓");
    }
}

