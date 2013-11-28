package test;

import java.util.GregorianCalendar;

import business.Cliente;
import business.Cliente.Dao;





public class Test {

	public static void main(String Args[]) {

		Cliente tizio = new Cliente(1);
		tizio.read();
		tizio.setPec("ciao@gaio.com");
		
		System.out.println(tizio.getPecLinkabile());
		
		
		
	
		/*
		Cliente tizio = new Cliente(1);
		tizio.leggiDB();
		System.out.println("pratiche non ordinate per "+tizio);
		
		for(Pratica p : tizio.selectPraticheNonPagate()) {
			p.printPratica();
		}
		
		System.out.println("");
		
		GregorianCalendar finoal = new GregorianCalendar(2013, 11, 4);
		GregorianCalendar creazione = new GregorianCalendar();
		NotaSpese ns = new NotaSpese(finoal, tizio, creazione);
		
		ns.stampa();
		*/
		System.exit(0);
			
			
	}

}
