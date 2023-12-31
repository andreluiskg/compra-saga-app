package com.andreluiskg;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Header;

@ApplicationScoped
public class CreditoService {

	private int creditoTotal;
	private Map<Long, Integer> pedido_valor = new HashMap<>();

	public CreditoService() {
		this.creditoTotal = 100;
	}

	public void newPedidoValor(@Header("pedidoId") Long pedidoId, @Header("valor") int valor) {
		if (valor > creditoTotal) {
			throw new IllegalStateException("Saldo insuficiente");
		}

		creditoTotal = creditoTotal - valor;
		pedido_valor.put(pedidoId, valor);
	}

	public void cancelPedidoValor(@Header("id") Long id) {
//		creditoTotal = creditoTotal + pedido_valor.get(id);
//		pedido_valor.remove(id);
		System.out.println("PedidoValor falhou. Iniciando cancelamento do pedido");
	}

	public int getCreditoTotal() {
		return creditoTotal;
	}

}
