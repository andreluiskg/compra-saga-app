package com.andreluiskg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;

@ApplicationScoped
public class SagaRoute extends RouteBuilder {

	@Inject
	PedidoService pedidoService;

	@Inject
	CreditoService creditoService;

	@Override
	public void configure() throws Exception {

		CamelSagaService sagaService = new InMemorySagaService();
		getContext().addService(sagaService);

		// Saga
		from("direct:saga").saga() // saga mae
				.propagation(SagaPropagation.REQUIRES_NEW).log("Iniciando a transação") // inicio
				.to("direct:newPedido").log("Criando novo pedido com id ${header.id}") // saga a implementar
				.to("direct:newPedidoValor").log("Reservando o crédito") // saga a implementar
				.to("direct:finaliza").log("Feito"); // saga a implementar

		// Pedido Service
		from("direct:newPedido").saga() // nome da saga que esta sendo implementada
				.propagation(SagaPropagation.MANDATORY) // MANDATORY = obrigatoria
				.compensation("direct:cancelPedido") // saga a implementar
				.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION) //
				.bean(pedidoService, "newPedido").log("Pedido ${body} criado"); // objeto.metodo que sera usado

		from("direct:cancelPedido") // nome da saga que esta sendo sendo implementada
				.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION) //
				.bean(pedidoService, "cancelPedido").log("Pedido ${body} cancelado"); // objeto.metodo que sera usado

		// Credito Service
		from("direct:newPedidoValor").saga() // nome da saga que esta sendo implementada
				.propagation(SagaPropagation.MANDATORY) // MANDATORY = obrigatoria
				.compensation("direct:cancelPedidoValor") // saga a implementar
				.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION) //
				.bean(creditoService, "newPedidoValor") // objeto.metodo que sera usado
				.log("Credido do pedido ${header.pedidoId} no valor de ${header.valor} reservado para a saga ${body}");

		from("direct:cancelPedidoValor") // nome da saga que esta sendo sendo implementada
				.transform().header(Exchange.SAGA_LONG_RUNNING_ACTION) //
				.bean(creditoService, "cancelPedidoValor") // objeto.metodo que sera usado
				.log("Credido do pedido ${header.pedidoId} no valor de ${header.valor} cancelado para a saga ${body}");

		// Finaliza a Saga
		from("direct:finaliza").saga() // nome da saga que esta sendo implementada
				.propagation(SagaPropagation.MANDATORY) // MANDATORY = obrigatoria
				.choice() //
				.end();

	}

}
