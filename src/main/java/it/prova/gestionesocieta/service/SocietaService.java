package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.util.List;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {
	public void inserisciNuovo(Societa societaInstance);

	public List<Societa> findByExample(Societa example);

	public void rimuovi(Societa societaInstance);

	List<Societa> listAllSocietaWhereDipendenteRALMaggiore3000();
}
