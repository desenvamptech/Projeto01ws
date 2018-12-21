package br.com.amptech.projeto01ws.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amptech.projeto01ws.domain.Categoria;
import br.com.amptech.projeto01ws.repositories.CategoriaRepository;
import br.com.amptech.projeto01ws.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria Find(Integer id) {
					
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {		
		obj.setId(null);		
		return repo.save(obj);		
	}
	
}
