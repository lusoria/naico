package dao;

import java.util.ArrayList;

import model.Cliente;


public interface ClienteDao {

	
	public void getCliente(int id);
	
	public ArrayList<Cliente> getTuttiClienti();
	
	public ArrayList<Cliente> getDebitori();
	
	
	
}
