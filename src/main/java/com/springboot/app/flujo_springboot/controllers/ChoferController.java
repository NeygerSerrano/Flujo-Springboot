package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Chofer;
// IMPORTANTE: Ahora importamos el Service en lugar del Repository
import com.springboot.app.flujo_springboot.services.ChoferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class ChoferController {

    // INYECCIÓN DE DEPENDENCIAS: Llamamos al Servicio
    @Autowired
    private ChoferService choferService;

    @GetMapping("/choferes")
    public String listarChoferes(Model model) {
        model.addAttribute("chofer", new Chofer());
        model.addAttribute("listaChoferes", choferService.listarTodos());
        return "choferes"; 
    }

    @PostMapping("/choferes/guardar")
    public String guardarChofer(@ModelAttribute Chofer chofer, RedirectAttributes redirectAttrs) {
        try {
            // Le pedimos al Servicio que intente guardar (él aplicará las reglas de negocio)
            choferService.guardarChofer(chofer);
            redirectAttrs.addFlashAttribute("mensajeExito", "Registro guardado exitosamente.");
            
        } catch (IllegalArgumentException e) {
            // ATRAPAMOS EL ERROR DE NUESTRA REGLA DE NEGOCIO (Ej: Nombre con números)
            redirectAttrs.addFlashAttribute("mensajeError", "Validación fallida: " + e.getMessage());
            
        } catch (DataIntegrityViolationException e) {
            // ATRAPAMOS EL ERROR DE BASE DE DATOS (Cédula duplicada)
            redirectAttrs.addFlashAttribute("mensajeError", "Error: La cédula ingresada ya se encuentra registrada en el sistema.");
        }
        
        return "redirect:/choferes";
    }

    @GetMapping("/choferes/editar/{id}")
    public String editarChofer(@PathVariable Long id, Model model) {
        Chofer choferEncontrado = choferService.buscarPorId(id);
        model.addAttribute("chofer", choferEncontrado); 
        model.addAttribute("listaChoferes", choferService.listarTodos()); 
        return "choferes"; 
    }

    @GetMapping("/choferes/eliminar/{id}")
    public String eliminarChofer(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        choferService.eliminarChofer(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Registro eliminado correctamente.");
        return "redirect:/choferes";
    }
}