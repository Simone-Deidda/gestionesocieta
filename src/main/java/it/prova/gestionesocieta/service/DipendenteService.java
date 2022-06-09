package it.prova.gestionesocieta.service;

import java.text.ParseException;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	public void inserisciNuovo(Dipendente dipendenteInstance);

	public void aggiorna(Dipendente dipendenteInstance);
	
	public Dipendente caricaSingoloDipendente(Long id);

	public Dipendente caricaDipendentePiuAnzianoInSocietaFondatePrima1990() throws ParseException;
}
