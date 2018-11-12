package br.com.amptech.projeto01ws.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.amptech.projeto01ws.domain.Categoria;
import br.com.amptech.projeto01ws.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)	
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
				
		Categoria obj = service.Find(id);
					
		return ResponseEntity.ok().body(obj);
	}

}
