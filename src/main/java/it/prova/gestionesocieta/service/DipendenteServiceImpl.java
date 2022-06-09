package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	public Dipendente caricaDipendentePiuAnzianoInSocietaFondatePrima1990() throws ParseException {
		return dipendenteRepository.findFirstBySocieta_DataFondazioneBeforeOrderByDataAssunzione(
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990")).orElse(null);
	}
}
