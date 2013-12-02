package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Cliente;
import model.soldi.Euro;
import util.DataBaseHelper;


/**
 * Cliente Data Access Object
 * Questa classe si occupa della lettura e scrittura nel DB di dati che riguardano un Cliente
 * CRUD: Create, Read, Update, Delete
 * @author nico
 *
 */
public class ClienteDao {


	/**
	 * Costruttore nascosto
	 */
	private ClienteDao() {}
	
	
	/**
	 * Interroga il database e cerca di riempire un oggetto Cliente con più dati possibili.<br>
	 * Alcuni campi saranno obbligatori e quindi sicuramente presenti nel database, mentre altri facoltativi.<br>
	 * In quest'ultimo caso, i relativi attributi verranno settati sul loro valore di default o lasciati vuoti.
	 * @param id identificativo del cliente sul DB
	 * @throws Exception se viene passato un id che non esiste nel database
	 */
	public static Cliente getCliente(int id) throws Exception {

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
			DataBaseHelper.manageError(e);
		}
		
		return cliente;
		
	}



	/**
	 * Restituisce la lista di tutti i clienti presenti nel Database, ordinati alfabeticamente
	 * per cognome.
	 * @return
	 */
	public static ArrayList<Cliente> getTuttiClienti() {

		ArrayList<Cliente> clienti = new ArrayList<Cliente>();
		
		Connection connessione = null;
		Statement state = null;
		ResultSet result = null;
		String query = "SELECT * FROM cliente ORDER BY cognome";
		
		connessione = DataBaseHelper.getConnection();
		
		try {
		
			state = connessione.createStatement();
			result = state.executeQuery(query);
		
			// recupero i dati
			if (result.isBeforeFirst()) {
				while(result.next()) {
				
					Cliente cliente = new Cliente(result.getInt("id"));
					
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
					
					clienti.add(cliente);
				}
				
			}
			
			result.close();
			state.close();
			connessione.close();
			
		} catch (SQLException e) {
			DataBaseHelper.manageError(e);
		}
		
		return clienti;
	}
	
	
	

}
