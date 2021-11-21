package it.prova.cartellaesattoriale.web.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import it.prova.cartellaesattoriale.dto.CartellaEsattorialeDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteAggiuntaDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteDaAttenzionareDTO;
import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.model.Stato;
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
		if (contribuenteInput.getId() != null) {
			throw new IdNotNullForInsertException("il campo ID deve essere null");
		}
		Contribuente contribuenteInserito = contribuenteService
				.inserisciNuovo(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteInserito, false);
	}

	@PutMapping("/{id}")
	public ContribuenteDTO update(@Valid @RequestBody ContribuenteDTO contribuenteInput,
			@PathVariable(required = true) Long id) {
		Contribuente contri = contribuenteService.caricaSingoloElemento(id);

		if (contri == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		contribuenteInput.setId(id);
		Contribuente contrAggiornato = contribuenteService.aggiorna(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contrAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		if (contribuente.getCartelleEsattoriali().size() != 0)
			throw new ContribuenteDeleteException("Il regista selezionato ha dei film associati");

		contribuenteService.rimuovi(contribuente);
	}

	@PostMapping("/search")
	public List<ContribuenteDTO> search(@RequestBody ContribuenteDTO example) {
		return ContribuenteDTO.createContribuenteDTOListFromModelList(
				contribuenteService.findByExample(example.buildContribuenteModel()), false);
	}

	@GetMapping("/verificaContenziosi")
	public List<ContribuenteDaAttenzionareDTO> verificaContenziosi() {

		List<ContribuenteDTO> listaCompleta = ContribuenteDTO
				.createContribuenteDTOListFromModelList(contribuenteService.listAllElementsEager(), false);
		List<ContribuenteDaAttenzionareDTO> contribuenteInContenzioso = ContribuenteDaAttenzionareDTO
				.createContribuenteDTOListFromModelList(contribuenteService.trovaPerStatoCartelle(Stato.IN_CONTENZIOSO),
						false);

		for (ContribuenteDaAttenzionareDTO inContenziosoItem : contribuenteInContenzioso) {
			inContenziosoItem.setDaAttenzionare(true);
		}

		for (int i = 0; i < contribuenteInContenzioso.size(); i++) {
			if (listaCompleta.get(i).getId() == contribuenteInContenzioso.get(i).getId()) {
				listaCompleta.remove(i);
			}
		}

		List<ContribuenteDaAttenzionareDTO> newList = (List<ContribuenteDaAttenzionareDTO>) Stream
				.of(listaCompleta, contribuenteInContenzioso).flatMap(Collection::stream).collect(Collectors.toList());

		return newList;
	}
	
	@GetMapping("/reportContribuenti")
	public List<ContribuenteAggiuntaDTO> reportContribuenti() {
		List<ContribuenteAggiuntaDTO> contribuenti = ContribuenteAggiuntaDTO.createContribuenteDTOListFromModelList(contribuenteService.listAllElementsEager(), true);
		
		
		for (ContribuenteAggiuntaDTO contribuenteItem : contribuenti) {
			int importoCartelle = 0;
			int conclusoEPagato = 0;
			int inContenzioso = 0;
			for (CartellaEsattorialeDTO cartellaItem : contribuenteItem.getCartelleEsattoriali()) {
				importoCartelle += cartellaItem.getImporto();
				if(cartellaItem.getStato().equals(Stato.CONCLUSA))
					conclusoEPagato += cartellaItem.getImporto();
				if(cartellaItem.getStato().equals(Stato.IN_CONTENZIOSO))
					inContenzioso += cartellaItem.getImporto();
			}
			contribuenteItem.setInContenzioso(inContenzioso);
			contribuenteItem.setConclusoEPagato(conclusoEPagato);
			contribuenteItem.setImportoCartelle(importoCartelle);
		}
		return contribuenti;
	}
}
