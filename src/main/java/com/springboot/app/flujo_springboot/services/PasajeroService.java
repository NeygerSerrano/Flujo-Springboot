package com.springboot.app.flujo_springboot.services;

import com.springboot.app.flujo_springboot.models.Pasajero;
import com.springboot.app.flujo_springboot.repositories.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PasajeroService {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    public List<Pasajero> listarTodos() {
        return pasajeroRepository.findAll();
    }

    public Pasajero buscarPorId(Long id) {
        return pasajeroRepository.findById(id).orElse(null);
    }

    public void guardarPasajero(Pasajero pasajero) {
        // REGLA 1: Nombre solo letras
        if (!pasajero.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios.");
        }
        
        // REGLA 2: Cédula estrictamente numérica
        if (!pasajero.getCedula().matches("^[0-9]{6,15}$")) {
            throw new IllegalArgumentException("La cédula debe contener únicamente números.");
        }

        pasajeroRepository.save(pasajero);
    }

    public void eliminarPasajero(Long id) {
        pasajeroRepository.deleteById(id);
    }
}