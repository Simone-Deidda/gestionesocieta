package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.CannotDeleteSocietaWithDipendentiException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {
	@Autowired
	private SocietaRepository societaRepository;

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional(readOnly = true)
	public List<Societa> findByExample(Societa example) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING);
		// Match string containing pattern
		// .withIgnoreCase();
		return (List<Societa>) societaRepository.findAll(Example.of(example, matcher));
	}

	@Transactional
	public void rimuovi(Societa societaInstance) {
		if (societaInstance.getDipendenti() != null && !societaInstance.getDipendenti().isEmpty()) {
			throw new CannotDeleteSocietaWithDipendentiException("Non puoi eliminare una Società avente Dipendenti.");
		}
		societaRepository.delete(societaInstance);
	}

	@Override
	public List<Societa> listAllSocietaWhereDipendenteRALMaggiore3000() {
		return societaRepository.findAllDistinctByDipendenti_ReditoAnnuoLordoGreaterThan(3000);
	}

}
