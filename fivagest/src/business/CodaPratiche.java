package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


/**
 * Con Pila si intende l'insieme (non vuoto!) di TUTTE le pratiche non pagate di un cliente, ordinate da quella il cui pagamento è più urgente, a quella meno urgente.
 * Il criterio che decide quanto sia urgente il pagamento di una pratica è vario e potrebbe cambiare nel tempo.
 * Quando il cliente viene a dare dei soldi, questi andranno a pagare le pratiche più in basso nella pila, lasciando
 * eventualmente "scoperte" le pratiche più alte nella pila.
 * <p>La classe è iterabile, quindi ad esempio inseribile in un for, che restituirà una pratica alla volta in ordine.</p>
 * @author nico
 *
 */
public class CodaPratiche implements Iterable<Pratica> {

	private ArrayList<Pratica> pratiche;

	
	public CodaPratiche(Cliente cliente) throws Exception {
		ArrayList<Pratica> pratiche = cliente.selectPraticheNonPagate();
		// controllo che ci sia almeno una pratica
		if(pratiche.size() <= 0) {
			// errore!
			throw new Exception("Il cliente "+cliente+" non ha pratiche non pagate.");
		}else{
			CodaPratiche.ordina(pratiche);
			this.pratiche = pratiche;
		}
	}
	
	
	
	public ArrayList<Pratica> getPratiche() {
		return pratiche;
	}


	
	public static void ordina(ArrayList<Pratica> pratiche) {
		Collections.sort(pratiche, new Pratica.DataPagamentoComparator());
	}


	
	@Override
	public Iterator<Pratica> iterator() {
		Iterator<Pratica> p = this.pratiche.iterator();
		return p;
	}
	
	
	
	

	
	
}
