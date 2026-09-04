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
    public String guardarMotor(@ModelAttribute Motor motor) {
        motorRepository.save(motor);
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
    public String eliminarMotor(@PathVariable Long id) {
        motorRepository.deleteById(id);
        return "redirect:/motores";
    }
}