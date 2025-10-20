package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Musica;
import br.serratec.atividademusic.domain.entity.Playlist;
import br.serratec.atividademusic.repository.MusicaRepository;
import br.serratec.atividademusic.repository.PlaylistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping
    @Operation(summary = "Lista todas as playlists", description = "Retorna uma lista de todas as playlists cadastradas.")
    public ResponseEntity<List<Playlist>> listar() {
        List<Playlist> playlists = playlistRepository.findAll();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar playlist por ID", description = "Retorna uma playlist específica baseado no ID.")
    public ResponseEntity<Playlist> buscar(@PathVariable Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);

        if (playlist.isPresent()) {
            return ResponseEntity.ok(playlist.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar nova playlist", description = "Cria uma nova playlist e a associa a um usuário existente.")
    public ResponseEntity<Playlist> criar(@RequestBody @Valid Playlist playlist) {
        
        Playlist novaPlaylist = playlistRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPlaylist);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar playlist (incluindo músicas)", 
               description = "Atualiza nome da playlist e sobrescreve a lista de músicas. É necessário enviar a lista completa de músicas no Body.")
    public ResponseEntity<Playlist> atualizar(@PathVariable Long id, @RequestBody @Valid Playlist playlistAtualizada) {
        Optional<Playlist> playlistExistente = playlistRepository.findById(id);

        if (playlistExistente.isPresent()) {
            Playlist playlist = playlistExistente.get();
            
       
            playlist.setNome(playlistAtualizada.getNome());
            playlist.setUsuario(playlistAtualizada.getUsuario()); 

          
            if (playlistAtualizada.getMusicas() != null && !playlistAtualizada.getMusicas().isEmpty()) {
                Set<Long> idsMusicas = playlistAtualizada.getMusicas().stream()
                                                            .map(Musica::getId)
                                                            .collect(Collectors.toSet());
                
                
                List<Musica> musicasEncontradas = musicaRepository.findAllById(idsMusicas);
                
              
                if (musicasEncontradas.size() != idsMusicas.size()) {
                
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                
                playlist.setMusicas(Set.copyOf(musicasEncontradas));
            } else {
                 
                 playlist.setMusicas(null);
            }

            Playlist playlistSalva = playlistRepository.save(playlist);
            return ResponseEntity.ok(playlistSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar playlist", description = "Exclui uma playlist.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (playlistRepository.existsById(id)) {
            playlistRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
