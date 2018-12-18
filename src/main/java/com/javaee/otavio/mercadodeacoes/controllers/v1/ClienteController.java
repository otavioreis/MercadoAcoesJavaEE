package com.javaee.otavio.mercadodeacoes.controllers.v1;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.otavio.mercadodeacoes.domain.Cliente;
import com.javaee.otavio.mercadodeacoes.services.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

		@Autowired
		private ClienteService clienteService;
		
		@GetMapping
		@ResponseStatus(HttpStatus.OK)
		public Set<Cliente> getAll(){
			return clienteService.getAll();
		}
		
		@GetMapping({"/{id}"})
		@ResponseStatus(HttpStatus.OK)
		public Cliente findById(@PathVariable String id) {
			return clienteService.findById(id);
		}
		
		@PostMapping
		@ResponseStatus(HttpStatus.OK)
		public Cliente createNew(@RequestBody Cliente cliente) {
			return clienteService.createNew(cliente);
		}
		
		@PutMapping({"/{id}"})
		@ResponseStatus(HttpStatus.OK)
		public Cliente update(@PathVariable String id, @RequestBody Cliente cliente) {
			Cliente clienteReg = clienteService.findById(id);
			clienteReg.setNome(cliente.getNome());
			clienteReg.setEmail(cliente.getNome());
			return clienteService.save(clienteReg);
		}
		
		@DeleteMapping({"/{id}"})
		@ResponseStatus(HttpStatus.OK)
		public void deleteById(@PathVariable String id) {
			clienteService.deleteById(id);
		}
		
}
