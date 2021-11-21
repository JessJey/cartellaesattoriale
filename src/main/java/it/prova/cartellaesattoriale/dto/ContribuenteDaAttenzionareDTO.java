package it.prova.cartellaesattoriale.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.cartellaesattoriale.model.Contribuente;

public class ContribuenteDaAttenzionareDTO {
	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotNull(message = "{datadinascita.notnull}")
	private Date dataDiNascita;

	@NotBlank(message = "{codicefiscale.notblank}")
	private String codicefiscale;

	@NotBlank(message = "{indirizzo.notblank}")
	private String indirizzo;

	@JsonIgnoreProperties(value = { "contribuente" })
	private Set<CartellaEsattorialeDTO> cartelleEsattoriali = new HashSet<CartellaEsattorialeDTO>(0);

	private boolean daAttenzionare;


	public ContribuenteDaAttenzionareDTO() {
		super();
	}

	public ContribuenteDaAttenzionareDTO(Long id, String nome, String cognome, Date dataDiNascita, String codicefiscale,
			String indirizzo, Set<CartellaEsattorialeDTO> cartelleEsattoriali) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codicefiscale = codicefiscale;
		this.indirizzo = indirizzo;
		this.cartelleEsattoriali = cartelleEsattoriali;
	}

	public ContribuenteDaAttenzionareDTO(String nome, String cognome, Date dataDiNascita, String codicefiscale,
			String indirizzo, Set<CartellaEsattorialeDTO> cartelleEsattoriali) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codicefiscale = codicefiscale;
		this.indirizzo = indirizzo;
		this.cartelleEsattoriali = cartelleEsattoriali;
	}

	public ContribuenteDaAttenzionareDTO(Long id, String nome, String cognome, Date dataDiNascita, String codicefiscale,
			String indirizzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codicefiscale = codicefiscale;
		this.indirizzo = indirizzo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getCodicefiscale() {
		return codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Set<CartellaEsattorialeDTO> getCartelleEsattoriali() {
		return cartelleEsattoriali;
	}

	public void setCartelleEsattoriali(Set<CartellaEsattorialeDTO> cartelleEsattoriali) {
		this.cartelleEsattoriali = cartelleEsattoriali;
	}

	public boolean isDaAttenzionare() {
		return daAttenzionare;
	}

	public void setDaAttenzionare(boolean daAttenzionare) {
		this.daAttenzionare = daAttenzionare;
	}

	

	public Contribuente buildContribuenteModel() {
		return new Contribuente(this.id, this.nome, this.cognome, this.dataDiNascita, this.codicefiscale,
				this.indirizzo);
	}

	public static ContribuenteDaAttenzionareDTO buildContribuenteDTOFromModel(Contribuente contribuenteModel,
			boolean includeCartelle) {
		ContribuenteDaAttenzionareDTO result = new ContribuenteDaAttenzionareDTO(contribuenteModel.getId(),
				contribuenteModel.getNome(), contribuenteModel.getCognome(), contribuenteModel.getDataDiNascita(),
				contribuenteModel.getCodicefiscale(), contribuenteModel.getIndirizzo());
		if (includeCartelle)
			result.setCartelleEsattoriali(CartellaEsattorialeDTO
					.createCartellaEsattorialeDTOSetFromModelSet(contribuenteModel.getCartelleEsattoriali(), false));
		return result;
	}

	public static List<ContribuenteDaAttenzionareDTO> createContribuenteDTOListFromModelList(
			List<Contribuente> modelListInput, boolean includeCartelle) {
		return modelListInput.stream().map(contribuenteEntity -> {
			ContribuenteDaAttenzionareDTO result = ContribuenteDaAttenzionareDTO.buildContribuenteDTOFromModel(contribuenteEntity,
					includeCartelle);
			if (includeCartelle)
				result.setCartelleEsattoriali(CartellaEsattorialeDTO.createCartellaEsattorialeDTOSetFromModelSet(
						contribuenteEntity.getCartelleEsattoriali(), false));
			return result;
		}).collect(Collectors.toList());
	}

}
