package test;


import java.util.ArrayList;
import java.util.Collections;

import model.Cliente;
import model.Pratica;
import model.soldi.Aliquota;
import model.soldi.Euro;
import model.soldi.Importo;
import util.Data;


public class Test {

	public static void main(String Args[]) {
		System.out.println("inizio test");
		
		// creo nuova data con formato mysql
		Data inizioAnno = new Data("2014-01-01");
		//System.out.println("compleanno: "+inizioAnno);

		
		// creo altra data e faccio il paragone
		Data fineAnno = new Data("31/12/2013");
		if(inizioAnno.compareTo(fineAnno) < 0) {
			//System.out.println(inizioAnno);
		}else {
			//System.out.println(fineAnno);
		}
		
		
		// creo una pratica p1 più vecchia ma più costosa
		Cliente tony = new Cliente("Tony", "Concilianti");
		Pratica p1 = new Pratica(tony, new Importo("42.00", new Aliquota(22), false), new Euro(), "ZETA", fineAnno);
		//p1.printPratica();
		
		// creo una pratica p2 più recente ma meno costosa
		Cliente bobby = new Cliente("Bobby", "Solo");
		Pratica p2 = new Pratica(bobby, new Importo("42.50", new Aliquota(22), false), new Euro(), "BILLO", inizioAnno);
		//p2.printPratica();
		
		// creo una pratica p3
		Cliente tom = new Cliente("Tom", "Lota");
		Pratica p3 = new Pratica(tom, new Importo("43.00", new Aliquota(22), false), new Euro(), "ALLA", new Data("30/12/2013"));
		//p2.printPratica();
		
		ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
		listaPratiche.add(p1);
		listaPratiche.add(p2);
		listaPratiche.add(p3);
		

		Collections.sort(listaPratiche, new Pratica.DataPagamentoComparator());
		System.out.println("Stampo le pratiche ordinate per data");
		for(Pratica p : listaPratiche) {
			p.printPratica();
			System.out.println("");
		}
		
		Collections.sort(listaPratiche);
		System.out.println("Stampo le pratiche ordinate alfabeticamente");
		for(Pratica p : listaPratiche) {
			p.printPratica();
			System.out.println("");
		}
			
		
		Collections.sort(listaPratiche, new Pratica.ImponibileComparator());
		System.out.println("Stampo le pratiche ordinate per Imponibile");
		for(Pratica p : listaPratiche) {
			p.printPratica();
			System.out.println("");
		}
		
		

		


	}

}
