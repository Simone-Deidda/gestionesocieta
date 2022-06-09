package it.prova.gestionesocieta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {
	@Autowired
	private SocietaRepository societaRepository;

}
