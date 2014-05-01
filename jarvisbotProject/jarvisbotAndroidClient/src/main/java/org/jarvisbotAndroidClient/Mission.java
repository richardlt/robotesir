package org.jarvisbotAndroidClient;

import java.io.Serializable;

public class Mission implements Serializable {
	

	private static final long serialVersionUID = -4883257587265912047L;
	private String m_nom;
	private String m_Email;
	private boolean m_retourDepart;
	private boolean m_prendrePhotosArrivee;
	private int[] m_fin;
	
	/**
	 * Constructeur de la mission
	 */
	public Mission(String nom, String mail, boolean retourDepart, boolean prendrePhotosA){
		m_nom=nom;
		m_Email=mail;
		m_retourDepart=retourDepart;
		m_prendrePhotosArrivee=prendrePhotosA;
	}
	
	/** 
	 * @return le nom de la mission
	 */
	public String getNom(){
		return m_nom;
	}
	
	/**
	 * Change le nom de la mission
	 * @param n : nouveau nom de la mission
	 */
	public void setNom(String n){
		m_nom=n;
	}
	
	/**
	 * @return l'Email de la mission
	 */
	public String getEmail(){
		return m_Email;
	}
	
	/**
	 * Change l'Email de la mission
	 * @param em : le nouvel Email de la mission
	 */
	public void setEmail(String em){
		m_Email=em;
	}
	
	/**
	 * @return si le retour au point de d�part, une fois la mission finie, est activ� ou non
	 */
	public boolean getRetourDepart(){
		return m_retourDepart;
	}
	
	/**
	 * Permet de d�finir le retour ou non au point de d�part une fois la mission finie.
	 * @param rd : si false attend un nouvel ordre, sinon retour au point de d�part.
	 */
	public void setRetourDepart(boolean rd){
		m_retourDepart=rd;
	}
	
	/**
	 * @return si la prise de photos au point d'arriv�e est activ�e ou non
	 */
	public boolean getPrendrePhotosArrivee(){
		return m_prendrePhotosArrivee;
	}
	
	/**
	 * Permet d'activ�e/d�sactiver la prise de photos au point d'arriv�e
	 * @param rd : si false prise de photos d�sactiv�e, sinon activ�e.
	 */
	public void setPrendrePhotosArrivee(boolean pp){
		m_prendrePhotosArrivee=pp;
	}
	
	/**
	 * @return les coordonn�es (lattitude en case 0 et longitude en case 1) du point d'arriv�e
	 */
	public int[] getM_fin() {
		return m_fin;
	}

	/**
	 * initialise/modifie le point d'arriv�e (lattitude en case 0 et longitude en case 1)
	 * @param m_fin le nouveau point d'arriv�e (lattitude en case 0 et longitude en case 1)
	 * @throws Exception si le tableau de coordonn�es n'a pas uniquement 2 cases
	 */
	public void setM_fin(int[] fin) throws Exception {
		if (fin.length==2){
			this.m_fin = fin;
		}
		else throw new Exception("demande non-r�alisable : tableau de coordonn�es debut de mauvaise taille");		
	}
	
	/**
	 * renvoie le nom de la mission
	 */
	public String toString(){
		return m_nom;
	}
}
