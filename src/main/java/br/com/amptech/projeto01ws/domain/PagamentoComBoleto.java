package br.com.amptech.projeto01ws.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.amptech.projeto01ws.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dataVencimento;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataPagamento, Date dataVencimento) {
		super(id, estado, pedido);
		// TODO Auto-generated constructor stub
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
	
	
	
}
