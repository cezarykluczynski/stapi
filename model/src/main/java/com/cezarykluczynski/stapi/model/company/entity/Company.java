package com.cezarykluczynski.stapi.model.company.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = CompanyRepository.class, singularName = "company", pluralName = "companies",
		restApiVersion = "v2")
public class Company extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_sequence_generator")
	@SequenceGenerator(name = "company_sequence_generator", sequenceName = "company_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean broadcaster;

	private Boolean streamingService;

	private Boolean collectibleCompany;

	private Boolean conglomerate;

	private Boolean visualEffectsCompany;

	private Boolean digitalVisualEffectsCompany;

	private Boolean distributor;

	private Boolean gameCompany;

	private Boolean filmEquipmentCompany;

	private Boolean makeUpEffectsStudio;

	private Boolean mattePaintingCompany;

	@Column(name = "model_and_miniature_company")
	private Boolean modelAndMiniatureEffectsCompany;

	private Boolean postProductionCompany;

	private Boolean productionCompany;

	private Boolean propCompany;

	private Boolean recordLabel;

	private Boolean specialEffectsCompany;

	private Boolean tvAndFilmProductionCompany;

	private Boolean videoGameCompany;

	private Boolean publisher;

	private Boolean publicationArtStudio;

}
