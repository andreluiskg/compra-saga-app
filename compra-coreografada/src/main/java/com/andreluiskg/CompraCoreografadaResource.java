package com.andreluiskg;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("compra-coreografada")
public class CompraCoreografadaResource {

	@Inject
	PedidoService pedidoService;

	@GET
	@Path("teste")
	@Produces(MediaType.TEXT_PLAIN)
	public Response saga() {

		// credito = 100 (valor inicial no construtor)
		Long id = 0L;

		pedidoService.newPedido(++id, 20);
		pedidoService.newPedido(++id, 30);
		pedidoService.newPedido(++id, 40);
		pedidoService.newPedido(++id, 15);

		return Response.ok().build();
	}

//	private void comprar(Long id, int valor) {
//		pedidoService.newPedido(id);
//		try {
//			creditoService.newPedidoValor(id, valor);
//			System.out.println("Pedido " + id + " registrado no valor de " + valor);
//		} catch (IllegalStateException e) {
//			pedidoService.cancelPedido(id);
//			System.err.println("Pedido " + id + " estornado no valor de " + valor);
//		}
//	}

}
