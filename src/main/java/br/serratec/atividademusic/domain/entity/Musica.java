package br.serratec.atividademusic.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "musica")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da música é obrigatório")
    @Size(max = 100, message = "O título não pode exceder 100 caracteres")
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    // Relacionamento Many-to-One com Artista (Muitas músicas pertencem a um artista)
    @NotNull(message = "A música deve estar associada a um artista")
    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    // Relacionamento Many-to-Many com Playlist
    // O 'mappedBy' é omitido aqui porque a Playlist é quem fará o mapeamento.
    @ManyToMany(mappedBy = "musicas")
    private Set<Playlist> playlists;


    // Construtores
    public Musica() {}

    public Musica(Long id, String titulo, Artista artista, Set<Playlist> playlists) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.playlists = playlists;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }
}
