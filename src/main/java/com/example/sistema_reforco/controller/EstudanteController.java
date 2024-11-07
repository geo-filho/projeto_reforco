package com.example.sistema_reforco.controller;

import com.example.sistema_reforco.model.Estudante;
import com.example.sistema_reforco.repository.EstudanteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {

    private final EstudanteRepository estudanteRepository;

    public EstudanteController(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    @GetMapping
    public String listarEstudantes(Model model) {
        List<Estudante> estudantes = estudanteRepository.findAll();
        model.addAttribute("estudantes", estudantes);
        return "listaEstudantes";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("estudante", new Estudante());
        return "formularioEstudante";
    }

    @PostMapping
    public String salvarEstudante(@ModelAttribute Estudante estudante) {
        estudanteRepository.save(estudante);
        return "redirect:/estudantes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Estudante estudante = estudanteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Estudante inválido: " + id));
        model.addAttribute("estudante", estudante);
        return "formularioEstudante";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarEstudante(@PathVariable Long id, @ModelAttribute Estudante estudante) {
        estudante.setId(id);
        estudanteRepository.save(estudante);
        return "redirect:/estudantes";
    }

    @GetMapping("/deletar/{id}")
    public String deletarEstudante(@PathVariable Long id) {
        estudanteRepository.deleteById(id);
        return "redirect:/estudantes";
    }
}