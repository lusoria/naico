package test;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import util.Data;

public class TestData {

	
	
	@Test
	public void testOrdinamento() {
		ArrayList<Data> elencoDate = new ArrayList<Data>();
		elencoDate.add(new Data("01/01/2014"));
		elencoDate.add(new Data("02/01/2014"));
		elencoDate.add(new Data("30/12/2013"));
		elencoDate.add(new Data("31/12/2013"));
		elencoDate.add(new Data("31/12/2014"));
		elencoDate.add(new Data("06/06/2014"));
		elencoDate.add(new Data("06/06/2013"));

		ArrayList<Data> elencoDateOrdinate = new ArrayList<Data>();
		elencoDate.add(new Data("01/01/2013"));
		elencoDate.add(new Data("06/06/2013"));
		elencoDate.add(new Data("30/12/2013"));
		elencoDate.add(new Data("31/12/2013"));
		elencoDate.add(new Data("02/01/2014"));
		elencoDate.add(new Data("02/06/2013"));
		elencoDate.add(new Data("31/12/2013"));
		
		Collections.sort(elencoDate);
		boolean uguale = false;

	}
	
	@Test
	public void testCostruttore() {
		


	}

}
