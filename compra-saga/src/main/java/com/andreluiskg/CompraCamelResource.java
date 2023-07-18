package com.andreluiskg;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;

@Path("compra-camel")
public class CompraCamelResource {

	@Inject
	CamelContext context;

	@Path("teste")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response saga() {

		Long id = 0L;

		comprar(++id, 20);
		comprar(++id, 30);
		comprar(++id, 30);
		comprar(++id, 25);

		return Response.ok().build();

	}

	private void comprar(Long id, int valor) {

		try {
			context.createFluentProducerTemplate() //
					.to("direct:saga") // nome da saga que foi implementada
					.withHeader("id", id) //
					.withHeader("pedidoId", id) //
					.withHeader("valor", valor) //
					.request();
		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage());
//			throw new RuntimeException(e.getMessage("Entrou na Exception de CompraCamelResource.comprar(Long id, int valor)"));
			throw new RuntimeException("Entrou na Exception de CompraCamelResource.comprar(Long id, int valor)");
		}

	}

}
