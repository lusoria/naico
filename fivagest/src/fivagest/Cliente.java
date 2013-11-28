package fivagest;


import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
	 * Crea la rappresentazione di un cliente già esistente nel database e quindi con un identificativo univoco.<br>
	 * Per caricare e quindi poter accedere ai vari parametri del Cliente, usare in seguito il metodo read()
	 * @param id identificativo del cliente nel database
	 */
	public Cliente(int id) {
		this.id = id;
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
	 * Imposta il nome del Cliente (nome (anche più d'uno) di battesimo, non cognomi!)
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
	private void setAccontoVirtuale(Euro accontoVirtuale) {
		this.accontoVirtuale = accontoVirtuale;
	}


	/**
	 * Traduce l'oggetto corrente in una stringa<br>
	 * es.: "Rossi, Mario [5]", dove 5 è l'id del cliente<br>
	 * @return notazione in stringa dell'oggetto corrente
	 */
	public String toString() {
		return this.getNome()+" ["+Integer.toString(this.id)+"]";		
	}
	
	
	/**
	 * Carica dal DB i dati del Cliente corrente.<br>
	 * ATTENZIONE! Questo metodo sovrascrive gli attributi già memorizzati nell'oggetto Cliente.<br>
	 * Tipicamente questo metodo viene usato immediatamente dopo aver istanziato un nuovo oggetto Cliente per mezzo dell'id:<br>
	 * <pre>
	 * 	Cliente titolare = new Cliente(42);
	 * 	titolare.read();
	 * 	...
	 * </pre>
	 */
	public void read() {
		try {
			//ClienteDao.load(this);
			Dao.read(this);
		} catch (Exception e) {
			System.err.println("Errore nel caricamento dei dati del cliente: "+e.getMessage());
			System.exit(1);
		}
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
	 * ovvero i soldi già pagati dal cliente che però non sono andati a coprire ancora nessuna pratica.</p>
	 * <p>Questo metodo setta come pagate sul DB le eventuali pratiche che il pagamento riesce a coprire.</p>
	 * <p>Anche l'acconto virtuale del cliente può venir aggiornato sul DB</p>  
	 * @param pagamento oggetto Euro rappresentante la cifra che il cliente ci dà
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
	

	/**
	 * Cliente Data Access Object
	 * Questa classe si occupa della lettura e scrittura nel DB di dati che riguardano un Cliente
	 * CRUD: Create, Read, Update, Delete
	 * @author nico
	 *
	 */
	public static class Dao {
		
		public static void read(Cliente cliente) throws Exception {
			Connection connessione = null;
			Statement state = null;
			ResultSet result = null;
			String query = "SELECT * FROM Cliente WHERE id = "+cliente.getId();
			
			connessione = DataBaseHelper.getConnection();
			
			try {
			
				state = connessione.createStatement();
				result = state.executeQuery(query);
			
				// recupero i dati
				if (result.isBeforeFirst()) {
					result.first();
					
					// nome
					cliente.setNome(result.getString("nome"));
					
					// cognome
					cliente.setCognome(result.getString("cognome"));
					
					// acconto virtuale
					if(result.getDouble("accontoVirtuale") != 0.0) {
						cliente.setAccontoVirtuale(new Euro(result.getDouble("accontoVirtuale")));
					}else{
						// acconto virtuale mai null ma sempre con un valore Euro positivo (>= 0.00 )
						cliente.setAccontoVirtuale(new Euro());
					}
					
				} else {
					// nessun cliente trovato
					throw new Exception("nessun cliente con id "+cliente.getId()+" è presente nel database");
				}
				
				result.close();
				state.close();
				connessione.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		public static void elencoCattiviPagatori() {
			System.out.println("non ce ne sono");
		}
	}
}
