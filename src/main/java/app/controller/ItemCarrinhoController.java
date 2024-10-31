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

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody ItemCarrinho itemCarrinho){

		try {

			String mensagem = this.itemCarrinhoService.save(itemCarrinho);
			return new ResponseEntity<>(mensagem,HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			String erro = e.getMessage();
			return new ResponseEntity<String>(erro,HttpStatus.BAD_REQUEST);

		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/listAll")
	public ResponseEntity <List<ItemCarrinho>> listAll(){

		try {
			List<ItemCarrinho> lista = this.itemCarrinhoService.listAll();
			return new ResponseEntity<>(lista, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

		}

	}


	@GetMapping("/findById/{idItem}")
	public ResponseEntity <ItemCarrinho> findById(@PathVariable long idItem){

		try {
			ItemCarrinho itemCarrinho = this.itemCarrinhoService.findById(idItem);
			return new ResponseEntity<>(itemCarrinho, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@PutMapping("/update/{idItem}")
	public ResponseEntity<String> update(@RequestBody ItemCarrinho itemCarrinho, @PathVariable long idItem){

		try {
			String mensagem = this.itemCarrinhoService.update(itemCarrinho, idItem);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@DeleteMapping("/delete/{idItem}")
	public ResponseEntity<String> delete(@PathVariable long idItem){

		try {
			String mensagem = this.itemCarrinhoService.delete(idItem);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@GetMapping("/getCarrinhoByUser")
	public ResponseEntity <Carrinho> getCarrinhoByUser(@RequestParam long idUsuario){
		try {
			Carrinho carrinho = this.itemCarrinhoService.getCarrinhoByUser(idUsuario);
			return new ResponseEntity<>(carrinho, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}

	}

}