package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DataBaseHelper;
import business.PartitaIva;

public class TestDB1 {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		/*
		 * Carico e testo tutte le Partite IVA registrate nel db.
		 * 
		 * OUTPUT:
		 * 
		 * 11 partite iva corrette!
		 * Partita IVA non valida: 64738473845
		 * 
		 */

		Connection conn = DataBaseHelper.getConnection();
		Statement state = conn.createStatement();
		ResultSet rs = state
				.executeQuery("SELECT partitaIVA, cognome FROM cliente");

		int countCorrette = 0;

		while (rs.next()) {

			String strIva = rs.getString("partitaIVA");
			if (strIva != null) {

				PartitaIva piva = null;
				try {
					piva = new PartitaIva(strIva);
				} catch (Exception e) {
					// errore nella creazione di una Partita IVA
					System.out.println(e.getMessage());
					System.exit(1);
				}

				countCorrette += 1;

			}

		}

		System.out.println(countCorrette + " partite iva corrette!");

		rs.close();
		state.close();
		conn.close();

		
		// TEST Partita Iva scorretta

		PartitaIva piva = null;
		try {
			piva = new PartitaIva("64738473845");
		} catch (Exception e) {
			// errore nella creazione di una Partita IVA
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

}
