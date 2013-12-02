package model.soldi;


/**
 * <p>Aliquota IVA</p>
 * <p>rappresentazione in oggetti dell'aliquota IVA.</p>
 * <p>Inizializzando un oggetto Aliquota, si ha la certezza di applicare un'aliquota corretta e aggiornata.</p>
 * <p>La classe fornisce due array di interi pubblici:<br>
 * in ALIQUOTE[] sono salvate tutte le possibili aliquote che il programma abbia mai visto, quindi attuali e storiche (non pi� valide)<br>
 * in ALIQUOTEATTUALI[] sono salvate invece solamente le aliquote valide in data odierna</p>
 * @author nico
 *
 */
public class Aliquota {
	
	private int aliquota;
	
	public static final int ALIQUOTE[]={21,22};	// aliquote possibili, anche storiche e quindi non pi� valide
	public static final int ALIQUOTEATTUALI[] = {22};	// aliquote attuali. Ho omesso l'iva al 4 e al 10 perch� Paolo non le user� mai.
	
	
	public Aliquota(int aliquota) {
		// SISTEMAREEEE!!!
		if(aliquota == 22) {
			this.aliquota = aliquota;
		}else{
			System.err.println("aliquota IVA non valida!");
			System.exit(1);
		}
		
	}
	
	
	public String toString() {
		return aliquota+" %";
	}
	
	
	public int getAliquota() {
		return this.aliquota;
	}
}
