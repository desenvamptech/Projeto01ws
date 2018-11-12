package br.com.amptech.projeto01ws;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.amptech.projeto01ws.domain.Categoria;
import br.com.amptech.projeto01ws.repositories.CategoriaRepository;

@SpringBootApplication
public class Projeto01wsApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categorias;
	
	public static void main(String[] args) {
		SpringApplication.run(Projeto01wsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Cervejas");
		Categoria cat2 = new Categoria(null, "Porções");
		Categoria cat3 = new Categoria(null, "Pratos");
		Categoria cat4 = new Categoria(null, "Sobremesas");
		
		categorias.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		
	}
	
	
	
	
}
