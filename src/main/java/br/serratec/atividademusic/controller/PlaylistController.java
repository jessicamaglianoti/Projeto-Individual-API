package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Musica;
import br.serratec.atividademusic.domain.entity.Playlist;
import br.serratec.atividademusic.domain.entity.Usuario;
import br.serratec.atividademusic.exception.NotFoundException;
import br.serratec.atividademusic.repository.MusicaRepository;
import br.serratec.atividademusic.repository.PlaylistRepository;
import br.serratec.atividademusic.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
@Tag(name = "Playlists", description = "Endpoints para gerenciamento de playlists e músicas associadas.")
public class PlaylistController {

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private MusicaRepository musicaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	@Operation(summary = "Lista todas as playlists", description = "Retorna uma lista de todas as playlists cadastradas.")
	public ResponseEntity<List<Playlist>> listar() {
		List<Playlist> playlists = playlistRepository.findAll();
		return ResponseEntity.ok(playlists);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar playlist por ID", description = "Retorna uma playlist específica baseado no ID.")
	public ResponseEntity<Playlist> buscar(@PathVariable Long id) {

		Playlist playlist = playlistRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Playlist não encontrada com ID: " + id));

		return ResponseEntity.ok(playlist);
	}

	@PostMapping
	@Operation(summary = "Criar nova playlist e associar ao usuário", description = "Cria uma nova playlist. O body deve conter a propriedade 'usuario' com o ID de um usuário existente.")
	public ResponseEntity<Playlist> criar(@RequestBody @Valid Playlist playlist) {

		if (playlist.getUsuario() == null || playlist.getUsuario().getId() == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Long idUsuario = playlist.getUsuario().getId();
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new NotFoundException("Usuário para associação não encontrado com ID: " + idUsuario));

		playlist.setUsuario(usuario);

		playlist.setId(null);
		Playlist novaPlaylist = playlistRepository.save(playlist);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaPlaylist);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar playlist e alterar músicas (Desafio)", description = "Atualiza os dados da playlist e permite alterar a lista de músicas (adicionar/remover).")
	public ResponseEntity<Playlist> atualizar(@PathVariable Long id, @RequestBody @Valid Playlist playlistAtualizada) {

		Playlist playlist = playlistRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Playlist para atualização não encontrada com ID: " + id));

		playlist.setNome(playlistAtualizada.getNome());
		playlist.setDescricao(playlistAtualizada.getDescricao());

		if (playlistAtualizada.getMusicas() != null && !playlistAtualizada.getMusicas().isEmpty()) {
			Set<Long> idsMusicas = playlistAtualizada.getMusicas().stream().map(Musica::getId)
					.collect(Collectors.toSet());

			List<Musica> musicasEncontradas = musicaRepository.findAllById(idsMusicas);

			if (musicasEncontradas.size() != idsMusicas.size()) {

				throw new NotFoundException(
						"Uma ou mais Músicas não foram encontradas para a atualização da Playlist.");
			}

			playlist.setMusicas(Set.copyOf(musicasEncontradas));
		} else {

			playlist.setMusicas(Set.of());
		}

		Playlist playlistSalva = playlistRepository.save(playlist);
		return ResponseEntity.ok(playlistSalva);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar playlist", description = "Exclui uma playlist.")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!playlistRepository.existsById(id)) {
			throw new NotFoundException("Playlist para exclusão não encontrada com ID: " + id);
		}

		playlistRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}