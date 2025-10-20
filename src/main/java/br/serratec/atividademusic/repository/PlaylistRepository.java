package br.serratec.atividademusic.repository;

import br.serratec.atividademusic.domain.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Repository para a entidade Playlist.
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}