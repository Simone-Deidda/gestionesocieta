package it.prova.gestionesocieta.service.test;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		Societa societa2 = new Societa("Ragione Sociale" + longDate*2, "Indirizzo" + longDate*2, new Date());
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
}
