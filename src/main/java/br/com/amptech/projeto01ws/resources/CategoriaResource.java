package br.com.amptech.projeto01ws.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.amptech.projeto01ws.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> Listar() {
		
		Categoria cat1 = new Categoria(1, "Bebidas");
		Categoria cat2 = new Categoria(2, "Porções");
		
		List<Categoria> lista = new ArrayList<Categoria>();
		lista.add(cat1);
		lista.add(cat2);				
		
		return lista;
	}

}
