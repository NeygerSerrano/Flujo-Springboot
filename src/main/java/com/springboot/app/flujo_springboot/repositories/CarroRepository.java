package com.springboot.app.flujo_springboot.repositories;

import com.springboot.app.flujo_springboot.models.Carro; // Importamos el modelo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Le indicamos a Spring que este es nuestro componente de base de datos
@Repository 
public interface CarroRepository extends JpaRepository<Carro, Long> {
    // Al heredar de JpaRepository, Spring Boot nos regala automáticamente
    // todos los métodos para guardar, listar, buscar y eliminar.
    // ¡No hay que escribir ni una sola consulta SQL!
}