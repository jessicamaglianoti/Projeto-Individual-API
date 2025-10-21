package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Artista;
import br.serratec.atividademusic.exception.NotFoundException;
import br.serratec.atividademusic.repository.ArtistaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artistas")
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de artistas.")
public class ArtistaController {

	@Autowired
	private ArtistaRepository artistaRepository;

	@GetMapping
	@Operation(summary = "Lista todos os artistas", description = "Retorna uma lista de todos os artistas cadastrados.")
	public ResponseEntity<List<Artista>> listar() {
		List<Artista> artistas = artistaRepository.findAll();
		return ResponseEntity.ok(artistas);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar artista por ID", description = "Retorna um artista específico baseado no ID.")
	public ResponseEntity<Artista> buscar(@PathVariable Long id) {

		Artista artista = artistaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Artista não encontrado com ID: " + id));

		return ResponseEntity.ok(artista);
	}

	@PostMapping
	@Operation(summary = "Criar novo artista", description = "Cria um novo artista e o salva no banco de dados.")
	public ResponseEntity<Artista> criar(@RequestBody @Valid Artista artista) {

		artista.setId(null);
		Artista novoArtista = artistaRepository.save(artista);

		return ResponseEntity.status(HttpStatus.CREATED).body(novoArtista);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar artista", description = "Atualiza os dados de um artista existente baseado no ID.")
	public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody @Valid Artista artistaAtualizado) {

		Artista artistaExistente = artistaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Artista para atualização não encontrado com ID: " + id));

		artistaExistente.setNomeArtistico(artistaAtualizado.getNomeArtistico());

		Artista artistaSalvo = artistaRepository.save(artistaExistente);
		return ResponseEntity.ok(artistaSalvo);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar artista", description = "Exclui um artista.")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!artistaRepository.existsById(id)) {
			throw new NotFoundException("Artista para exclusão não encontrado com ID: " + id);
		}

		artistaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}