package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Cooperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface CooperatorRepository extends JpaRepository<Cooperator, Long> {

    Page<Cooperator> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM cooperator LEFT JOIN profile ON profile.id = cooperator.profile_id " +
                                                                  "ORDER BY profile.surname ASC", nativeQuery = true)
    List<Cooperator> findAllByOrderBySurnameAsc();

    @Query(value = "SELECT * FROM cooperator LEFT JOIN profile ON profile.id = cooperator.profile_id " +
                                                                 "ORDER BY profile.surname DESC", nativeQuery = true)
    List<Cooperator> findAllByOrderBySurnameDesc();
}
