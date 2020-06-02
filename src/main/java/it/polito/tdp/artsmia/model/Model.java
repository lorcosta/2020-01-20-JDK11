package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao=new ArtsmiaDAO();
	private Graph<Artist,DefaultWeightedEdge> grafo; 
	private Map<Integer,Artist> idMapArtists;
	public List<String> getRoles(){
		return dao.listRole();
	}
	
	public void creaGrafo(String role) {
		grafo=new SimpleWeightedGraph<Artist,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Artist> vertici=dao.getArtistSelectedRole(role,idMapArtists);
		Graphs.addAllVertices(this.grafo, vertici);
		List<Arco> archi=dao.getArchi(role,idMapArtists);
		for(Arco a:archi) {
			if(this.grafo.getEdge(a.getA1(), a.getA2())==null)
				Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
	}
}
