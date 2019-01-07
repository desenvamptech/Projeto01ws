package br.com.amptech.projeto01ws.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.amptech.projeto01ws.domain.ItemPedido;
import br.com.amptech.projeto01ws.domain.PagamentoComBoleto;
import br.com.amptech.projeto01ws.domain.Pedido;
import br.com.amptech.projeto01ws.domain.enums.EstadoPagamento;
import br.com.amptech.projeto01ws.repositories.ItemPedidoRepository;
import br.com.amptech.projeto01ws.repositories.PagamentoRepository;
import br.com.amptech.projeto01ws.repositories.PedidoRepository;
import br.com.amptech.projeto01ws.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	EmailService emailService;

	public Pedido find(Integer id) {

		Optional<Pedido> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());

		emailService.sendOrderConfirmationEmail(obj);		

		return obj;
	}

}
