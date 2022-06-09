package it.prova.gestionesocieta.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		// ###################### inizio caricamento dati per il test
		// #####################################
		// 1 società con 2 dipendenti con +3000
		// 1 società con 1 dipendenti con +3000
		// 1 società con 2 dipendenti con -3000

		Long longDate = new Date().getTime();
		Societa societaDaTrovare = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate, new Date());
		if (societaDaTrovare.getId() != null)
			throw new RuntimeException("testListaDistintaSocietaConRALDipendenteMaggiore3000...failed: transient object con id valorizzato");

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

		// 1 società con 2 dipendenti con +3000
		// 1 società con 1 dipendenti con +3000
		// 1 società con 2 dipendenti con -3000
		// ###################### fine caricamento dati per il test
		// ################################

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
		
		System.out.println("testListaDistintaSocietaConRALDipendenteMaggiore3000........OK\n");
	}

	public void caricaDipendentePiuAnzianoInSocietaFondataPrima1990() throws ParseException {
		// ###################### inizio caricamento dati per il test #####################################
		// società < 1980 con dipendente del 2000
		// società < 1980 con dipendente del 2003
		// società > 1980 con dipendente del 1999

		Long longDate = new Date().getTime();
		Societa societaDel1980 = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1980"));
		if (societaDel1980.getId() != null)
			throw new RuntimeException("testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societaDel1980);
		if (societaDel1980.getId() == null || societaDel1980.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		Dipendente dipendenteDaTrovare = new Dipendente("Antonietto" + longDate, "Cognome" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"), longDate.intValue());
		dipendenteDaTrovare.setSocieta(societaDel1980);
		if (dipendenteDaTrovare.getId() != null)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteDaTrovare);
		if (dipendenteDaTrovare.getId() == null || dipendenteDaTrovare.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		Societa societaDel1986 = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1986"));
		if (societaDel1986.getId() != null)
			throw new RuntimeException("testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societaDel1986);
		if (societaDel1986.getId() == null || societaDel1986.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		Dipendente dipendenteNonTrovare = new Dipendente("Nome" + longDate, "Cognome" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2003"), 3400);
		dipendenteNonTrovare.setSocieta(societaDel1986);
		if (dipendenteNonTrovare.getId() != null)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendenteNonTrovare);
		if (dipendenteNonTrovare.getId() == null || dipendenteNonTrovare.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		Societa societaDel1991 = new Societa("Ragione Sociale" + longDate, "Indirizzo" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1991"));
		if (societaDel1991.getId() != null)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		societaService.inserisciNuovo(societaDel1991);
		if (societaDel1991.getId() == null || societaDel1991.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		Dipendente dipendentePiuVecchioMaDiSocietaGiovane = new Dipendente("Nome" + longDate, "Cognome" + longDate,
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1999"), 3500);
		dipendentePiuVecchioMaDiSocietaGiovane.setSocieta(societaDel1991);
		if (dipendentePiuVecchioMaDiSocietaGiovane.getId() != null)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: transient object con id valorizzato");

		dipendenteService.inserisciNuovo(dipendentePiuVecchioMaDiSocietaGiovane);
		if (dipendentePiuVecchioMaDiSocietaGiovane.getId() == null
				|| dipendentePiuVecchioMaDiSocietaGiovane.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: inserimento fallito");

		// società < 1980 con dipendente del 2000
		// società < 1980 con dipendente del 2003
		// società > 1980 con dipendente del 1999
		// ###################### fine caricamento dati per il test #####################################

		Dipendente result = dipendenteService.caricaDipendentePiuAnzianoInSocietaFondatePrima1990();
		if (result == null || result.getId() == null || result.getId() < 1)
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: elemento non trovato fallito");

		if (!result.getNome().contains("Antonietto") || !result.getSocieta().getDataFondazione()
				.before(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990"))) {
			throw new RuntimeException(
					"testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990...failed: elemento non combacia con quello aspettato fallito");

		}
		
		System.out.println("testCaricaDipendentePiuAnzianoInSocietaFondataPrima1990........OK\n");
	}
}
