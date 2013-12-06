package model;


import java.util.ArrayList;
import java.util.Iterator;

import model.soldi.Euro;
import dao.PraticaDao;


/**
 * Rappresentazione di un Cliente
 * @author nico
 *
 */
public class Cliente {

	
	private int id;
	private String nome;
	private String cognome;
	private Email pec;
	private PartitaIva partitaiva;
	private Euro accontoVirtuale;
	
	
	/**
	 * Crea un Cliente <b>vuoto</b>, con solo il suo identificativo nel DB.<br>
	 * Per avere un Cliente istanziato correttamente dal solo id, usare la classe DAO corrispondente.
	 * @param id identificativo del cliente nel database
	 */
	public Cliente(int id) {
		this.id = id;
	}
	
	
	
	/**
	 * Rappresentazione di un nuovo Cliente con i soli parametri obbligatori per poter essere salvato nel DB.<br>
	 * Il Cliente appena creato avr� un acconto Virtuale pari a 0,00 �.
	 * @param nome
	 * @param cognome
	 */
	public Cliente(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.accontoVirtuale =  new Euro();
	}
	
	
	/**
	 * Restituisce l'identificativo che il Cliente ha sul DB
	 * @return identificativo del cliente nel database
	 */
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Restituisce il nome completo (nel formato "Cognome, Nome") del Cliente
	 * @return stringa col nome completo
	 */
	public String getNome() {
		return this.cognome+", "+this.nome;
	}
	
	
	/**
	 * Imposta il nome del Cliente (nome (anche pi� d'uno) di battesimo, non cognomi!)
	 * @param nome nome del Cliente
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	/**
	 * Imposta il cognome del Cliente
	 * @param cognome cognome del cliente
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	/**
	 * Restituisce la PEC del cliente, spesso (non sempre) nel formato "cognome.nome@pec.it"
	 * @return oggetto Email dell'indirizzo PEC
	 */
	public Email getPec() {
		return this.pec;
	}
	
	
	/**
	 * Restituisce la PEC del cliente linkabile in HTML"
	 * @return stringa linkabile all'indirizzo PEC
	 */
	public String getPecLinkabile() {
		return this.pec.link(this.getNome());
	}
	
	
	/**
	 * Imposta la PEC del Cliente
	 * @param pec oggetto Email per la PEC del cliente
	 */
	public void setPec(Email pec) {
		this.pec = pec;
	}
	
	
	/**
	 * Imposta la PEC del Cliente
	 * @param pec stringa per la PEC del cliente
	 */
	public void setPec(String pec) {
		Email mail = null;
		try {
			mail = new Email(pec);
		}catch(Exception e) {
			System.err.println("Impossibile impostare la PEC del cliente "+this+": ");
			System.err.print(e.getMessage());
			System.exit(1);
		}
		this.pec = mail;
	}
	
	
	/**
	 * Restituisce la Partita IVA del cliente
	 * @return oggetto PartitaIVA del cliente
	 */
	public PartitaIva getPartitaIva() {
		return this.partitaiva;
	}
	
	
	/**
	 * Imposta la partita IVA del cliente
	 * @param piva oggetto PartitaIVA
	 */
	public void setPartitaIva(PartitaIva piva) {
		this.partitaiva = piva;
	}
	
	
	/**
	 * Restituisce l'acconto virtuale del cliente.<br>
	 * Per informazioni sull'acconto virtuale, vedere ??? TODO: aggiungere da qualche parte la documentazione dell'acconto virtuale
	 * @return acconto virtuale (oggetto Euro) del cliente
	 */
	public Euro getAccontoVirtuale() {
		return this.accontoVirtuale;
	}


	/**
	 * Imposta l'acconto virtuale del cliente.<br>
	 * Per informazioni sull'acconto virtuale, vedere ??? TODO: aggiungere da qualche parte la documentazione dell'acconto virtuale
	 * @param accontoVirtuale oggetto Euro che rappresenta l'acconto virtuale del cliente
	 */
	public void setAccontoVirtuale(Euro accontoVirtuale) {
		this.accontoVirtuale = accontoVirtuale;
	}


	/**
	 * Traduce l'oggetto corrente in una stringa<br>
	 * es.: "Rossi, Mario [5]", dove 5 � l'id del cliente<br>
	 * @return notazione in stringa dell'oggetto corrente
	 */
	public String toString() {
		return this.getNome()+" ["+Integer.toString(this.id)+"]";		
	}
	
	
	
	/**
	 * Aggiorna il record sul DB corrispondente al Cliente in oggetto.
	 */
	public void update() {
		// TODO: implementare
	}
	
	
	// TODO: documentare
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
	 * <p>Metodo statico che registra un pagamento effettuato da un cliente.</p>
	 * <p>Questo metodo di fatto decide cosa sta pagando il cliente, prendendo in considerazione
	 * le sue pratiche non pagate (ordinate secondo il criterio di urgenza) e il suo acconto virtuale
	 * ovvero i soldi gi� pagati dal cliente che per� non sono andati a coprire ancora nessuna pratica.</p>
	 * <p>Questo metodo setta come pagate sul DB le eventuali pratiche che il pagamento riesce a coprire.</p>
	 * <p>Anche l'acconto virtuale del cliente pu� venir aggiornato sul DB</p>  
	 * @param pagamento oggetto Euro rappresentante la cifra che il cliente ci d�
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
			
			// TODO qui c'� il controllo se il cliente paga anche l'iva o no
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
