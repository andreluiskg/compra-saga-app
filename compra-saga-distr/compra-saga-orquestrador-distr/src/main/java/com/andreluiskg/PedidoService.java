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
@RegisterRestClient(baseUri = "http://compra-saga-pedido-distr-andreluiskg-dev.apps.sandbox-m4.g2pi.p1.openshiftapps.com/pedido")
public interface PedidoService {

	@GET
	@Path("newPedidoValor")
	@Produces(MediaType.TEXT_PLAIN)
	public void newPedido(@QueryParam("id") @Header("id") Long id);

	@GET
	@Path("cancelPedido")
	@Produces(MediaType.TEXT_PLAIN)
	public void cancelPedido(@QueryParam("id") @Header("id") Long id);

}
