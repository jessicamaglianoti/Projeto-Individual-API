package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Musica;
import br.serratec.atividademusic.exception.NotFoundException;
import br.serratec.atividademusic.repository.MusicaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicas")
@Tag(name = "Músicas", description = "Endpoints para gerenciamento de músicas.")
public class MusicaController {

	@Autowired
	private MusicaRepository musicaRepository;

	@GetMapping
	@Operation(summary = "Lista todas as músicas", description = "Retorna uma lista de todas as músicas cadastradas.")
	public ResponseEntity<List<Musica>> listar() {
		List<Musica> musicas = musicaRepository.findAll();
		return ResponseEntity.ok(musicas);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar música por ID", description = "Retorna uma música específica baseado no ID.")
	public ResponseEntity<Musica> buscar(@PathVariable Long id) {

		Musica musica = musicaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Música não encontrada com ID: " + id));

		return ResponseEntity.ok(musica);
	}

	@PostMapping
	@Operation(summary = "Criar nova música", description = "Cria uma nova música. O body deve incluir a lista de IDs de 'artistas'.")
	public ResponseEntity<Musica> criar(@RequestBody @Valid Musica musica) {

		musica.setId(null);
		Musica novaMusica = musicaRepository.save(musica);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaMusica);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar música", description = "Atualiza os dados de uma música existente baseado no ID.")
	public ResponseEntity<Musica> atualizar(@PathVariable Long id, @RequestBody @Valid Musica musicaAtualizada) {

		Musica musicaExistente = musicaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Música para atualização não encontrada com ID: " + id));

		musicaExistente.setTitulo(musicaAtualizada.getTitulo());
		musicaExistente.setMinutos(musicaAtualizada.getMinutos());
		musicaExistente.setGenero(musicaAtualizada.getGenero());

		Musica musicaSalva = musicaRepository.save(musicaExistente);
		return ResponseEntity.ok(musicaSalva);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar música", description = "Exclui uma música.")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!musicaRepository.existsById(id)) {
			throw new NotFoundException("Música para exclusão não encontrada com ID: " + id);
		}

		musicaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}