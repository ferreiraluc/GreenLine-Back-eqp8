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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Produto;
import app.service.ProdutoService;
import org.springframework.web.bind.annotation.PutMapping;


@RequestMapping("/api/produto")
@RestController
@CrossOrigin(origins = "*")

public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Produto produto) {
		try {

			String mensagem = produtoService.save(produto);
			return new ResponseEntity<String>(mensagem, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage() + " Ocorreu um erro!", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listAll")
	public ResponseEntity<List<Produto>> listAll() {
		try {
			List<Produto> produtos = produtoService.listAll();
			return new ResponseEntity<>(produtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/findById/{idProduto}")
	public ResponseEntity<Produto> findById(@PathVariable long idProduto) {
		try {

			Produto produto= produtoService.findById(idProduto);
			return new ResponseEntity<>(produto, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole ('ADMIN')")
	@PutMapping("update/{idProduto}")
	public ResponseEntity<String> update(@PathVariable Long idProduto, @RequestBody Produto produto) {

		try {

			String mensagem = produtoService.update(idProduto, produto);
			return new ResponseEntity<String>(mensagem, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>("Deu Ruim", HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole ('ADMIN')")
	@DeleteMapping("delete/{idProduto}")
	public ResponseEntity<String> delete(@PathVariable Long idProduto) {

		try {

			String mensagem = produtoService.delete(idProduto);
			return new ResponseEntity<String>(mensagem, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>("Deu Ruim", HttpStatus.BAD_REQUEST);
		}

	}
}