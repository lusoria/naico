package fivagest;

import java.util.ArrayList;
import java.util.Iterator;





/**
 * Questa classe rappresenta un Cliente
 * @author nico
 *
 */
public class Cliente {

	private int id;
	private String nome;
	private String cognome;
	private String pec;
	private PartitaIva partitaiva;
	private Euro accontoVirtuale;
	
	
	public Cliente(int id) {
		this.id = id;
	}
	
	
	
	
	/**
	 * Restituisce l'identificativo che il Cliente ha sul DB
	 * @return	ID del cliente
	 */
	public int getId() {
		return this.id;
	}
	
	
	
	/**
	 * Restituisce il nome completo ("Cognome, Nome") del Cliente
	 * @return		stringa col nome completo
	 */
	public String getNome() {
		return cognome+", "+nome;
	}
	
	
	
	/**
	 * Restituisce la PEC del cliente, di solito (non sempre) nel formato "cognome.nome@pec.it"
	 * @return	stringa dell'indirizzo PEC
	 */
	public String getPec() {
		return pec;
	}
	
	
	
	public PartitaIva getPartitaIva() {
		return partitaiva;
	}
	
	
	
	public void setPec(String pec) {
		this.pec = pec;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setPartitaIva(PartitaIva piva) {
		this.partitaiva = piva;
	}
	
	
	public Euro getAccontoVirtuale() {
		return accontoVirtuale;
	}




	public void setAccontoVirtuale(Euro accontoVirtuale) {
		this.accontoVirtuale = accontoVirtuale;
	}




	/**
	 * Restituisce la rappresentazione in stringa dell'oggetto Cliente corrente:<br>
	 * es.: "Rossi, Mario [5]", dove 5 è l'id del cliente
	 * @return notazione in stringa dell'oggetto corrente
	 */
	public String toString() {
		return this.getNome()+" ["+Integer.toString(this.id)+"]";		
	}
	
	
	
	public void leggiDB() {
		try {
			ClienteDao.load(this);
		} catch (Exception e) {
			System.err.println("Errore nel caricamento dei dati del cliente: "+e.getMessage());
			System.exit(1);
		}
	}
	
	
	public ArrayList<Pratica> selectPraticheNonPagate() {
		ArrayList<Pratica> ret = null;
		try {
			ret = PraticaDao.selectPraticheNonPagateByCliente(this);
		} catch (Exception e) {
			System.err.println("Errore nel caricamento delle pratiche del cliente: "+e.getMessage());
			System.exit(1);
		}
		return ret;
	}
	
	

	/**
	 * <p>Metodo statico che registra un pagamento da parte di un cliente.</p>
	 * <p>Questo metodo di fatto decide cosa sta pagando il cliente, prendendo in considerazione
	 * le sue pratiche non pagate (ordinate secondo il criterio di urgenza) e l'acconto virtuale del cliente
	 * ovvero i soldi già pagati dal cliente che però non sono andati a pagare ancora nessuna pratica.</p>
	 * <p>Questo metodo setta come pagate sul db le eventuali pratiche che il cliente riesce a pagare.</p>
	 * <p>Anche l'acconto virtuale del cliente può venir aggiornato sul db</p>  
	 * 
	 * @param coda		CodaPratiche pratiche del cliente non pagate.
	 * @param cliente
	 * @param pagamento
	 */
	public void pagamento(Euro pagamento) {
		
		CodaPratiche coda = null;
		try {
			coda = new CodaPratiche(this);
		} catch (Exception e) {
			System.err.println("Errore nella creazione della coda: "+e.getMessage());
			System.exit(1);
		}
		
		if(!pagamento.isZero()) {
			Euro cifraCliente = Euro.somma(this.getAccontoVirtuale(), pagamento);
			Iterator<Pratica> iterator = coda.iterator();
			
			// TODO qui c'è il controllo se il cliente paga anche l'iva o no
			// ovvero si applica l'eventuale IVA sull'importo della pratica
			// per ora lascio senza iva
			
			Pratica pratica = (Pratica) iterator.next();
			Euro totPratica = Euro.somma(new Euro[]{pratica.getImponibile(), pratica.getSpese()});
			System.out.println("prendo in esame la pratica["+pratica.getId()+"] :"+totPratica);
			
			
			while(cifraCliente.compareTo(totPratica) >= 0) {
	
				pratica.setPagata();
				System.out.println("pratica id "+pratica.getId()+" settata come pagata!");
				// TODO: salvare nel db la pratica!!
				cifraCliente.meno(totPratica);
				
				// prossima pratica!
				pratica = (Pratica) iterator.next();
				totPratica = Euro.somma(new Euro[]{pratica.getImponibile(), pratica.getSpese()});
				System.out.println("prendo in esame la pratica["+pratica.getId()+"] :"+totPratica);
			}
			
			this.setAccontoVirtuale(cifraCliente);
			System.out.println("al cliente "+this+" restano "+this.getAccontoVirtuale());
		}
	}
	

}
