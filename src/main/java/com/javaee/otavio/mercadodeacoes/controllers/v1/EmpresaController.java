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

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;
import com.javaee.otavio.mercadodeacoes.services.EmpresaService;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Empresa> getAll(){
		return empresaService.getAll();
	}
	
	@GetMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public Empresa findById(@PathVariable String id) {
		return empresaService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Empresa createNew(@RequestBody Empresa empresa) {
		return empresaService.createNew(empresa);
	}
	
	@PutMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public Empresa update(@PathVariable String id, @RequestBody Empresa empresa) {
		Empresa empresaReg = empresaService.findById(id);
		empresaReg.setNome(empresa.getNome());
		empresaReg.setEmail(empresa.getNome());
		return empresaService.save(empresaReg);
	}
	
	@DeleteMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable String id) {
		empresaService.deleteById(id);
	}
	
	@PostMapping({"/acao/{idEmpresa}"})
	@ResponseStatus(HttpStatus.OK)
	public Empresa createAcao(@PathVariable String idEmpresa, @RequestBody Acao acao) {
		return empresaService.CriarAcao(idEmpresa, acao);
	}
	
}
