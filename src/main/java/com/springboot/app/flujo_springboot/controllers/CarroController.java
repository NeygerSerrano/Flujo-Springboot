package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Carro;
import com.springboot.app.flujo_springboot.services.CarroService;
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
public class CarroController {

    @Autowired
    private CarroService carroService; // Ahora inyectamos el Service

    @GetMapping("/carros")
    public String listarCarros(Model model) {
        model.addAttribute("carro", new Carro());
        model.addAttribute("listaCarros", carroService.listarTodos());
        return "carros"; 
    }

    @PostMapping("/carros/guardar")
    public String guardarCarro(@ModelAttribute Carro carro, RedirectAttributes redirectAttrs) {
        try {
            carroService.guardarCarro(carro);
            redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo guardado exitosamente.");
            
        } catch (IllegalArgumentException e) {
            // Atrapa el error de formato de placa
            redirectAttrs.addFlashAttribute("mensajeError", "Validación fallida: " + e.getMessage());
            
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error: La placa ingresada ya se encuentra registrada en el sistema.");
        }
        
        return "redirect:/carros"; 
    }

    @GetMapping("/carros/editar/{id}")
    public String editarCarro(@PathVariable Long id, Model model) {
        Carro carroEncontrado = carroService.buscarPorId(id);
        model.addAttribute("carro", carroEncontrado);
        model.addAttribute("listaCarros", carroService.listarTodos());
        return "carros";
    }

    @GetMapping("/carros/eliminar/{id}")
    public String eliminarCarro(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        carroService.eliminarCarro(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo eliminado correctamente.");
        return "redirect:/carros";
    }
}