package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import business.Cliente;
import business.Euro;


/**
 * Cliente Data Access Object
 * Questa classe si occupa della lettura e scrittura nel DB di dati che riguardano un Cliente
 * CRUD: Create, Read, Update, Delete
 * @author nico
 *
 */
public class ClienteDao {


	

	/**
	 * Recupera i dati dal database e cerca di riempire l'oggetto Cliente con più dati possibili.<br>
	 * Alcuni saranno obbligatori e quindi sicuramente presenti nel database, mentre altri facoltativi.<br>
	 * In quest'ultimo caso, i relativi attributi verranno settati sul loro valore di default o lasciati vuoti.
	 * 
	 * ATTENZIONE! Questo metodo sovrascrive gli attributi già memorizzati nell'oggetto Cliente.<br>
	 * Tipicamente questo metodo viene usato immediatamente dopo aver istanziato un nuovo oggetto Cliente per mezzo dell'id:<br>
	 * <pre>
	 * 	Cliente titolare = new Cliente(42);
	 * 	titolare.read();
	 * 	...
	 * </pre>
	 * 
	 * @param cliente oggetto Cliente
	 * @throws Exception se viene passato un cliente che non esiste nel database
	 */
	public Cliente getCliente(int id) throws Exception {

		Cliente cliente = new Cliente(id);
		
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
				// nessun cliente selezionato!!
				throw new Exception("nessun cliente con id "+cliente.getId()+" è presente nel database");
			}
			
			result.close();
			state.close();
			connessione.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cliente;
		
	}
	
	


}
