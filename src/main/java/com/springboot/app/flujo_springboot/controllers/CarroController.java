package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Carro;
import com.springboot.app.flujo_springboot.repositories.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

// Importaciones vitales para el manejo de errores y alertas
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @GetMapping("/carros")
    public String listarCarros(Model model) {
        model.addAttribute("carro", new Carro());
        model.addAttribute("listaCarros", carroRepository.findAll());
        return "carros"; 
    }

    @PostMapping("/carros/guardar")
    public String guardarCarro(@ModelAttribute Carro carro, RedirectAttributes redirectAttrs) {
        try {
            // Intentamos guardar en la base de datos
            carroRepository.save(carro);
            redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo guardado exitosamente.");
            
        } catch (DataIntegrityViolationException e) {
            // Atrapamos el error si se intenta registrar una placa duplicada
            redirectAttrs.addFlashAttribute("mensajeError", "Error: La placa ingresada ya se encuentra registrada en el sistema.");
        }
        
        return "redirect:/carros"; 
    }

    // Método para cargar los datos en el formulario y editar
    @GetMapping("/carros/editar/{id}")
    public String editarCarro(@PathVariable Long id, Model model) {
        Carro carroEncontrado = carroRepository.findById(id).orElse(null);
        model.addAttribute("carro", carroEncontrado);
        model.addAttribute("listaCarros", carroRepository.findAll());
        return "carros";
    }

    // Método para eliminar un carro y enviar alerta de éxito
    @GetMapping("/carros/eliminar/{id}")
    public String eliminarCarro(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        carroRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo eliminado correctamente.");
        return "redirect:/carros";
    }
}