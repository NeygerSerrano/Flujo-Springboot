package com.springboot.app.flujo_springboot.repositories;

import com.springboot.app.flujo_springboot.models.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Long> {
}