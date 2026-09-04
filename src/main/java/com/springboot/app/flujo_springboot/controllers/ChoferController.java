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

    // CREAR / ACTUALIZAR: Guarda los datos en la base de datos
    @PostMapping("/choferes/guardar")
    public String guardarChofer(@ModelAttribute Chofer chofer) {
        // Si el chofer no tiene ID, JPA hace un INSERT. Si ya tiene ID, hace un UPDATE.
        choferRepository.save(chofer);
        return "redirect:/choferes";
    }

    // EDITAR: Cargar los datos de un registro específico en el formulario
    @GetMapping("/choferes/editar/{id}")
    public String editarChofer(@PathVariable Long id, Model model) {
        Chofer choferEncontrado = choferRepository.findById(id).orElse(null);
        model.addAttribute("chofer", choferEncontrado); // Enviamos el chofer lleno al formulario
        model.addAttribute("listaChoferes", choferRepository.findAll()); // Mantenemos la tabla visible
        return "choferes"; // Reutilizamos la misma vista
    }

    // ELIMINAR: Borra un registro por su ID
    @GetMapping("/choferes/eliminar/{id}")
    public String eliminarChofer(@PathVariable Long id) {
        choferRepository.deleteById(id);
        return "redirect:/choferes";
    }
}