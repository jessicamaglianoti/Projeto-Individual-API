package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.dto.request.UsuarioRequestDTO;
import br.serratec.atividademusic.domain.entity.Perfil;
import br.serratec.atividademusic.domain.entity.Usuario;
import br.serratec.atividademusic.exception.NotFoundException;
import br.serratec.atividademusic.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	@Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico baseado no ID.")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
	
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	@Operation(summary = "Criar novo usuário (Com Perfil Aninhado)", description = "Cria um novo usuário. O Body deve conter o objeto 'perfil' aninhado.")
	public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioRequestDTO dto) {


		Perfil perfil = new Perfil();
		perfil.setTelefone(dto.getPerfil().getTelefone());
		perfil.setDataNascimento(dto.getPerfil().getDataNascimento());

	
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setPerfil(perfil);

		
		perfil.setUsuario(usuario);


		Usuario novoUsuario = usuarioRepository.save(usuario);

		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente baseado no ID.")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid Usuario usuarioAtualizado) {
		
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuário para atualização não encontrado com ID: " + id));

		usuarioExistente.setNome(usuarioAtualizado.getNome());
		usuarioExistente.setEmail(usuarioAtualizado.getEmail());
	
		Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);
		return ResponseEntity.ok(usuarioSalvo);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar usuário", description = "Exclui um usuário e todos os seus dados (Playlists, etc.).")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		if (!usuarioRepository.existsById(id)) {
			throw new NotFoundException("Usuário para exclusão não encontrado com ID: " + id);
		}

		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}