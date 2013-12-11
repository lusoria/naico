package test;

import model.Cliente;
import model.Pagamento;
import model.soldi.Euro;
import util.Data;
import dao.ClienteDao;
import dao.PagamentoDao;




public class Test {

	public static void main(String Args[]) {
		System.out.println("inizio test");
		
		Cliente cliente = null;
		try {
			cliente = ClienteDao.getCliente(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(cliente);
		
		System.out.println("fine test");
		System.exit(0);
		
		Pagamento pagamento = new Pagamento(cliente, new Data(), new Euro("100.00"), true);
		PagamentoDao.effettuaPagamento(pagamento);
		
		Pagamento pagamento2 = new Pagamento(cliente, new Data(), new Euro("100.00"), false);
		PagamentoDao.effettuaPagamento(pagamento2);
		
		
		System.out.println("fine test");
		System.exit(0);
	}
}
