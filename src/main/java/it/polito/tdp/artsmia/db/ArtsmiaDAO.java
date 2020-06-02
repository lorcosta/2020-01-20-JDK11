package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> listRole(){
		String sql="SELECT DISTINCT(role) " + 
				"FROM authorship";
		List<String> roles=new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				String role=res.getString("role");
				roles.add(role);
			}
			conn.close();
			return roles;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Artist> getArtistSelectedRole(String role, Map<Integer,Artist> idMapArtists){
		String sql="SELECT DISTINCT(artists.artist_id),artists.name " + 
				"FROM authorship, artists " + 
				"WHERE authorship.artist_id=artists.artist_id AND role=?";
		List<Artist> selectedArtists= new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Artist a=new Artist(res.getInt("artist_id"),res.getString("name"));
				idMapArtists.put(a.getArtistID(),a);
				selectedArtists.add(a);
			}
			conn.close();
			return selectedArtists;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> getArchi(String role, Map<Integer,Artist> idMapArtists){
		String sql="select a1.artist_id AS a1, a2.artist_id AS a2, COUNT(DISTINCT eo1.exhibition_id) AS peso " + 
				"from artists a1, artists a2, authorship au1, authorship au2, exhibition_objects eo1, exhibition_objects eo2 " + 
				"where au1.role = ? and au2.role = ? " + 
				"and au1.artist_id = a1.artist_id and au2.artist_id = a2.artist_id " + 
				"and au1.object_id = eo1.object_id and au2.object_id = eo2.object_id " + 
				"and eo1.exhibition_id = eo2.exhibition_id " + 
				"and a1.artist_id > a2.artist_id " + 
				"GROUP BY a1.artist_id, a2.artist_id";
		List<Arco> archi=new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			st.setString(2, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Arco a=new Arco(idMapArtists.get(res.getInt("a1")),idMapArtists.get(res.getInt("a2")),res.getInt("peso"));
				archi.add(a);
			}
			conn.close();
			return archi;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
