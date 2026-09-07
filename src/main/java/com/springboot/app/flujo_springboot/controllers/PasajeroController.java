package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Pasajero;
import com.springboot.app.flujo_springboot.repositories.PasajeroRepository;
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
public class PasajeroController {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @GetMapping("/pasajeros")
    public String listarPasajeros(Model model) {
        model.addAttribute("pasajero", new Pasajero());
        model.addAttribute("listaPasajeros", pasajeroRepository.findAll());
        return "pasajeros"; 
    }

    @PostMapping("/pasajeros/guardar")
    public String guardarPasajero(@ModelAttribute Pasajero pasajero, RedirectAttributes redirectAttrs) {
        try {
            // Intentamos guardar en la base de datos
            pasajeroRepository.save(pasajero);
            redirectAttrs.addFlashAttribute("mensajeExito", "Pasajero guardado exitosamente.");
            
        } catch (DataIntegrityViolationException e) {
            // Atrapamos el error si se intenta registrar una cédula duplicada
            redirectAttrs.addFlashAttribute("mensajeError", "Error: La cédula ingresada ya se encuentra registrada en el sistema.");
        }
        
        return "redirect:/pasajeros";
    }

    @GetMapping("/pasajeros/editar/{id}")
    public String editarPasajero(@PathVariable Long id, Model model) {
        Pasajero pasajeroEncontrado = pasajeroRepository.findById(id).orElse(null);
        model.addAttribute("pasajero", pasajeroEncontrado);
        model.addAttribute("listaPasajeros", pasajeroRepository.findAll());
        return "pasajeros"; 
    }

    @GetMapping("/pasajeros/eliminar/{id}")
    public String eliminarPasajero(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        pasajeroRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Pasajero eliminado correctamente.");
        return "redirect:/pasajeros";
    }
}