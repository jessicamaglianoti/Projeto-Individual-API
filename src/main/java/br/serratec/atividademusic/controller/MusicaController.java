package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Musica;
import br.serratec.atividademusic.repository.MusicaRepository;
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
        Optional<Musica> musica = musicaRepository.findById(id);

        if (musica.isPresent()) {
            return ResponseEntity.ok(musica.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar nova música", description = "Cria uma nova música, associando-a a um artista existente.")
    public ResponseEntity<Musica> criar(@RequestBody @Valid Musica musica) {
     
        Musica novaMusica = musicaRepository.save(musica);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMusica);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar música", description = "Atualiza os dados de uma música existente baseado no ID.")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @RequestBody @Valid Musica musicaAtualizada) {
        Optional<Musica> musicaExistente = musicaRepository.findById(id);

        if (musicaExistente.isPresent()) {
            musicaAtualizada.setId(id);
            Musica musicaSalva = musicaRepository.save(musicaAtualizada);
            return ResponseEntity.ok(musicaSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar música", description = "Exclui uma música.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (musicaRepository.existsById(id)) {
            musicaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
