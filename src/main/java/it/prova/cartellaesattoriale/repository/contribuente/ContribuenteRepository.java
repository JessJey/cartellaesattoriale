package it.prova.cartellaesattoriale.repository.contribuente;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.model.Stato;


public interface ContribuenteRepository extends CrudRepository<Contribuente, Long>, CustomContribuenteRepository {

	@Query("select distinct c from Contribuente c left join fetch c.cartelleEsattoriali ")
	List<Contribuente> findAllEager();
	
	@Query("from Contribuente c left join fetch c.cartelleEsattoriali where c.id=?1")
	Contribuente findByIdEager(Long idContribuente);
	
	List<Contribuente> findAllByCartelleEsattoriali_StatoLike(Stato stato);
}
