package br.serratec.atividademusic.repository;

import br.serratec.atividademusic.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Repository para a entidade Usuario.
 * Herda JpaRepository para prover métodos CRUD prontos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos customizados podem ser adicionados aqui se necessário, 
    // mas o JpaRepository já cobre o CRUD básico.
}