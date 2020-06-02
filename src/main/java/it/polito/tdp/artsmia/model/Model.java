package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao=new ArtsmiaDAO();
	private Graph<Artist,DefaultWeightedEdge> grafo; 
	private Map<Integer,Artist> idMapArtists=new HashMap<>();
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
	public Integer getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	public Integer getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	public List<Arco> getArtistiConnessi(String role){
		List<Arco> archi=dao.getArchi(role,idMapArtists);
		return archi;
	}

	public List<Artist> calcolaPercorso(Integer artistID) {
		List<Artist> visita= new ArrayList<Artist>();
		DepthFirstIterator<Artist,DefaultWeightedEdge> dfv= new DepthFirstIterator<>(this.grafo, idMapArtists.get(artistID));
		while(dfv.hasNext()) {
			visita.add(dfv.next());
		}
		return visita;
	}
}
