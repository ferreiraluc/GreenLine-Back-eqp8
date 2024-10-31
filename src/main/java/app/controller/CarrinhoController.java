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
import app.service.CarrinhoService;
import dto.MesValorDTO;

@RequestMapping("/api/carrinho")
@RestController
@CrossOrigin(origins = "*")
public class CarrinhoController {

	@Autowired
	private CarrinhoService carrinhoService;

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Carrinho carrinho){

		try {

			String mensagem = this.carrinhoService.save(carrinho);
			return new ResponseEntity<>(mensagem,HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listAll")
	public ResponseEntity <List<Carrinho>> listAll(){

		try {
			List<Carrinho> lista = this.carrinhoService.listAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	@GetMapping("/findById/{idCarrinho}")
	public ResponseEntity <Carrinho> findById(@PathVariable long idCarrinho){

		try {
			Carrinho carrinho = this.carrinhoService.findById(idCarrinho);
			return new ResponseEntity<>(carrinho, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{idCarrinho}")
	public ResponseEntity<String> update(@RequestBody Carrinho carrinho, @PathVariable long idCarrinho){

		try {
			String mensagem = this.carrinhoService.update(carrinho, idCarrinho);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{idCarrinho}")
	public ResponseEntity<String> delete(@PathVariable long idCarrinho){

		try {
			String mensagem = this.carrinhoService.delete(idCarrinho);
			return new ResponseEntity<>(mensagem,HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

		}

	}

	//consultas DB

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/findByNomeProduto")
	public ResponseEntity<List<Carrinho>> findByItemCarrinhoProdutoNomeProduto (@RequestParam String nomeProduto){

		try {

			List<Carrinho> lista = this.carrinhoService.findByItemCarrinhoProdutoNomeProduto(nomeProduto);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/buscarVendaAcimaValor")
	public ResponseEntity<List<Carrinho>> buscarVendaAcimaValor (@RequestParam double valorCarrinho){

		try {

			List<Carrinho> lista = this.carrinhoService.buscarVendaAcimaValor(valorCarrinho);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/buscarVendaAbaixoValor")
	public ResponseEntity<List<Carrinho>> buscarVendaAbaixoValor (@RequestParam double valorCarrinho){

		try {

			List<Carrinho> lista = this.carrinhoService.buscarVendaAbaixoValor(valorCarrinho);
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/ListVendasByMonthForLast12Months")
	public ResponseEntity<List<MesValorDTO>> ListVendasByMonthForLast12Months (){

		try {

			List<MesValorDTO> lista = this.carrinhoService.getTotalValorCarrinhoByMonthForLast12Months();
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getVendasFinalizadas")
	public ResponseEntity<List<Carrinho>> getVendasFinalizadas (){

		try {

			List<Carrinho> lista = this.carrinhoService.getVendasFinalizadas();
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

}