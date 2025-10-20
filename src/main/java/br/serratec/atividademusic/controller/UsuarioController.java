package br.serratec.atividademusic.controller;

import br.serratec.atividademusic.domain.entity.Usuario;
import br.serratec.atividademusic.repository.UsuarioRepository;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários.")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	@Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
	public ResponseEntity<List<Usuario>> listar() {
	
		List<Usuario> usuarios = usuarioRepository.findAll();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico baseado no ID.")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
	
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
	
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Operation(summary = "Criar novo usuário", description = "Cria um novo usuário e o salva no banco de dados.")
	public ResponseEntity<Usuario> criar(@RequestBody @Valid Usuario usuario) {
		
		Usuario novoUsuario = usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente baseado no ID.")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid Usuario usuarioAtualizado) {
		// 1. Busca o usuário existente
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

		if (usuarioExistente.isPresent()) {
			// 2. Define o ID no objeto atualizado para garantir que o JPA faça o UPDATE e
			// não o INSERT
			usuarioAtualizado.setId(id);
			// 3. Salva o objeto atualizado
			Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);
			return ResponseEntity.ok(usuarioSalvo);
		}
		// Retorna 404 Not Found se o ID não existir
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar usuário", description = "Exclui um usuário e todos os seus dados (Playlists, etc.).")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
