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
import org.springframework.web.bind.annotation.RestController;

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.validation.Valid;

@RequestMapping("/api/cliente")
@RestController
@Validated
@CrossOrigin(origins = "*")

public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Cliente cliente){

		try {

			String mensagem = this.clienteService.save(cliente);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listAll")
	public ResponseEntity <List<Cliente>> listAll(){

		try {
			List<Cliente> lista = this.clienteService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}


	@GetMapping("/findById/{idCliente}")
	public ResponseEntity <Cliente> findById(@PathVariable long idCliente){

		try {
			Cliente cliente = this.clienteService.findById(idCliente);
			return new ResponseEntity<>(cliente, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@GetMapping("/findByUsuarioId/{idUsuario}")
	public ResponseEntity <Cliente> findByUsuarioId(@PathVariable long idUsuario){

		try {
			Cliente cliente = this.clienteService.findByUsuarioId(idUsuario);
			return new ResponseEntity<>(cliente, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@PutMapping("/update/{idCliente}")
	public ResponseEntity<String> update(@Valid @RequestBody Cliente cliente, @PathVariable long idCliente){

		try {
			String mensagem = this.clienteService.update(cliente, idCliente);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@DeleteMapping("/delete/{idCliente}")
	public ResponseEntity<String> update(@PathVariable long idCliente){

		try {
			String mensagem = this.clienteService.delete(idCliente);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

}