package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * <p>Una NotaSpese è un Report di facile consultazione, possibilmente cartaceo, di nessun valore fiscale, che elenca le pratiche
 * NON PAGATE di un certo cliente (ovviamente con i rispettivi importi), ma solo FINO ad una certa data, chiamata "data di indagine".</p>
 * <p>La Data di Indagine può essere una qualsiasi data, odierna, futura o passata poichè si da per scontato che le pratiche
 * una volta salvate nel db restino sempre le stesse, e quindi anche le NotaSpese riferite a quelle.</p>
 * @author nico
 *
 */
public class NotaSpese extends Report {

	GregorianCalendar dataIndagine;
	
	
	
	public NotaSpese(int id) {
		super(id);
	}
	

	public NotaSpese(GregorianCalendar dataIndagine, Cliente cliente) {
		// TODO: escludere quelle dopo la data di indagine!! 
		super(cliente, cliente.selectPraticheNonPagate());

		this.dataIndagine = dataIndagine;
		
		for(Pratica pratica : pratiche) {
			if(pratica.getDataPagamento().after(dataIndagine)) {
				// la pratica non è da inserire nella NotaSpese
				// TODO: andare avanti perchè non funziona!!
				pratiche.remove(pratica);
			}
		}
	}

	
	
	@Override
	public void stampa() {

		System.out.println("ecco le pratiche rimaste in NotaSpese:");
		for(Pratica pratica : this.pratiche) {
			pratica.printPratica();
		}
	}



	@Override
	public boolean salvaPDF() {
		// TODO Auto-generated method stub
		return false;
	}

}
