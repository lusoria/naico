package dao;

import java.util.ArrayList;
import business.Cliente;


public interface ClienteDao {

	public ArrayList<Cliente> getTuttiClienti();
	
	public void getCliente(int id);
	
	public ArrayList<Cliente> getDebitori();
	
	
	
}
