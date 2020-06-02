package it.polito.tdp.artsmia.model;

import java.util.List;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao=new ArtsmiaDAO();
	
	public List<String> getRoles(){
		return dao.listRole();
	}
}
