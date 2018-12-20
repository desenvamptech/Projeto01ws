package br.com.amptech.projeto01ws;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.amptech.projeto01ws.domain.Categoria;
import br.com.amptech.projeto01ws.domain.Cidade;
import br.com.amptech.projeto01ws.domain.Cliente;
import br.com.amptech.projeto01ws.domain.Endereco;
import br.com.amptech.projeto01ws.domain.Estado;
import br.com.amptech.projeto01ws.domain.Pagamento;
import br.com.amptech.projeto01ws.domain.PagamentoComBoleto;
import br.com.amptech.projeto01ws.domain.PagamentoComCartao;
import br.com.amptech.projeto01ws.domain.Pedido;
import br.com.amptech.projeto01ws.domain.Produto;
import br.com.amptech.projeto01ws.domain.enums.EstadoPagamento;
import br.com.amptech.projeto01ws.domain.enums.TipoCliente;
import br.com.amptech.projeto01ws.repositories.CategoriaRepository;
import br.com.amptech.projeto01ws.repositories.CidadeRepository;
import br.com.amptech.projeto01ws.repositories.ClienteRepository;
import br.com.amptech.projeto01ws.repositories.EnderecoRepository;
import br.com.amptech.projeto01ws.repositories.EstadoRepository;
import br.com.amptech.projeto01ws.repositories.PagamentoRepository;
import br.com.amptech.projeto01ws.repositories.PedidoRepository;
import br.com.amptech.projeto01ws.repositories.ProdutoRepository;

@SpringBootApplication
public class Projeto01wsApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository EnderecoRepository;	
	
	@Autowired
	private PedidoRepository PedidoRepository;	
	
	@Autowired
	private PagamentoRepository PagamentoRepository;		
	
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2	= new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Artur Nogueira", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));	
		
		Cliente cli1 = new Cliente(null, "Andre Politti", "andrepolitti@gmail.com", "35491720879", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("38771034", "38771184"));
		
		Endereco e1 = new Endereco(null, "Rua Jose Amaro Rodrigues Filho", "709", "", "Jardim Ricardo Duzzi", "13160000", cli1, c2);
		Endereco e2 = new Endereco(null, "Avenida Tancredo Neves", "422", "Apto 24", "Jardim Rezek 2", "13160000", cli1, c3);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		EnderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("20/12/2018 15:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("20/12/2018 16:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("26/12/2018 00:00"), sdf.parse("31/12/2018 00:00"));
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		PedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		PagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
				
	}
		
}
