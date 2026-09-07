package com.springboot.app.flujo_springboot.controllers;

import com.springboot.app.flujo_springboot.models.Motor;
import com.springboot.app.flujo_springboot.services.MotorService;
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
public class MotorController {

    @Autowired
    private MotorService motorService;

    @GetMapping("/motores")
    public String listarMotores(Model model) {
        model.addAttribute("motor", new Motor());
        model.addAttribute("listaMotores", motorService.listarTodos());
        return "motores"; 
    }

    @PostMapping("/motores/guardar")
    public String guardarMotor(@ModelAttribute Motor motor, RedirectAttributes redirectAttrs) {
        try {
            motorService.guardarMotor(motor);
            redirectAttrs.addFlashAttribute("mensajeExito", "Motor guardado exitosamente.");
            
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Validación fallida: " + e.getMessage());
            
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error: El número de serie ingresado ya se encuentra registrado.");
        }
        
        return "redirect:/motores";
    }

    @GetMapping("/motores/editar/{id}")
    public String editarMotor(@PathVariable Long id, Model model) {
        Motor motorEncontrado = motorService.buscarPorId(id);
        model.addAttribute("motor", motorEncontrado);
        model.addAttribute("listaMotores", motorService.listarTodos());
        return "motores"; 
    }

    @GetMapping("/motores/eliminar/{id}")
    public String eliminarMotor(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        motorService.eliminarMotor(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Motor eliminado correctamente.");
        return "redirect:/motores";
    }
}