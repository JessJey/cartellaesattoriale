package it.prova.cartellaesattoriale.repository.cartellaesattoriale;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;

public interface CartellaEsattorialeRepository extends CrudRepository<CartellaEsattoriale, Long>, CustomCartellaEsattorialeRepository {

	@Query("from Film f join fetch f.regista where f.id = ?1")
	CartellaEsattoriale findSingleCartellaEsattorialeEager(Long id);
	
	@Query("select e from CartellaEsattoriale e join fetch e.contribuente")
	List<CartellaEsattoriale> findAllCartellaEsattorialeEager();
	
}
