package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Artista;
import br.serratec.atividademusic.repository.ArtistaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Artista> artista = artistaRepository.findById(id);

        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar novo artista", description = "Cria um novo artista e o salva no banco de dados.")
    public ResponseEntity<Artista> criar(@RequestBody @Valid Artista artista) {
        Artista novoArtista = artistaRepository.save(artista);
        // Retorna 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(novoArtista);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar artista", description = "Atualiza os dados de um artista existente baseado no ID.")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody @Valid Artista artistaAtualizado) {
        Optional<Artista> artistaExistente = artistaRepository.findById(id);

        if (artistaExistente.isPresent()) {
            
            artistaAtualizado.setId(id);
            Artista artistaSalvo = artistaRepository.save(artistaAtualizado);
            return ResponseEntity.ok(artistaSalvo);
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar artista", description = "Exclui um artista.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
