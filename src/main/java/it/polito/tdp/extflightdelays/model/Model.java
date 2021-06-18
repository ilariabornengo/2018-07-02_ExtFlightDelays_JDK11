package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	ExtFlightDelaysDAO dao;
	Map<Integer,Airport> idMap;
	Graph<Airport,DefaultWeightedEdge> grafo;
	List<Airport> listaBest;
	public int distanzaTot;
	
	public Model()
	{
		this.dao=new ExtFlightDelaysDAO();
	}
	
	public void creaGrafo(Integer soglia)
	{
		this.idMap=new HashMap<Integer,Airport>();
		this.grafo=new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//inserisco i vertici
		this.dao.getVerticiOrigin(idMap);
		this.dao.getVerticiDESTIN(idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//inserisco gli archi
		for(Adiacenza a:this.dao.getAdiacenze(idMap, soglia))
		{
			if(this.grafo.vertexSet().contains(a.getA1()) && this.grafo.vertexSet().contains(a.getA2()))
			{
				Graphs.addEdge(this.grafo,a.getA1(), a.getA2(), a.getPeso());
			}
		}
	}
	
	public List<Adiacenza> getAdiacenti(Airport a)
	{
		List<Adiacenza> adiacenti=new ArrayList<Adiacenza>();
		for(Airport ai:Graphs.neighborListOf(this.grafo, a))
		{
			int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(a, ai));
			
			Adiacenza ad=new Adiacenza(a,ai,peso);
			adiacenti.add(ad);
		}
		Collections.sort(adiacenti, new ComparatorAdiacenze());
		return adiacenti;
	}
	public List<Airport> getAirport()
	{
		List<Airport> lista=new ArrayList<Airport>(this.grafo.vertexSet());
		Collections.sort(lista, new ComparatorAir());
		return lista;
	}
	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
	
	
	public List<Airport> getListaBest(Airport partenza, Integer soglia)
	{
		this.listaBest=new ArrayList<Airport>();
		List<Airport> parziale=new ArrayList<Airport>();
		this.distanzaTot=0;
		parziale.add(partenza);
		ricorsione(parziale,0,soglia);
		return this.listaBest;
	}

	private void ricorsione(List<Airport> parziale, int i, Integer soglia) {
		
		Airport ultimo=parziale.get(parziale.size()-1);
		for(Airport a:Graphs.neighborListOf(this.grafo, ultimo))
		{
			if(!parziale.contains(a))
			{
				
				int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, a));
				if(i+peso<=soglia)
				{
					parziale.add(a);
					i=i+peso;
					ricorsione(parziale,i,soglia);
					parziale.remove(a);
				}
			}
		}
		
		if(i<=soglia)
		{
			if(parziale.size()>this.listaBest.size())
			{
				this.listaBest=new ArrayList<Airport>(parziale);
				distanzaTot=i;
				return;
			}
			else
			{
				return;
			}
		}
		
	}
}
