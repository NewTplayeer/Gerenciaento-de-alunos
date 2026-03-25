package br.com.gerenciamento.controller;

import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/inserirAlunos")
    public ModelAndView insertAlunos(Aluno aluno) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Aluno/formAluno");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @PostMapping("/inserirAlunos")
    public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult br) {
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()) {
            mv.setViewName("Aluno/formAluno");
            mv.addObject("aluno", aluno);
        } else {
            alunoRepository.save(aluno);
            mv.setViewName("redirect:/alunos-adicionados");
        }
        return mv;
    }

    @GetMapping("/alunos-adicionados")
    public ModelAndView listagemAlunos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Aluno/listAlunos");
        mv.addObject("alunosList", alunoRepository.findAll());
        return mv;
    }

    // --- NOVA ROTA ADICIONADA AQUI ---
    // Serve para abrir a página de pesquisa quando você clica no menu
    @GetMapping("/pesquisar-aluno")
    public ModelAndView telaPesquisa() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Aluno/pesquisa-resultado"); 
        mv.addObject("ListaDeAlunos", alunoRepository.findAll()); // Mostra todos os alunos inicialmente
        return mv;
    }

    // Serve para filtrar os alunos quando você envia o formulário/modal
    @PostMapping("/pesquisar-aluno")
    public ModelAndView pesquisarAluno(@RequestParam(required = false) String nome) {
        ModelAndView mv = new ModelAndView();
        List<Aluno> lista;
        if(nome == null || nome.trim().isEmpty()) {
            lista = alunoRepository.findAll();
        } else {
            lista = alunoRepository.findByNomeContainingIgnoreCase(nome);
        }
        mv.addObject("ListaDeAlunos", lista);
        mv.setViewName("Aluno/pesquisa-resultado"); 
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Aluno/editar");
        mv.addObject("aluno", alunoRepository.getById(id));
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editar(Aluno aluno) {
        alunoRepository.save(aluno);
        return new ModelAndView("redirect:/alunos-adicionados");
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable("id") Long id) {
        alunoRepository.deleteById(id);
        return "redirect:/alunos-adicionados";
    }
}