package br.com.amptech.projeto01ws.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.amptech.projeto01ws.domain.Pedido;
import br.com.amptech.projeto01ws.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)	
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
				
		Pedido obj = service.Find(id);
					
		return ResponseEntity.ok().body(obj);
	}

}