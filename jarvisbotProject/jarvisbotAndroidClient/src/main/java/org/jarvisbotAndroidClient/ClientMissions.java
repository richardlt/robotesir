package org.jarvisbotAndroidClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

public class ClientMissions {

	//attributs
	private ListeMissions m_listM;
	private Mission m_missionEnCours;
	private Context m_c;
	
	/**
	 * Cr�ation du client qui va g�rer la base de donn�es des missions
	 */
	public ClientMissions(Context c){
		m_c=c;
		m_listM=loadMission(c);
		m_missionEnCours= null;
	}
	
	/**
	 * @return la liste des missions enregistr�es dans la base
	 */
	public ListeMissions getListeMissions(){
		return m_listM;
	}
	
	/**
	 * Permet de changer la liste des missions par une autre
	 * @param lm : la nouvelle liste des missions
	 */
	public void setListeMissions(ListeMissions lm){
		m_listM.setListe(lm.getListe());
	}
	
	/**
	 * Cr�� une nouvelle mission dans la base et la place en tant que mission en cours
	 * @throws Exception : si le nom de la nouvelle mission existe d�j� 
	 */
	public void creerMission(Mission m) throws Exception{
		if(!m_listM.existeMission(m)){
			m_missionEnCours=m;
			m_listM.addMissions(m_missionEnCours);
			saveMissions(m_c);
		}
		else{
			throw new Exception("demande non-r�alisable : nom d�j� existant");
		}
	}
	
	/**
	 * Change la mission en cours par une d�j� pr�sente dans la base
	 * @param m : la nouvelle mission en cours
	 * @throws Exception  : si la mission en param�tre n'existe pas dans la base
	 */
	public void changerMissionEnCours(Mission m) throws Exception{
		if (m_listM.existeMission(m)){
			m_missionEnCours= m_listM.getMissions(m.getNom());
		}
		else{
			throw new Exception("demande non-r�alisable : cette mission n'existe pas");
		}
	}
	
	/**
	 * Supprime une mission de la base de donn�es
	 * @param m : la mission � supprimer
	 * @throws Exception : si la mission en param�tre n'exise pas dans la base
	 */
	public void supprimerMission(Mission m) throws Exception{
		if(m_listM.existeMission(m)){
			m_listM.removeMissions(m);
			saveMissions(m_c);
		}
		else{
			throw new Exception("demande non-r�alisable : cette mission n'est pas pr�sente dans la base");
		}
	}
	
	/**
	 * supprime toute les missions de la liste
	 */
	public void supprimerToutesLesMissions(){
		m_listM.viderListe();
		saveMissions(m_c);
	}
	
	/**
	 * v�rifie si la mission n'existe pas d�j� dans la base
	 * @param m : la mission � tester
	 * @return : si le nom de cette mission est d�j� dans la base ou non.
	 */
	public boolean existeMission(Mission m){
		return m_listM.existeMission(m);
	}

	/**----------> obtenir les informations de la mission en cours de cr�ation/modification <----------**/
	
	/**
	 * @return le nom de la mission en cours
	 */
	public String getNomMissionEnCours(){
		return m_missionEnCours.getNom();
	}
	
	/**
	 * @return l'Email de la mission en cours
	 */
	public String getEMailMissionEnCours(){
		return m_missionEnCours.getEmail();
	}
	
	/**
	 * @return si la mission doit retourner au point de d�part ou non
	 */
	public boolean getRetourDepartMissionEnCours(){
		return m_missionEnCours.getRetourDepart();
	}
	
	/**
	 * @return si la mission doit prendre des photos � l'arriv�e ou non
	 */
	public boolean getPrendrePhotosArriveeMissionEnCours(){
		return m_missionEnCours.getPrendrePhotosArrivee();
	}
	
	/**
	 * @return le point d'arriv�e sous forme d'un tableau de deux entiers: en case 0 la lattitude en case 1 la longitude
	 */
	public int[] getPointArriveeMissionEnCours(){
		return m_missionEnCours.getM_fin();
	}
	
	/**----------> modifier les informations de la mission en cours de cr�ation/modification <----------**/
	
	/**
	 * Change le nom de la mission en cours
	 * @param n : le nouveau nom de la mission
	 * @throws Exception si le nouveau nom existe d�j� dans la base
	 */
	public void setNomMissionEnCours(String n) throws Exception{
		if(m_listM.getMissions(n)==null){
			m_listM.removeMissions(m_missionEnCours);
			m_missionEnCours.setNom(n);
			m_listM.addMissions(m_missionEnCours);
			saveMissions(m_c);
		}
		else throw new Exception("demande non-r�alisable : ce nom de mission est d�j� utilis�!");
	}
	
	/**
	 * Change l'Email de la mission en cours
	 * @param mail : le nouveau mail
	 */
	public void setEMailMissionEnCours(String mail){
		m_listM.removeMissions(m_missionEnCours);
		m_missionEnCours.setEmail(mail);
		m_listM.addMissions(m_missionEnCours);
		saveMissions(m_c);
		
	}
	
	/**
	 * Change le retour au d�part de la mission	
	 * @param b : si la mission doit retourner � son point de d�part � la fin ou non
	 */
	public void setRetourDepartMissionEnCours(boolean b){
		m_listM.removeMissions(m_missionEnCours);
		m_missionEnCours.setRetourDepart(b);
		m_listM.addMissions(m_missionEnCours);
		saveMissions(m_c);
		
	}
	
	/**
	 * Change la prise de photos � l'arriv�e de la mission
	 * @param b : nous dit si il y aura des photos pour cette mission ou non
	 */
	public void setPrendrePhotosArriveeMissionEnCours(boolean b){
		m_listM.removeMissions(m_missionEnCours);
		m_missionEnCours.setPrendrePhotosArrivee(b);
		m_listM.addMissions(m_missionEnCours);
		saveMissions(m_c);
	}
	
	
	/**
	 * change le point d'arriv�e de la missions en cours
	 * @param arrivee : le nouveau point d'arriv�e sous forme d'un tableau de deux entiers: en case 0 la lattitude en case 1 la longitude
	 * @throws Exception  si le tableau de coordonn�es n'a pas uniquement 2 cases
	 */
	public void setPointArriveeMissionsEnCours(int[] arrivee) throws Exception{
		m_listM.removeMissions(m_missionEnCours);
		m_missionEnCours.setM_fin(arrivee);
		m_listM.addMissions(m_missionEnCours);
		saveMissions(m_c);
	}
	
	/**----------> Serialization <----------**/
	
	/**
	 * sauvegarde la liste des missions du client
	 * @param c : contexte
	 */
	public void saveMissions(Context c){
        Log.v("[SERIAL]", "Saving..."); 
        try{ 
            FileOutputStream fos = c.openFileOutput("ClientMissions", Context.MODE_PRIVATE); 
            ObjectOutputStream os = new ObjectOutputStream(fos); 
            os.writeObject(m_listM); 
            os.close(); 
            fos.close(); 
        } catch (Exception e){ 
            e.printStackTrace(); 
        } 
	}
    
	/**
	 * charge la liste des missions du client
	 * @param c : contexte
	 * @return la liste charg�e
	 */
    public ListeMissions loadMission(Context c){ 
        Log.v("[SERIAL]", "Loading..."); 
        try{ 
            FileInputStream fis = c.openFileInput("ClientMissions"); 
            ObjectInputStream is = new ObjectInputStream(fis); 
            ListeMissions liste = (ListeMissions) is.readObject(); 
            is.close(); 
            fis.close();
            return liste;
        } catch(Exception e){ 
        	return new ListeMissions();
        } 

    }
}
