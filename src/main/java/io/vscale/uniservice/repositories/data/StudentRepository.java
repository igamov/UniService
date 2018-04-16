package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM student LEFT JOIN profile ON profile.id = student.profile_id " +
                            "ORDER BY profile.surname ASC LIMIT 4 OFFSET :number", nativeQuery = true)
    List<Student> findAllByOrderBySurnameAsc(@Param("number") Long number);

    @Query(value = "SELECT * FROM student LEFT JOIN profile ON profile.id = student.profile_id " +
                            "ORDER BY profile.surname DESC LIMIT 4 OFFSET :number", nativeQuery = true)
    List<Student> findAllByOrderBySurnameDesc(@Param("number") Long number);
}
