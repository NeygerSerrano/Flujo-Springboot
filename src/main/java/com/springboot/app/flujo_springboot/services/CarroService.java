package com.springboot.app.flujo_springboot.services;

import com.springboot.app.flujo_springboot.models.Carro;
import com.springboot.app.flujo_springboot.repositories.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    public Carro buscarPorId(Long id) {
        return carroRepository.findById(id).orElse(null);
    }

    public void guardarCarro(Carro carro) {
        // REGLA 1: Placa con formato estricto
        if (!carro.getPlaca().matches("^[a-zA-Z]{3}-\\d{3}$")) {
            throw new IllegalArgumentException("La placa debe tener 3 letras, un guion y 3 números (Ej. ABC-123).");
        }
        
        // REGLA 2: La marca solo debe contener letras, números y espacios (evita símbolos como @, $, %)
        if (!carro.getMarca().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("La marca solo debe contener letras y números.");
        }

        // REGLA 3: El modelo debe ser estrictamente un número de 4 dígitos
        if (!carro.getModelo().matches("^\\d{4}$")) {
            throw new IllegalArgumentException("El modelo debe ser un año válido de 4 dígitos (Ej. 2024).");
        }

        // Normalizamos los datos antes de guardar
        carro.setPlaca(carro.getPlaca().toUpperCase());
        carro.setMarca(carro.getMarca().toUpperCase());

        carroRepository.save(carro);
    }

    public void eliminarCarro(Long id) {
        carroRepository.deleteById(id);
    }
}