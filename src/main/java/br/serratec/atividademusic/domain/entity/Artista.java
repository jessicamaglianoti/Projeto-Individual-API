package br.serratec.atividademusic.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome artístico é obrigatório")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
    @Column(name = "nome_artistico", length = 100, nullable = false)
    private String nomeArtistico;

   
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Musica> musicas;

   
    public Artista() {}

    public Artista(Long id, String nomeArtistico, List<Musica> musicas) {
        this.id = id;
        this.nomeArtistico = nomeArtistico;
        this.musicas = musicas;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
