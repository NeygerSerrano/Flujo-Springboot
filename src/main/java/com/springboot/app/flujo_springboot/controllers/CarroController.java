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
    public String guardarCarro(@ModelAttribute Carro carro) {
        carroRepository.save(carro);
        return "redirect:/carros"; 
    }

    // NUEVO: Método para cargar los datos en el formulario y editar
    @GetMapping("/carros/editar/{id}")
    public String editarCarro(@PathVariable Long id, Model model) {
        Carro carroEncontrado = carroRepository.findById(id).orElse(null);
        model.addAttribute("carro", carroEncontrado);
        model.addAttribute("listaCarros", carroRepository.findAll());
        return "carros";
    }

    // NUEVO: Método para eliminar un carro
    @GetMapping("/carros/eliminar/{id}")
    public String eliminarCarro(@PathVariable Long id) {
        carroRepository.deleteById(id);
        return "redirect:/carros";
    }
}