package com.example.demo.repository;


import com.example.demo.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    List<Production> findAllByEmployee_Id(Long id);
}
