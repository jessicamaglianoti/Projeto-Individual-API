package br.serratec.atividademusic.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

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

	@Schema(description = "Nacionalidade do artista.")
	@NotBlank(message = "A nacionalidade é obrigatória")
	@Size(max = 50, message = "A nacionalidade não pode exceder 50 caracteres")
	@Column(name = "nacionalidade", length = 50, nullable = false)
	private String nacionalidade;

	@ManyToMany(mappedBy = "artistas")
	private Set<Musica> musicas;

	public Artista() {
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

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Set<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(Set<Musica> musicas) {
		this.musicas = musicas;
	}
}