package br.com.gerenciamento.model;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @Size(min = 5, max = 35, message = "O Nome deve conter entre 5 a 35 caracteres")
    @NotBlank(message = "O nome não pode ser vazio")
    @NotNull
    private String nome;

    @Column(name = "email") 
    @Size(max = 100)
    @NotBlank(message = "O e-mail não pode ser vazio")
    @NotNull
    private String email;

    @Column(name = "matricula")
    @NotNull
    @Size(min = 3, message = "É necessário Gerar o número de matricula")
    private String matricula;

    @Column(name = "curso")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Curso curso;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Column(name = "turno")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Turno turno;

    // NOVO CAMPO: Nota do ENADE
    @Column(name = "nota_enade")
    private Double notaEnade;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    // Getter e Setter para a nota do ENADE
    public Double getNotaEnade() { return notaEnade; }
    public void setNotaEnade(Double notaEnade) { this.notaEnade = notaEnade; }
}