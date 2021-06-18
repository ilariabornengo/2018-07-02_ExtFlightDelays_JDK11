package it.polito.tdp.extflightdelays.model;

import java.util.Comparator;

public class ComparatorAir implements Comparator<Airport> {

	@Override
	public int compare(Airport o1, Airport o2) {
		// TODO Auto-generated method stub
		return o1.getAirportName().compareTo(o2.getAirportName());
	}

}
