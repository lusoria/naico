package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import model.Cliente;
import model.Pagamento;
import model.Pratica;
import model.soldi.Euro;
import util.Data;
import util.DataBaseHelper;


/**
 * Pagamento Data Access Object
 * Questa classe si occupa della lettura e scrittura nel DB di dati che riguardano un Pagamento
 * CRUD: Create, Read, Update, Delete
 * @author nico
 *
 */
public class PagamentoDao {

	/**
	 * Costruttore nascosto
	 */
	private PagamentoDao() {}
	
	
	/**
	 * Interroga il database e restituisce un oggetto Pagamento.
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static Pagamento getPagamento(int id) throws Exception {
		
		Pagamento pagamento = new Pagamento(id);
		
		Connection connessione = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		
		connessione = DataBaseHelper.getConnection();
		
		try {
			ps = connessione.prepareStatement("SELECT * FROM Pagamento WHERE id = ?");
			ps.setInt(1, pagamento.getId());
			
			result = ps.executeQuery();
			
			// recupero i dati
			if (result.isBeforeFirst()) {
				result.first();
				
				// cliente
				pagamento.setCliente(ClienteDao.getCliente(result.getInt("cliente")));
				
				// importo
				pagamento.setPagamento(new Euro(result.getDouble("ammontare")));
				
				// data
				pagamento.setData(new Data(result.getString("data")));
				
				// paga anche l'IVA?
				pagamento.setPagaAncheIva(result.getBoolean("pagaIVA"));
				
				
			} else {
				// nessun pagamento selezionato
				throw new Exception("nessuna pagamento con id "+pagamento.getId()+" è presente nel database");
			}
			
		} catch (SQLException e) {
			DataBaseHelper.manageError(e);
		}
		
		return pagamento;
	}
	
	
	
	public static void effettuaPagamento(Cliente cliente, Pagamento pagamento) {
		if(!pagamento.getPagamento().isZero()) {
			// raccolgo le pratiche non pagate del cliente
			ArrayList<Pratica> praticheNonPagate = PraticaDao.selectPraticheNonPagateByCliente(cliente);
			
			// ordino le pratiche non pagate secondo un criterio di urgenza
			Collections.sort(praticheNonPagate, new Pratica.DataPagamentoComparator());
			
			// sommo questo pagamento con l'eventuale acconto virtuale del cliente
		}		
	}
	
}
