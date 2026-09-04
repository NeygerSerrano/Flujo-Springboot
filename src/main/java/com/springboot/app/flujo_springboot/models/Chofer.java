package com.springboot.app.flujo_springboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Chofer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Llave primaria interna (AI)
    
    private String nombre;
    
    @Column(unique = true)
    private String cedula; // Valor único e irrepetible
    
    private String licencia;
}