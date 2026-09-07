package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Motor;
import com.springboot.app.flujo_springboot.repositories.MotorRepository;
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
public class MotorController {

    @Autowired
    private MotorRepository motorRepository;

    @GetMapping("/motores")
    public String listarMotores(Model model) {
        model.addAttribute("motor", new Motor());
        model.addAttribute("listaMotores", motorRepository.findAll());
        return "motores"; 
    }

    @PostMapping("/motores/guardar")
    public String guardarMotor(@ModelAttribute Motor motor, RedirectAttributes redirectAttrs) {
        try {
            // Intentamos guardar en la base de datos
            motorRepository.save(motor);
            redirectAttrs.addFlashAttribute("mensajeExito", "Motor guardado exitosamente.");
            
        } catch (DataIntegrityViolationException e) {
            // Atrapamos el error si se intenta registrar un número de serie duplicado
            redirectAttrs.addFlashAttribute("mensajeError", "Error: El número de serie ingresado ya se encuentra registrado en el sistema.");
        }
        
        return "redirect:/motores";
    }

    @GetMapping("/motores/editar/{id}")
    public String editarMotor(@PathVariable Long id, Model model) {
        Motor motorEncontrado = motorRepository.findById(id).orElse(null);
        model.addAttribute("motor", motorEncontrado);
        model.addAttribute("listaMotores", motorRepository.findAll());
        return "motores"; 
    }

    @GetMapping("/motores/eliminar/{id}")
    public String eliminarMotor(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        motorRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Motor eliminado correctamente.");
        return "redirect:/motores";
    }
}