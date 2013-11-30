package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import business.Cliente;
import business.Euro;


/**
 * Cliente Data Access Object
 * Questa classe si occupa della lettura e scrittura nel DB di dati che riguardano un Cliente
 * CRUD: Create, Read, Update, Delete
 * @author nico
 *
 */
public class ClienteDaoImpl implements ClienteDao {


	/**
	 * Costruttore nascosto
	 */
	private ClienteDaoImpl() {}
	
	
	/**
	 * Recupera i dati dal database e cerca di riempire un oggetto Cliente con più dati possibili.<br>
	 * Alcuni saranno obbligatori e quindi sicuramente presenti nel database, mentre altri facoltativi.<br>
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cliente;
		
	}
	
	
	
	/**
	 * Restituisce una lista con tutti i clienti presenti nel DB.
	 * @return lista con tutti i clienti
	 */
	public static ArrayList<Cliente> getTuttiClienti() {
		// TODO: da implementare
		return null;
	}




	@Override
	public ArrayList<Cliente> getDebitori() {
		// TODO Auto-generated method stub
		return null;
	}


}
