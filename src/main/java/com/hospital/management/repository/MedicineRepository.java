package com.hospital.management.repository;

import com.hospital.management.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);
    List<Medicine> findByCategory(String category);
    List<Medicine> findByActive(boolean active);

    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity <= m.reorderLevel AND m.active = true")
    List<Medicine> findLowStockMedicines();

    long countByActive(boolean active);
}
