package model;

import java.util.Comparator;

import model.soldi.Euro;
import model.soldi.Importo;
import util.Data;


/**
 * Un oggetto Pratica � la rappresentazione di un qualsiasi lavoro/servizio/oggetto
 * che l'ufficio fornisce ad un solo cliente e che viene ricompensato da un solo importo in Euro (>= 0.00 �).
 * Un oggetto Pratica ha anche una voce Spese (in Euro) che rappresenta la somma di tutte quelle spese esenti iva
 * (quindi non oggetti Importi) che � utile che stiano visibilmente separate e che, in fattura, avranno una voce dedicata.
 * @author nico
 *
 */
public class Pratica implements Comparable<Pratica>{

	
	private int id;
	private Cliente cliente;
	private Importo imponibile;
	private Euro spese;
	private String descrizione;
	/*
	data pagamento:
	Data dalla quale il cliente sar� ufficialmente in debito per la pratica in oggetto.
	Molto spesso coincider� con la data di inserimento della pratica nel sistema, poich� si inizia subito a lavorarci e il cliente deve pagare da subito.
	Ma a volte capita che una pratica viene inserite all'inizio dell'anno, ma non deve comparire tra i debiti del cliente, poich� non � ancora tempo.
	Esempio: contabilit� dei trimestri futuri.
	*/
	private Data dataPagamento;
	private boolean pagata;
	
	
	/**
	 * Costruttore per rappresentare una nuova pratica, non ancora presente nel database.
	 * Con questo costruttore si potr� istanziare un oggetto pronto per essere memorizzato nel database poich�
	 * qui bisogna fornire tutti gli attributi obbligatori.
	 * @param cliente Cliente per il quale � stata aperta la pratica da creare
	 * @param imponibile Euro di costo per il cliente della pratica da creare
	 * @param spese Euro di spese nostre sostenute per avviare la pratica
	 * @param descrizione Nome o brevissima descrizione della pratica
	 * @param dataPagamento Data dalla quale il cliente sar� ufficialmente in debito
	 */
	public Pratica(Cliente cliente, Importo imponibile, Euro spese, String descrizione, Data dataPagamento) {
		// TODO: controllini sui parametri
		this.cliente = cliente;
		this.imponibile = imponibile;
		this.spese = spese;
		this.descrizione = descrizione;
		this.setDataPagamento(dataPagamento);
		this.pagata = false;
		
	}
	
	
	/**
	 * Costruttore per una pratica vuota.
	 * @param id identificativo della pratica nel database
	 */
	public Pratica(int id) {
		this.id = id;
	}
	
	
	/**
	 * Restituisce l'identificativo univoco con la quale la Pratica � salvata nel database. 
	 * @return	int identificativo univoco della pratica
	 */
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Restituisce la descrizione (ovvero il titolo) della Pratica (es.: "PEC: ATTIVAZIONE E COMUNICAZIONE")
	 * @return descrizione (stringa tutta maiuscola)
	 */
	public String getDescrizione() {
		return descrizione.toUpperCase();
	}
	
	
	/**
	 * Imposta la descrizione della Pratica corrente.
	 * @param descrizione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	/**
	 * Restituisce il Cliente (l'unico) della Pratica
	 * @return oggetto Cliente
	 */
	public Cliente getCliente() {
		return this.cliente;
	}
	
	
	/**
	 * Imposta il cliente della pratica corrente
	 * @param cliente (oggetto Cliente)
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	/**
	 * Restituisce il costo della Pratica per il Cliente
	 * @return imponibile (oggetto Importo)
	 */
	public Importo getImponibile() {
		return this.imponibile;
	}
	
	
	/**
	 * Imposta il valore dell'imponibile (costo della pratica al cliente)
	 * @param imponibile (oggetto Importo, tendenzialmente non ancora ivato)
	 */
	public void setImponibile(Importo imponibile) {
		if(imponibile.isPositivo()) {
			this.imponibile = imponibile;			
		}else{
			// TODO: inserire eccezione!
		}
		
	}
	
	
	/**
	 * Restituisce le spese affrontate dallo studio per la Pratica corrente.
	 * Essenso spese esenti IVA, il metodo restituisce un oggetto Euro, dove appunto l'iva non potr� essere applicata.
	 * @return	spese esenti IVA 
	 */
	public Euro getSpese() {
		return this.spese;
	}
	
	
	/**
	 * Imposta le spese per la Pratica corrente in Euro.
	 * @param spese
	 */
	public void setSpese(Euro spese) {
		if(spese.isPositivo()) {
			this.spese = spese;
		}else{
			// TODO: scrivere l'eccezione per spese negative
		}
	}
	
	
	/**
	 * Restituisce la data di pagamento della pratica corrente.<br>
	 * @return data di pagamento
	 */
	public Data getDataPagamento() {
		return dataPagamento;
	}
	
	
	/**
	 * Imposta la data del pagamento. Vedi la documentazione tra gli attributi
	 * @param dataPagamento
	 */
	public void setDataPagamento(Data dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	

	/**
	 * Restituisce se la Pratica corrente � stata pagata oppure no.  
	 * @return	boolean
	 */
	public boolean isPagata() {
		return this.pagata;
	}
	
	
	
	/**
	 * Imposta la Pratica corrente come pagata.<br>
	 * Non � consentita l'operazione inversa.
	 */
	public void setPagata() {
		this.pagata = true;
	}
	
	
	/**
	 * Stampa la descrizione della Pratica. SOLO PER DEBUG!
	 */
	public void printPratica() {
		Cliente tizio = this.cliente;

		// TODO: tizio � sincronizzato col db?
		
		System.out.println("-----");
		System.out.println("Pratica ["+this.getId()+"] di "+this.imponibile+" con "+this.getSpese()+" di spese: "+this.getDescrizione());
		System.out.println("per "+tizio);
		System.out.println("pagamento al "+this.getDataPagamento());
	}
	

	
	/**
	 * alfabeticamente sulla descrizione!!
	 */
	@Override
	public int compareTo(Pratica pratica) {
		// TODO Auto-generated method stub
		return this.descrizione.compareToIgnoreCase(pratica.getDescrizione());
		//return this.getData().compareTo(pratica.getData());
	}
	
	
	/**
	 * Classe comparator. Implementa unicamente il metodo compare e serve per ordinare le Pratiche per loro Imponibile
	 * dal pi� basso al pi� alto.
	 * @author nico
	 *
	 */
	static class ImponibileComparator implements Comparator<Pratica> {

		@Override
		public int compare(Pratica p1, Pratica p2) {
			return p1.getImponibile().compareTo(p2.getImponibile());
		}
	}
	
	
	/**
	 * Sottoclasse comparator. Implementa unicamente il metodo compare e serve per ordinare le Pratiche per Data di pagamento
	 * dalla meno recente alla pi� recente.
	 * @author nico
	 *
	 */
	static class DataPagamentoComparator implements Comparator<Pratica> {

		@Override
		public int compare(Pratica p1, Pratica p2) {
			return p1.getDataPagamento().compareTo(p2.getDataPagamento());
		}
		
	}
}
