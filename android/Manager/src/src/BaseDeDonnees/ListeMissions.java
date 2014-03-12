package BaseDeDonnees;

import java.io.Serializable;
import java.util.LinkedList;

public class ListeMissions implements Serializable{
	
	private static final long serialVersionUID = -5641214778914549383L;
	
	//attributs
	private LinkedList<Mission> m_listMissions;
	
	/**
	 * Constructeur d'une liste de missions
	 */
	public ListeMissions (){
		m_listMissions= new LinkedList<Mission>();
	}
	
	/**
	 * @return la liste de toute les missions
	 */
	public LinkedList<Mission> getListe(){
		return m_listMissions;
	}
	
	/**
	 * Remplace l'ancienne liste des missions par une nouvelle
	 * @param lm : la nouvelle liste des missions
	 */
	public void setListe(LinkedList<Mission> lm){
		m_listMissions=lm;
	}
	
	/**
	 * Ajoute une mission à la liste
	 * @param m : la mission à ajouter
	 */
	public void addMissions(Mission m){
		m_listMissions.add(m);
	}
	
	/**
	 * Supprime une mission de la liste
	 * @param m : la mission à supprimer
	 */
	public void removeMissions(Mission m){
		if(m_listMissions.contains(m)){
			m_listMissions.remove(m);
		}
	}
	
	/**
	 * Permet de sélectionner une mission dans la base
	 * @param nom : la mission recherchée
	 * @return la mission si elle existe, null sinon
	 */
	public Mission getMissions(String nom){
		for (int i=0; i<m_listMissions.size(); i++){
			if(m_listMissions.get(i).getNom().equals(nom)){
				return m_listMissions.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Supprime tout les éléments de la liste
	 */
	public void viderListe(){
		m_listMissions.clear();
	}
	
	/**
	 * Permet de savoir si la mission existe dans la base ou non
	 * @param m : la mission à chercher
	 * @return vrai si la mission est dans la base faux sinon
	 */
	public boolean existeMission(Mission m){
		return (!(getMissions(m.getNom())==null));
	}
}
