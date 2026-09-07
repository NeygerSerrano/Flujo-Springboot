package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Chofer;
import com.springboot.app.flujo_springboot.repositories.ChoferRepository;
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
public class ChoferController {

    @Autowired
    private ChoferRepository choferRepository;

    // LEER: Mostrar la lista y el formulario vacío para crear
    @GetMapping("/choferes")
    public String listarChoferes(Model model) {
        model.addAttribute("chofer", new Chofer());
        model.addAttribute("listaChoferes", choferRepository.findAll());
        return "choferes"; 
    }

    // CREAR / ACTUALIZAR: Guarda los datos y maneja el error de cédula duplicada
    @PostMapping("/choferes/guardar")
    public String guardarChofer(@ModelAttribute Chofer chofer, RedirectAttributes redirectAttrs) {
        try {
            // Intentamos guardar en la base de datos
            choferRepository.save(chofer);
            redirectAttrs.addFlashAttribute("mensajeExito", "Registro guardado exitosamente.");
            
        } catch (DataIntegrityViolationException e) {
            // Si la base de datos rechaza el registro (ej. cédula repetida), atrapamos el error aquí
            redirectAttrs.addFlashAttribute("mensajeError", "Error: La cédula ingresada ya se encuentra registrada en el sistema.");
        }
        
        return "redirect:/choferes";
    }

    // EDITAR: Cargar los datos de un registro específico en el formulario
    @GetMapping("/choferes/editar/{id}")
    public String editarChofer(@PathVariable Long id, Model model) {
        Chofer choferEncontrado = choferRepository.findById(id).orElse(null);
        model.addAttribute("chofer", choferEncontrado); 
        model.addAttribute("listaChoferes", choferRepository.findAll()); 
        return "choferes"; 
    }

    // ELIMINAR: Borra un registro por su ID y envía alerta de éxito
    @GetMapping("/choferes/eliminar/{id}")
    public String eliminarChofer(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        choferRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Registro eliminado correctamente.");
        return "redirect:/choferes";
    }
}