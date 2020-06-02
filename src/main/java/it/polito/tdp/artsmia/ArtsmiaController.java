package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi\n");
    	String role=this.boxRuolo.getValue();
    	if(role==null)
    		txtResult.appendText("ERRORE. Nessun ruolo selezionato, selezionarne uno.\n");
    	doCreaGrafo(event);
    	List<Arco> archi=model.getArtistiConnessi(role);
    	if(archi==null) {
    		this.txtResult.appendText("Errore! Creare prima il grafo.\n");
    	}
    	Collections.sort(archi);
    	for(Arco a:archi) {
    		this.txtResult.appendText("\n"+a.toString());
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso\n");
    	String stringID=this.txtArtista.getText();
    	Integer artistID;
    	try {
    		artistID=Integer.parseInt(stringID);
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ERRORE! Il valore inserito non è un numero corretto");
    		throw new NumberFormatException("Errore nel parsificare il numero inserito");
    	}
    	List<Artist> visita=model.calcolaPercorso(artistID);
    	for(Artist a:visita) {
    		//System.out.println()""+a.getName()+" "+a;
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo\n");
    	String role=this.boxRuolo.getValue();
    	if(role==null)
    		txtResult.appendText("ERRORE. Nessun ruolo selezionato\n");
    	model.creaGrafo(role);
    	this.txtResult.appendText("Grafo creato con "+model.getNumArchi()+" archi e "+model.getNumVertici()+"vertici.\n");
    }

    public void setModel(Model model) {
    	this.model = model;
    	loadData();
    }
    public void loadData() {
    	List<String> roles=model.getRoles();
    	this.boxRuolo.getItems().addAll(roles);
    }
    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
