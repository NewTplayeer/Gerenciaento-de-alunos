package br.com.gerenciamento.controller;

import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/inserirAlunos")
    public ModelAndView insertAlunos(Aluno aluno) {
        ModelAndView modelAndView = new ModelAndView("Aluno/formAluno");
        modelAndView.addObject("aluno", new Aluno());
        return modelAndView;
    }

    @PostMapping("/InsertAlunos")
    public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("Aluno/formAluno");
            modelAndView.addObject("aluno", aluno);
        } else {
            alunoRepository.save(aluno);
            modelAndView.setViewName("redirect:/alunos-ativos");
        }
        return modelAndView;
    }

    @GetMapping("/alunos-adicionados")
    public ModelAndView listagemAlunos() {
        ModelAndView modelAndView = new ModelAndView("Aluno/listAlunos");
        modelAndView.addObject("alunosList", alunoRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("Aluno/editar");
        Aluno aluno = alunoRepository.findById(id).orElse(new Aluno());
        modelAndView.addObject("aluno", aluno);
        return modelAndView;
    }

    @PostMapping("/editar")
    public ModelAndView editar(Aluno aluno) {
        alunoRepository.save(aluno);
        return new ModelAndView("redirect:/alunos-ativos");
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable("id") Long id) {
        alunoRepository.deleteById(id);
        return "redirect:/alunos-ativos";
    }

    @GetMapping("/filtro-alunos")
    public ModelAndView filtroAlunos() {
        return new ModelAndView("Aluno/filtroAlunos");
    }

    @GetMapping("/alunos-ativos")
    public ModelAndView listaAlunosAtivos() {
        ModelAndView modelAndView = new ModelAndView("Aluno/alunos-ativos");
        modelAndView.addObject("alunosAtivos", alunoRepository.findByStatusAtivo());
        return modelAndView;
    }

    @GetMapping("/alunos-inativos")
    public ModelAndView listaAlunosInativos() {
        ModelAndView modelAndView = new ModelAndView("Aluno/alunos-inativos");
        modelAndView.addObject("alunosInativos", alunoRepository.findByStatusInativo());
        return modelAndView;
    }

    @PostMapping("/pesquisar-aluno")
    public ModelAndView pesquisarAluno(@RequestParam(required = false) String nome) {
        ModelAndView modelAndView = new ModelAndView("Aluno/pesquisa-resultado");
        List<Aluno> listaAlunos;
        if(nome == null || nome.trim().isEmpty()) {
            listaAlunos = alunoRepository.findAll();
        } else {
            listaAlunos = alunoRepository.findByNomeContainingIgnoreCase(nome);
        }
        modelAndView.addObject("alunos", listaAlunos);
        return modelAndView;
    }

    @GetMapping("/calcular-media")
    public ModelAndView calcularMediaEnade() {
        ModelAndView mv = new ModelAndView("home/index");
        Double media = alunoRepository.calcularMediaEnadeAtivos();
        mv.addObject("mediaEnade", media != null ? media : 0.0);
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @GetMapping("/alunos_media_maior_enade")
    public ModelAndView alunosAcimaMediaEnade() {
        ModelAndView mv = new ModelAndView("Aluno/pesquisa-resultado");
        mv.addObject("alunos", alunoRepository.findAlunosAtivosAcimaMediaEnade());
        return mv;
    }
}