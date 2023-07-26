package com.andreluiskg;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.camel.Header;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(baseUri = "http://compra-saga-credito-distr-andreluiskg-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/credito")
public interface CreditoService {

	@GET
	@Path("newPedidoValor")
	public void newPedidoValor(@QueryParam("pedidoId") @Header("pedidoId") Long pedidoId,
			@QueryParam("valor") @Header("valor") int valor);

	@GET
	@Path("cancelPedidoValor")
	public void cancelPedidoValor(@QueryParam("id") @Header("id") Long id);

	@GET
	@Path("getCreditoTotal")
	public int getCreditoTotal();

}
