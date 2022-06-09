package it.prova.gestionesocieta.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>, QueryByExampleExecutor<Dipendente> {
	@EntityGraph(attributePaths = {"societa"})
	Optional<Dipendente> findFirstBySocieta_DataFondazioneBeforeOrderByDataAssunzione(Date dataFondazione);
}
