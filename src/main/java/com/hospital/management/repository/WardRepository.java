package com.hospital.management.repository;

import com.hospital.management.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByWardType(String wardType);
    List<Ward> findByActive(boolean active);
    boolean existsByWardName(String wardName);
}
