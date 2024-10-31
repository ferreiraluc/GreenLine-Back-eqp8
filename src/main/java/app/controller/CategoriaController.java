package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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

import app.entity.Categoria;
import app.service.CategoriaService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*")

public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@Valid @RequestBody Categoria categoria)
	{
		try {
			String mensagem = this.categoriaService.save(categoria);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
		} catch (Exception e) { //Se acontecer algum erro

			return new ResponseEntity<String>("Ocorreu o seguinte erro1"+e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{idCategoria}")
	public ResponseEntity<String> update(@Valid @RequestBody Categoria categoria, @PathVariable long idCategoria){
		try {
			String mensagem = this.categoriaService.update(idCategoria, categoria);
			return new ResponseEntity<String>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listAll")
	public ResponseEntity<List<Categoria>> listAll (){

		try {

			List<Categoria> lista = this.categoriaService.listAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}


	@GetMapping("/findById/{idCategoria}")
	public ResponseEntity<Categoria> findById(@PathVariable long idCategoria){

		try {

			Categoria categoria = this.categoriaService.findById(idCategoria);
			return new ResponseEntity<>(categoria, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{idCategoria}")
	public ResponseEntity<String> delete(@PathVariable long idCategoria){
		try {

			String mensagem = this.categoriaService.delete(idCategoria);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu o seguinte erro: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findByDescricao")
	public ResponseEntity<List<Categoria>> findByDescricao (@RequestParam String descricao){

		try {

			List<Categoria> lista = this.categoriaService.findByDescricao(descricao);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}