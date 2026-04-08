package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT AVG(a.notaEnade) FROM Aluno a WHERE a.status = 'ATIVO'")
    Double calcularMediaEnadeAtivos();

    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' AND a.notaEnade >= " +
           "(SELECT AVG(al.notaEnade) FROM Aluno al WHERE al.status = 'ATIVO')")
    List<Aluno> findAlunosAtivosAcimaMediaEnade();

    List<Aluno> findByNomeContainingIgnoreCase(String nome);
    
    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO'")
    List<Aluno> findByStatusAtivo();

    @Query("SELECT a FROM Aluno a WHERE a.status = 'INATIVO'")
    List<Aluno> findByStatusInativo();
}