package test;



import model.*;
import dao.*;




public class Test {

	public static void main(String Args[]) {



		System.out.println("ciao");
		for(Cliente cliente : ClienteDao.getTuttiClienti()) {
			System.out.println(cliente);
		}
		
		
		
		/*
		Cliente tizio = new Cliente(1);
		tizio.read();
		tizio.setPec("ciao@gaio.com");
		
		System.out.println(tizio.getPecLinkabile());
		*/
		
		
	
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
