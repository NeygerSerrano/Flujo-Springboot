package com.springboot.app.flujo_springboot.services;

import com.springboot.app.flujo_springboot.models.Chofer;
import com.springboot.app.flujo_springboot.repositories.ChoferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChoferService {

    @Autowired
    private ChoferRepository choferRepository;

    public List<Chofer> listarTodos() {
        return choferRepository.findAll();
    }

    public Chofer buscarPorId(Long id) {
        return choferRepository.findById(id).orElse(null);
    }

    public void guardarChofer(Chofer chofer) {
        // REGLA 1: Nombre solo letras
        if (!chofer.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios.");
        }

        // REGLA 2: Cédula estrictamente numérica (al menos 6 dígitos)
        if (!chofer.getCedula().matches("^[0-9]{6,15}$")) {
            throw new IllegalArgumentException("La cédula debe contener únicamente números (entre 6 y 15 dígitos).");
        }

        // REGLA 3: Licencia alfanumérica (letras, números y tal vez un guion)
        if (!chofer.getLicencia().matches("^[a-zA-Z0-9-]+$")) {
            throw new IllegalArgumentException("La licencia contiene caracteres no permitidos.");
        }

        chofer.setLicencia(chofer.getLicencia().toUpperCase());
        choferRepository.save(chofer);
    }

    public void eliminarChofer(Long id) {
        choferRepository.deleteById(id);
    }
}