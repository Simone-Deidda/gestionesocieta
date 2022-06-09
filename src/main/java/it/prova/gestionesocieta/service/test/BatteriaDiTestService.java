package it.prova.gestionesocieta.service.test;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.CannotDeleteSocietaWithDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.service.DipendenteService;
import it.prova.gestionesocieta.service.SocietaService;

@Service
public class BatteriaDiTestService {
	@Autowired
	private DipendenteService dipendenteService;
	@Autowired
	private SocietaService societaService;

	public void inserisciSocieta() {
		Long longDate = new Date().getTime();
		Societa societa = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societa.getId() != null)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societa);
		if (societa.getId() == null || societa.getId() < 1)
			throw new RuntimeException("testInserisciNuovoDipendente...failed: inserimento fallito");

		System.out.println("testInserisciNuovoDipendente........OK\n");
	}

	public void findSocietaByExample() {
		Long longDate = new Date().getTime();
		Societa societa = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societa.getId() != null)
			throw new RuntimeException("testFindByExample...failed: transient object con id valorizzato");
		societaService.inserisciNuovo(societa);
		if (societa.getId() == null || societa.getId() < 1)
			throw new RuntimeException("testFindByExample...failed: inserimento fallito");

		Societa societa2 = new Societa("Ragione Sociale" + longDate * 2, "Indirizzo" + longDate * 2, new Date());
		if (societa2.getId() != null)
			throw new RuntimeException("testFindByExample...failed: transient object con id valorizzato");
		societaService.inserisciNuovo(societa2);
		if (societa2.getId() == null || societa2.getId() < 1)
			throw new RuntimeException("testFindByExample...failed: inserimento fallito");

		Societa example = new Societa("Ragione Sociale", "Indirizzo", null);
		List<Societa> listaSocieta = societaService.findByExample(example);
		if (listaSocieta.size() < 2) {
			throw new RuntimeException("testFindByExample...failed: ricerca fallita");
		}

		System.out.println("testFindByExample........OK\n");
	}

	public void rimuoviSocieta() {
		Long longDate = new Date().getTime();
		Societa societa = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societa.getId() != null)
			throw new RuntimeException("testRimuoviSocieta...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societa);
		if (societa.getId() == null || societa.getId() < 1)
			throw new RuntimeException("testRimuoviSocieta...failed: inserimento fallito");

		Societa societa2 = new Societa("Ragione Sociale" + longDate * 2, "Indirizzo" + longDate * 2, new Date());
		if (societa2.getId() != null)
			throw new RuntimeException("testRimuoviSocieta...failed: transient object con id valorizzato");
		societaService.inserisciNuovo(societa2);
		if (societa2.getId() == null || societa2.getId() < 1)
			throw new RuntimeException("testRimuoviSocieta...failed: inserimento fallito");
		societa2.getDipendenti().add(new Dipendente());

		societaService.rimuovi(societa);

		try {
			societaService.rimuovi(societa2);
			throw new RuntimeException("testRimuoviSocieta...failed: l'eliminazione non è stata impedita.");
		} catch (CannotDeleteSocietaWithDipendentiException e) {
			// mi aspetto che l'eccezione venga lanciata
		}

		System.out.println("testRimuoviSocieta........OK\n");
	}

	public void inserisciDipendente() {
		Long longDate = new Date().getTime();
		Societa societa = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societa.getId() != null)
			throw new RuntimeException("testInserisciDipendente...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societa);
		if (societa.getId() == null || societa.getId() < 1)
			throw new RuntimeException("testInserisciDipendente...failed: inserimento fallito");

		Dipendente dipendente = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(),
				longDate.intValue());
		dipendente.setSocieta(societa);
		if (dipendente.getId() != null)
			throw new RuntimeException("testInserisciDipendente...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendente);
		if (dipendente.getId() == null || dipendente.getId() < 1)
			throw new RuntimeException("testInserisciDipendente...failed: inserimento fallito");

		System.out.println("testInserisciDipendente........OK\n");
	}

	public void modificaDipendente() {
		Long longDate = new Date().getTime();
		Societa societa = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societa.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societa);
		if (societa.getId() == null || societa.getId() < 1)
			throw new RuntimeException("testModificaDipendente...failed: inserimento fallito");

		Dipendente dipendente = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(),
				longDate.intValue());
		dipendente.setSocieta(societa);
		if (dipendente.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendente);
		if (dipendente.getId() == null || dipendente.getId() < 1)
			throw new RuntimeException("testModificaDipendente...failed: inserimento fallito");

		String nuovoNome = "Nuovo Nome" + longDate;
		String nuovoCognome = "Nuovo Cognome" + longDate;
		dipendente.setNome(nuovoNome);
		dipendente.setCognome(nuovoCognome);

		dipendenteService.aggiorna(dipendente);
		Dipendente dipendenteDB = dipendenteService.caricaSingoloDipendente(dipendente.getId());
		if (dipendenteDB.getId() != dipendente.getId()) {
			throw new RuntimeException("testModificaDipendente...failed: caricamento singolo elemenato fallito");
		}
		if (!dipendenteDB.getNome().equals(nuovoNome) || !dipendenteDB.getCognome().equals(nuovoCognome)) {
			throw new RuntimeException("testModificaDipendente...failed: aggiornamento fallito");
		}

		System.out.println("testModificaDipendente........OK\n");
	}

	public void listaDistintaSocietaConRALDipendenteMaggiore3000() {

	}

	public void caricaDipendentePiuAnzianoInSocietaFondataPrima1990() {

	}
}
