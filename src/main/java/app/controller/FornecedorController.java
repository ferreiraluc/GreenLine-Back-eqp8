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

import app.entity.Fornecedor;
import app.service.FornecedorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fornecedor")
@Validated
@CrossOrigin(origins = "*")

public class FornecedorController {


    @Autowired
    private FornecedorService fornecedorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Fornecedor fornecedor) {


        try {

            String mensagem = this.fornecedorService.save(fornecedor);
            return new ResponseEntity<String>(mensagem, HttpStatus.CREATED);//Se acontecer algum erro

        } catch (Exception e) {

            return new ResponseEntity<String>("Ocorreu o seguinte erro:"+e.getMessage(),HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{idFornecedor}")
    public ResponseEntity<String> update(@RequestBody Fornecedor fornecedor, @PathVariable long idFornecedor) {
        try {
            String mensagem = this.fornecedorService.update(idFornecedor, fornecedor);
            return new ResponseEntity<String>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Ocorreu o seguinte erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listAll")
    public ResponseEntity<List<Fornecedor>> listAll (){

        try {

            List<Fornecedor> lista = this.fornecedorService.listAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findById/{idFornecedor}")
    public ResponseEntity<Fornecedor> findById(@PathVariable long idFornecedor){

        try {

            Fornecedor fornecedor = this.fornecedorService.findById(idFornecedor);
            return new ResponseEntity<>(fornecedor, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{idFornecedor}")
    public ResponseEntity<String> delete(@PathVariable long idFornecedor){

        try {

            String mensagem = this.fornecedorService.delete(idFornecedor);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("Ocorreu o seguinte erro: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //-------

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/findByNomeFornecedor")
    public ResponseEntity<List<Fornecedor>> findByNomeFornecedor (@RequestParam String nomeFornecedor){

        try {

            List<Fornecedor> lista = this.fornecedorService.findByNomeFornecedor(nomeFornecedor);
            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}