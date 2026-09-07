package com.springboot.app.flujo_springboot.services;

import com.springboot.app.flujo_springboot.models.Motor;
import com.springboot.app.flujo_springboot.repositories.MotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MotorService {

    @Autowired
    private MotorRepository motorRepository;

    public List<Motor> listarTodos() {
        return motorRepository.findAll();
    }

    public Motor buscarPorId(Long id) {
        return motorRepository.findById(id).orElse(null);
    }

    public void guardarMotor(Motor motor) {
        // REGLA 1: Número de serie alfanumérico (sin espacios ni caracteres especiales)
        if (!motor.getNumero_serie().matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El número de serie no debe contener espacios ni caracteres especiales.");
        }

        // REGLA 2: El tipo de motor solo letras
        if (!motor.getTipo().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El tipo de motor solo debe contener letras (Ej. Gasolina, Eléctrico).");
        }

        // REGLA 3: Caballos de fuerza debe ser un valor numérico
        if (!motor.getCaballos_fuerza().matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Los caballos de fuerza deben ser expresados únicamente en números.");
        }

        motor.setNumero_serie(motor.getNumero_serie().toUpperCase());
        motorRepository.save(motor);
    }

    public void eliminarMotor(Long id) {
        motorRepository.deleteById(id);
    }
}