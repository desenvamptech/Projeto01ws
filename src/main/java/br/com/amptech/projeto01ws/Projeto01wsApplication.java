package br.com.amptech.projeto01ws;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.amptech.projeto01ws.domain.Categoria;
import br.com.amptech.projeto01ws.domain.Produto;
import br.com.amptech.projeto01ws.repositories.CategoriaRepository;
import br.com.amptech.projeto01ws.repositories.ProdutoRepository;

@SpringBootApplication
public class Projeto01wsApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categorias;
	
	@Autowired
	private ProdutoRepository produtos;
	
	public static void main(String[] args) {
		SpringApplication.run(Projeto01wsApplication.class, args);
	}

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Cervejas");
		Categoria cat2 = new Categoria(null, "Porções");	
		Categoria cat3 = new Categoria(null, "Sobremesas");
		
		Produto p1 = new Produto(null, "Brhama", 10.0);
		Produto p2 = new Produto(null, "Skol", 10.0);
		Produto p3 = new Produto(null, "Heineken", 12.8);
		
		Produto p4 = new Produto(null, "Calabresa", 20.0);
		Produto p5 = new Produto(null, "Picanha", 45.0);
		Produto p6 = new Produto(null, "Batata Frita", 22.0);
		
		Produto p7 = new Produto(null, "Brigadeiro", 10.0);
		Produto p8 = new Produto(null, "Pudim", 10.0);
		Produto p9 = new Produto(null, "Sorvete", 10.0);		
				
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));		
		cat2.getProdutos().addAll(Arrays.asList(p4, p5, p6));
		cat3.getProdutos().addAll(Arrays.asList(p7, p8, p9));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat2));
		p6.getCategorias().addAll(Arrays.asList(cat2));
		
		p7.getCategorias().addAll(Arrays.asList(cat3));
		p8.getCategorias().addAll(Arrays.asList(cat3));
		p9.getCategorias().addAll(Arrays.asList(cat3));
		
		categorias.saveAll(Arrays.asList(cat1, cat2, cat3));
		
		produtos.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));
		
	}
		
}
