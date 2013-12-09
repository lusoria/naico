package test;

import model.Pagamento;
import dao.PagamentoDao;




public class Test {

	public static void main(String Args[]) {
		System.out.println("inizio test");
		
		Pagamento ciao = null;
		try {
			ciao = PagamentoDao.getPagamento(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ciao);
		

	}
}
