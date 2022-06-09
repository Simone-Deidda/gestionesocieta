package it.prova.gestionesocieta.service;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	public void inserisciNuovo(Dipendente dipendenteInstance);

	public void aggiorna(Dipendente dipendenteInstance);
	
	public Dipendente caricaSingoloDipendente(Long id);

}
