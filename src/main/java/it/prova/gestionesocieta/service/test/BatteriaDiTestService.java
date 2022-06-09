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
		Long longDate = new Date().getTime();
		Societa societaDaTrovare = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societaDaTrovare.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societaDaTrovare);
		if (societaDaTrovare.getId() == null || societaDaTrovare.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Dipendente dipendenteRAL3000 = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(), 3500);
		dipendenteRAL3000.setSocieta(societaDaTrovare);
		if (dipendenteRAL3000.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteRAL3000);
		if (dipendenteRAL3000.getId() == null || dipendenteRAL3000.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Dipendente dipendenteRAL3000StessaSocieta = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(),
				3400);
		dipendenteRAL3000StessaSocieta.setSocieta(societaDaTrovare);
		if (dipendenteRAL3000StessaSocieta.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteRAL3000StessaSocieta);
		if (dipendenteRAL3000StessaSocieta.getId() == null || dipendenteRAL3000StessaSocieta.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Societa altraSocietaDaTrovare = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (altraSocietaDaTrovare.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(altraSocietaDaTrovare);
		if (altraSocietaDaTrovare.getId() == null || altraSocietaDaTrovare.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Dipendente dipendenteRAL3000AltraSocieta = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(),
				3500);
		dipendenteRAL3000AltraSocieta.setSocieta(altraSocietaDaTrovare);
		if (dipendenteRAL3000AltraSocieta.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteRAL3000AltraSocieta);
		if (dipendenteRAL3000AltraSocieta.getId() == null || dipendenteRAL3000AltraSocieta.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Societa societaNonDaTrovare = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societaNonDaTrovare.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societaNonDaTrovare);
		if (societaNonDaTrovare.getId() == null || societaNonDaTrovare.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		Dipendente dipendenteRALNot3000 = new Dipendente("Nome" + longDate, "Cognome" + longDate, new Date(), 2500);
		dipendenteRALNot3000.setSocieta(societaNonDaTrovare);
		if (dipendenteRALNot3000.getId() != null)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteRALNot3000);
		if (dipendenteRALNot3000.getId() == null || dipendenteRALNot3000.getId() < 1)
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: inserimento fallito");

		List<Societa> listaSocietaRAL3000 = societaService.listAllSocietaWhereDipendenteRALMaggiore3000();
		if (listaSocietaRAL3000.size() < 2) {
			throw new RuntimeException(
					"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: il numero di dipendenti trovati non è quello che aspettavo");
		}
		
		for (Societa societa : listaSocietaRAL3000) {
			// prendo i dipendenti di ogni società trovata
			societa.getDipendenti().forEach(dip -> {
				// e controllo che ognuno di essi non abbia un RAL minore di 3000 
				if (dip.getReditoAnnuoLordo() < 3000) {
					throw new RuntimeException(
							"testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: almeno un dipendente trovato ha meno di 3000 RAL");
				}
			});
		}
	}

	public void caricaDipendentePiuAnzianoInSocietaFondataPrima1990() {

	}
}
