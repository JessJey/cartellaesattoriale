package it.prova.cartellaesattoriale.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.cartellaesattoriale.dto.ContribuenteDTO;
import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.service.contribuente.ContribuenteService;
import it.prova.cartellaesattoriale.web.api.exception.ContribuenteDeleteException;
import it.prova.cartellaesattoriale.web.api.exception.ContribuenteNotFoundException;
import it.prova.cartellaesattoriale.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/contribuente")
public class ContribuenteController {

	@Autowired
	private ContribuenteService contribuenteService;
	

	@GetMapping
	public List<ContribuenteDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return ContribuenteDTO.createContribuenteDTOListFromModelList(contribuenteService.listAllElementsEager(), true);
	}
	
	@GetMapping("/{id}")
	public ContribuenteDTO findById(@PathVariable(value = "id", required = true) long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElementoConCartelleEsattoriali(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuente, true);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContribuenteDTO createNew(@Valid @RequestBody ContribuenteDTO contribuenteInput) {
			if(contribuenteInput.getId() != null) {
				throw new IdNotNullForInsertException("il campo ID deve essere null");
			}
		Contribuente contribuenteInserito = contribuenteService.inserisciNuovo(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteInserito, false);
	}
	
	@PutMapping("/{id}")
	public ContribuenteDTO update(@Valid @RequestBody ContribuenteDTO contribuenteInput, @PathVariable(required = true) Long id) {
		Contribuente regista = contribuenteService.caricaSingoloElemento(id);

		if (regista == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		contribuenteInput.setId(id);
		Contribuente registaAggiornato = contribuenteService.aggiorna(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(registaAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);
		
		if(contribuente.getCartelleEsattoriali().size() != 0)
			throw new ContribuenteDeleteException("Il regista selezionato ha dei film associati");

		contribuenteService.rimuovi(contribuente);
	}

	@PostMapping("/search")
	public List<ContribuenteDTO> search(@RequestBody ContribuenteDTO example) {
		return ContribuenteDTO.createContribuenteDTOListFromModelList(contribuenteService.findByExample(example.buildContribuenteModel()),
				false);
	}
}
