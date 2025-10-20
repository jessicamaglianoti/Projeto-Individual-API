package br.serratec.atividademusic.repository;

import br.serratec.atividademusic.domain.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Repository para a entidade Artista.
 */
@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}