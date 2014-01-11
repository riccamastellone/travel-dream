package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondaria;
import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.manager.AttivitaMng;

@Stateless
public class AttivitaMngBean implements AttivitaMng {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;


	private AttivitaSecondariaDTO AttivitaToDTO(AttivitaSecondaria a) {
		AttivitaSecondariaDTO as = new AttivitaSecondariaDTO();
		as.setNome(a.getNome());
		as.setCosto(a.getCosto());
		as.setDescrizione(a.getDescrizione());
		as.setDisponibilita(a.getDisponibilita());
		as.setId(a.getId());
		as.setLocalita(a.getLocalita());
		return as;
	}

	public void salvaAttivita(AttivitaSecondariaDTO attivita) {
		AttivitaSecondaria attivitaNew = new AttivitaSecondaria(attivita);
		em.persist(attivitaNew);
	}

	public ArrayList<AttivitaSecondariaDTO> getAttivita() {
		List<AttivitaSecondaria> myList;
		ArrayList<AttivitaSecondariaDTO> attivitaDTO = new ArrayList<AttivitaSecondariaDTO>();
		myList = em.createNamedQuery("AttivitaSecondaria.findAll", AttivitaSecondaria.class)
				.getResultList();
		for (AttivitaSecondaria h : myList) {
			attivitaDTO.add(this.AttivitaToDTO(h));
		}
		return attivitaDTO;
	}

	private AttivitaSecondaria findAttivita(int id) {
		return em.find(AttivitaSecondaria.class, id);
	}

	public void aggiornaAttivita(AttivitaSecondariaDTO attivita)  {
		AttivitaSecondaria as = this.findAttivita(attivita.getId());
		as.setNome(attivita.getNome());
		as.setLocalita(attivita.getLocalita());
		as.setDisponibilita(attivita.getDisponibilita());
		as.setCosto(attivita.getCosto());
		as.setDescrizione(attivita.getDescrizione());

		em.merge(as);

	}

	public void deleteAttivita(AttivitaSecondariaDTO attivita) {
		AttivitaSecondaria attivitaDC = this.findAttivita(attivita.getId());
		attivitaDC.setEliminato(1);
		em.merge(attivitaDC);
	}

}