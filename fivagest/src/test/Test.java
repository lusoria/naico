package test;

import org.pmw.tinylog.Logger;

public class Test {
	
	

	public static void main(String Args[]) {
		Logger.info("inizio test");
		Logger.info("fine test");
		System.exit(0);
		
		/*
		Cliente cliente = null;
		try {
			cliente = ClienteDao.getCliente(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(cliente);
		
		Pagamento pagamento = new Pagamento(cliente, new Data(), new Euro("100.00"), true);
		PagamentoDao.effettuaPagamento(pagamento);
		
		Pagamento pagamento2 = new Pagamento(cliente, new Data(), new Euro("100.00"), false);
		PagamentoDao.effettuaPagamento(pagamento2);
		
		System.out.println("fine test");
		System.exit(0);
		*/
	}
}
