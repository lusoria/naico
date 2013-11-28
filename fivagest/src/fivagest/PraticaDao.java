package fivagest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PraticaDao {

	
	/**
	 * Recupera i dati dal database e cerca di riempire l'oggetto Pratica con più dati possibili.
	 * Alcuni saranno obbligatori e quindi sicuramente presenti nel database, mentre altri facoltativi.
	 * @param pratica
	 * @throws Exception
	 */
	public static void load(Pratica pratica) throws Exception {
		Connection connessione = null;
		Statement state = null;
		ResultSet result = null;
		String query = "SELECT * FROM Pratica WHERE id = "+pratica.getId();
		
		connessione = DataBaseHelper.getConnection();
		try {
			state = connessione.createStatement();
			result = state.executeQuery(query);
		
			if (result.isBeforeFirst()) {
				result.first();
				
				// cliente obbligatorio
				pratica.setCliente(new Cliente(result.getInt("cliente")));
				
				// imponibile obbligatorio
				// ATTENZIONE!! aliquota al 22% e importo non ivato HARD CODED!
				Importo imponibile = new Importo(result.getBigDecimal("imponibile"), new Aliquota(22), false);
				pratica.setImponibile(imponibile);
				
				// spese obbligatorie
				pratica.setSpese(new Euro(result.getBigDecimal("spese")));
				
				// descrizione obbligatoria
				pratica.setDescrizione(result.getString("descrizione"));
				
				// data pagamento obbligatoria
				pratica.setDataPagamentoMYSQL(result.getString("dataPagamento"));
				
			} else {
				// nessuna pratica selezionata!!
				throw new Exception("Non esiste la pratica con id: "+pratica.getId()+".");
			}
		
			result.close();
			state.close();
			connessione.close();
			
		} catch(SQLException erroreSQL) {
			DataBaseHelper.manageError(erroreSQL, query);
		}
	}
	
	
	
	/**
	 * <p>Memorizza nel db una Pratica appena creata (quindi si presuppone almeno con i campi obbligatori istanziati).</p>
	 * @param pratica	pratica da caricare nel db
	 */
	public static void store(Pratica pratica) {
		// TODO: implementare
		
	}
	
	
	
	/**
	 * <p>Restituisce un ArrayList con tutte le pratiche del Cliente passato come parametro.</p>
	 * </p>Se il Cliente non ha pratiche, restituisce un ArrayList vuoto.</p>
	 * @param cliente
	 * @return
	 */
	public static ArrayList<Pratica> selectPraticheNonPagateByCliente(Cliente cliente) {
		Connection connessione = null;
		Statement state = null;
		ResultSet result = null;
		String query = "SELECT * FROM Pratica WHERE cliente = "+cliente.getId()+" AND pagata = 0";
		
		connessione = DataBaseHelper.getConnection();
		
		ArrayList<Pratica> pratiche = new ArrayList<Pratica>();
		try {
			state = connessione.createStatement();
			result = state.executeQuery(query);
			
			
			if (result.isBeforeFirst()) {
				// qualcosa c'è
				while(result.next()) {
					Pratica pratica = new Pratica(result.getInt("id"));
					pratica.setCliente(cliente);
					pratica.setImponibile(new Importo(result.getDouble("imponibile"), new Aliquota(22), false));
					pratica.setSpese(new Euro(result.getDouble("spese")));
					pratica.setDescrizione(result.getString("descrizione"));
					pratica.setDataPagamentoMYSQL(result.getString("dataPagamento"));
					
					pratiche.add(pratica);
					
				}
				
			}
			
			result.close();
			state.close();
			connessione.close();
			
		} catch(SQLException erroreSQL) {
			DataBaseHelper.manageError(erroreSQL, query);
		}
			
		return pratiche;
		
	}
}
