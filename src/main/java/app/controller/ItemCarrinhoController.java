package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Carrinho;
import app.entity.ItemCarrinho;
import app.service.ItemCarrinhoService;

@RequestMapping("/api/itemcarrinho")
@RestController
@CrossOrigin(origins = "*")
public class ItemCarrinhoController {

	@Autowired
	private ItemCarrinhoService itemCarrinhoService;

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody ItemCarrinho itemCarrinho) {
		try {
			// Garante que o carrinho associado ao item está salvo
			if (itemCarrinho.getCarrinho() == null) {
				return new ResponseEntity<>("Carrinho não associado ao item", HttpStatus.BAD_REQUEST);
			}

			String mensagem = this.itemCarrinhoService.save(itemCarrinho);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Erro ao salvar item no carrinho: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/listAll")
	public ResponseEntity<List<ItemCarrinho>> listAll() {
		try {
			List<ItemCarrinho> lista = this.itemCarrinhoService.listAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/findById/{idItem}")
	public ResponseEntity<ItemCarrinho> findById(@PathVariable long idItem) {
		try {
			ItemCarrinho itemCarrinho = this.itemCarrinhoService.findById(idItem);
			return new ResponseEntity<>(itemCarrinho, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@PutMapping("/update/{idItem}")
	public ResponseEntity<String> update(@RequestBody ItemCarrinho itemCarrinho, @PathVariable long idItem) {
		try {
			String mensagem = this.itemCarrinhoService.update(itemCarrinho, idItem);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao atualizar item: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@DeleteMapping("/delete/{idItem}")
	public ResponseEntity<String> delete(@PathVariable long idItem) {
		try {
			String mensagem = this.itemCarrinhoService.delete(idItem);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao deletar item: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping("/getCarrinhoByUser")
	public ResponseEntity<Carrinho> getCarrinhoByUser(@RequestParam long idUsuario) {
		try {
			Carrinho carrinho = this.itemCarrinhoService.getCarrinhoByUser(idUsuario);
			return new ResponseEntity<>(carrinho, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
