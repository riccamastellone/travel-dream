package traveldream.dipendente.web;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;

import com.sun.el.parser.ParseException;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.manager.AttivitaMng;;

@ManagedBean(name = "attivitaBean")
@SessionScoped
public class AttivitaBean implements Serializable {

	private static final long serialVersionUID = -259217230585957009L;

	@EJB
	private AttivitaMng attivitaMng;

	private AttivitaSecondariaDTO attivita;

	private ArrayList<AttivitaSecondariaDTO> allAttivita;

	public AttivitaBean() {
		this.setAttivita(new AttivitaSecondariaDTO());
	}

	public String aggiungiAttivita() throws ParseException {
		attivitaMng.salvaAttivita(attivita);
		refreshAttivita();
		return "catalogo?faces-redirect=true";

	}
	
	private void refreshAttivita() {
		this.allAttivita = attivitaMng.getAttivita();
	}


	public ArrayList<AttivitaSecondariaDTO> getAllAttivita() {
		if (this.allAttivita == null) {
			refreshAttivita();
		}
		return this.allAttivita;
	}
	

	public void deleteAttivita(AttivitaSecondariaDTO attivita) {
		attivitaMng.deleteAttivita(attivita);
		this.allAttivita.remove(attivita);
	}


	public AttivitaSecondariaDTO getAttivita() {
		return attivita;
	}


	public void setAttivita(AttivitaSecondariaDTO attivita) {
		this.attivita = attivita;
	}
	
	public void onEdit(RowEditEvent event) throws ParseException { 
	       FacesMessage msg = new FacesMessage("Attivita Aggiornato");  
	       attivitaMng.aggiornaAttivita((AttivitaSecondariaDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 
	
	public String goToGestioneAttivita(){
		refreshAttivita();
		return "/dipendente/gestioneAttivita/catalogo?faces-redirect=true";
	}

}
