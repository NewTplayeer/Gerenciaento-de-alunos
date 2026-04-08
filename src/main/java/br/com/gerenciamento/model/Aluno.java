package br.com.gerenciamento.model;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O curso deve ser preenchido")
    private Curso curso;

    @NotBlank(message = "A matrícula deve ser preenchida")
    private String matricula;

    @NotBlank(message = "O nome deve ser preenchido")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status deve ser preenchido")
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O turno deve ser preenchido")
    private Turno turno;

    @NotBlank(message = "O email deve ser preenchido")
    @Email(message = "Email inválido")
    private String email;

    private Double notaEnade;
}