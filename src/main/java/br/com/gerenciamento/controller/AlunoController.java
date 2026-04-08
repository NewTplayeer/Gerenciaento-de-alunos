package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("home/index");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @GetMapping("/inserirAlunos")
    public ModelAndView insertAlunos() {
        ModelAndView mv = new ModelAndView("Aluno/formAluno");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @PostMapping("/InsertAlunos")
    public ModelAndView inserirAluno(@Valid Aluno aluno, BindingResult br) {
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()) {
            mv.setViewName("Aluno/formAluno");
            mv.addObject("aluno", aluno);
        } else {
            alunoRepository.save(aluno);
            mv.setViewName("redirect:/alunos-ativos");
        }
        return mv;
    }

    @GetMapping("/filtro-alunos")
    public ModelAndView filtroAlunos() {
        return new ModelAndView("Aluno/filtroAlunos");
    }

    @GetMapping("/alunos-ativos")
    public ModelAndView listaAlunosAtivos() {
        ModelAndView mv = new ModelAndView("Aluno/alunos-ativos");
        mv.addObject("alunos", alunoRepository.findByStatusAtivo());
        return mv;
    }

    @GetMapping("/alunos-inativos")
    public ModelAndView listaAlunosInativos() {
        ModelAndView mv = new ModelAndView("Aluno/alunos-inativos");
        mv.addObject("alunos", alunoRepository.findByStatusInativo());
        return mv;
    }

    @PostMapping("/pesquisar-aluno")
    public ModelAndView pesquisarAluno(String nome) {
        ModelAndView mv = new ModelAndView("Aluno/pesquisa-resultado");
        mv.addObject("alunos", alunoRepository.findByNomeContainingIgnoreCase(nome));
        return mv;
    }

    @RequestMapping(value = "/calcular-media", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView calcularMediaEnade() {
        ModelAndView mv = new ModelAndView("home/index");
        Double media = alunoRepository.calcularMediaEnadeAtivos();
        mv.addObject("mediaEnade", media != null ? media : 0.0);
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @RequestMapping(value = "/alunos_media_maior_enade", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView alunosAcimaMediaEnade() {
        ModelAndView mv = new ModelAndView("Aluno/pesquisa-resultado");
        mv.addObject("alunos", alunoRepository.findAlunosAtivosAcimaMediaEnade());
        return mv;
    }
}