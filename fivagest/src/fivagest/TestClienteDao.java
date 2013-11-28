package fivagest;

public class TestClienteDao {

	public static void main(String[] args) {

		/*
		 * Testo alcuni possibili caricamenti dei clienti dal DB
		 */

		// carico un cliente esistente nel db
		System.out.println("CLIENTE VERO");
		Cliente clienteVero = new Cliente(5);
		System.out.println("pec di " + clienteVero + ": "+ clienteVero.getPec());
		try {
			clienteVero.leggiDB();
		} catch (Exception e) {
			System.out.println("errore!");
			System.out.println(e.getMessage());
		}
		System.out.println("pec di " + clienteVero + ": "+ clienteVero.getPec());

		// creo un cliente con id che non esiste nel db
		System.out.println("CLIENTE FALSO");
		Cliente clienteFalso = new Cliente(999);
		try {
			clienteFalso.leggiDB();
		} catch (Exception e) {
			System.out.println("errore!");
			System.out.println(e.getMessage());
		}

		// carico un cliente esistente nel db ma con partita IVA errata
		System.out.println("\nCLIENTE ERRATO");
		Cliente clienteErrato = new Cliente(1);
		try {
			clienteErrato.leggiDB();
		} catch (Exception e) {
			System.out.println("errore!");
			System.out.println(e.getMessage());
		}

	}

}
