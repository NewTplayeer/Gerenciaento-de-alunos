@GetMapping("/calcular-media-enade") // Rota exata do seu botão azul no index.html
public ModelAndView calcularMediaEnade() {
    ModelAndView mv = new ModelAndView("home/index");
    Double media = alunoRepository.calcularMediaEnadeAtivos();
    mv.addObject("mediaEnade", media != null ? media : 0.0);
    mv.addObject("aluno", new Aluno()); // Necessário para o modal de pesquisa não dar erro
    return mv;
}

@GetMapping("/alunos_media_maior_enade") // Rota para a pesquisa especial
public ModelAndView alunosAcimaMediaEnade() {
    ModelAndView mv = new ModelAndView("Aluno/pesquisa-resultado");
    mv.addObject("alunos", alunoRepository.findAlunosAtivosAcimaMediaEnade());
    return mv;
}