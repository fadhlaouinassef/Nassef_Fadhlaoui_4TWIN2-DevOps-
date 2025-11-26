package tn.esprit.studentmanagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException; // Recommended
// Or create your own: @ResponseStatus(HttpStatus.NOT_FOUND) + extends RuntimeException

import java.util.List;

@Service
public class EnrollmentService implements IEnrollment {

    private final EnrollmentRepository enrollmentRepository;

    // Constructor injection — perfect!
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(Long idEnrollment) {
        return enrollmentRepository.findById(idEnrollment)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Enrollment not found with id: " + idEnrollment));
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long idEnrollment) {
        // Best practice: verify existence before deleting (helps with proper 404 responses)
        if (!enrollmentRepository.existsById(idEnrollment)) {
            throw new EntityNotFoundException("Enrollment not found with id: " + idEnrollment);
        }
        enrollmentRepository.deleteById(idEnrollment);
    }
}