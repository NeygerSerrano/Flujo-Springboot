package com.springboot.app.flujo_springboot.repositories;

import com.springboot.app.flujo_springboot.models.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
}