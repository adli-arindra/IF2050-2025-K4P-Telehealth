package org.drpl.telebe.repository;

import org.drpl.telebe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPatientIdOrPharmacistId(Long patientId, Long pharmacistId);
}