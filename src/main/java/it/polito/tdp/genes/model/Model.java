package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	private List<Integer> risultato;
	
	
	public Model() {
		
		this.dao = new GenesDao();
		
	}
	
	
	public String creaGrafo() {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici());
		
		for(Adiacenza a : this.dao.getArchi()) {
			if(!this.grafo.containsEdge(a.getC1(), a.getC2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getPeso());
			}	
		}
		
		return "Grafo creato con "+ this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+
				" archi.";
	}
	
	
	
		public double getPesoMin() {
			
			double min = 1000;
			
			for(Adiacenza a : this.dao.getArchi()) {
				if(a.getPeso()<min)
					min=a.getPeso();
			}
			return min;
		}
	
	public double getPesoMax() {
				
				double max = 0;
				
				for(Adiacenza a : this.dao.getArchi()) {
					if(a.getPeso()>max)
						max=a.getPeso();
				}
				return max;
			}
	
	
	public boolean ifCompreso(double soglia) {
		
		if(soglia<=this.getPesoMax() && soglia>=this.getPesoMin())
			return true;
		else
			return false;
		
	}
	
	
	public int getArchiMaggiori(double soglia) {
		
		int cont = 0;
		
		for(Adiacenza a : this.dao.getArchi()) {
			if(a.getPeso()>soglia)
			cont++;
		}
		return cont;
	}
	
	public int getArchiMinori(double soglia) {
			
			int cont = 0;
			
			for(Adiacenza a : this.dao.getArchi()) {
				if(a.getPeso()<soglia)
				cont++;
			}
			return cont;
		}
	
	
	public void getCammino(double soglia) {
		
		this.risultato = new ArrayList<>();
		List<Integer> parziale = new ArrayList<Integer>();
		
		for(Integer i : this.dao.getVertici()) {
			parziale.add(i);
			ricorsione (parziale, soglia);
			parziale.remove(parziale.size()-1);
		}
		
		
	}
	
	public List<Integer> getRisultato() {
		return risultato;
	}


	private void ricorsione(List<Integer> parziale, double soglia) {
		
		if(calcolaPeso(parziale)>calcolaPeso(this.risultato)) {
			this.risultato = new ArrayList(parziale);
		}
		
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(parziale.get(parziale.size()-1))) {
			
			Integer vertice = this.grafo.getEdgeTarget(e);
			
			if(!parziale.contains(vertice) && this.grafo.getEdgeWeight(e)>soglia) {
				parziale.add(vertice);
				ricorsione(parziale, soglia);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	
	public Double calcolaPeso(List<Integer> parziale) {
		
		double peso=0.0;
		
		for(int i=0; i<parziale.size()-1; i++) {
			peso += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		return peso;
	}
	
	
	
}