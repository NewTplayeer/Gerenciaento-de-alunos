package br.com.gerenciamento.controller;

import br.com.gerenciamento.exception.ServiceExc;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
public class UsuarioController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login/login"); 
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("usuario", new Usuario());
        mv.setViewName("login/cadastro");
        return mv;
    }

    @PostMapping("/salvarUsuario")
    public ModelAndView salvar(Usuario usuario) throws Exception {
        serviceUsuario.salvarUsuario(usuario);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/login")
    public ModelAndView efetuarLogin(@Valid Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceExc {
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()) {
            mv.setViewName("login/login");
            return mv;
        }

        Usuario userLogin = serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha()));
        
        if(userLogin == null) {
            mv.setViewName("login/login");
            mv.addObject("msg","Usuário não encontrado.");
            return mv;
        } else {
            session.setAttribute("usuarioLogado", userLogin);
            return new ModelAndView("redirect:/index"); 
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}