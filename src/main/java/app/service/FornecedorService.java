package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Fornecedor;
import app.repository.FornecedorRepository;

@Service
public class FornecedorService {

        @Autowired
        private FornecedorRepository fornecedorRepository; 

        //CRUD


        //Create
        public String save(Fornecedor fornecedor) {
        	
        	//FORÇANDO A VALIDAÇÃO QUE DEVERIA OCORRER AUTOMATICAMENTE PELA ENTITY
        	//if(fornecedor.getCnpj() == null)
        	//	throw new RuntimeException();
        	
        	//FORÇANDO A VALIDAÇÃO QUE DEVERIA OCORRER AUTOMATICAMENTE PELA ENTITY
        //	else if(fornecedor.getCnpj().isBlank())
        //		throw new RuntimeException();
        	
        	
            this.fornecedorRepository.save(fornecedor);
            return fornecedor.getNomeFornecedor()+" Fornecedor salvo com sucesso!!";
        }


        //Read
        public List<Fornecedor> listAll(){ 
            return this.fornecedorRepository.findAll(); 
        }
        public Fornecedor findById(long idFornecedor) { 
            Fornecedor fornecedor = this.fornecedorRepository.findById(idFornecedor).get(); 
            return fornecedor; 
        }


        //Update
        public String update (long id, Fornecedor fornecedor) { 
            fornecedor.setIdFornecedor(id); 
            this.fornecedorRepository.save(fornecedor);
            return fornecedor.getNomeFornecedor()+" Atualizado fornecedor com sucesso!!";
        }


        //Delete
        public String delete (long idFornecedor) {
            this.fornecedorRepository.deleteById(idFornecedor);;
            return " Fornecedor deletado com sucesso";
        }


        //-- Consulta ao SGDB (Read)--

        public List<Fornecedor> findByNomeFornecedor(String nomeFornecedor){
            return this.fornecedorRepository.findByNomeFornecedor(nomeFornecedor);
        }
    }





