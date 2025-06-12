package org.drpl.telebe.repository;

import org.drpl.telebe.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    Page<Pharmacist> findAll(Pageable pageable);
}