package br.serratec.atividademusic.repository;

import br.serratec.atividademusic.domain.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Repository para a entidade Musica.
 */
@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
