package com.cezarykluczynski.stapi.model.material.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository;
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
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = MaterialRepository.class, singularName = "material", pluralName = "materials")
public class Material extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_sequence_generator")
	@SequenceGenerator(name = "material_sequence_generator", sequenceName = "material_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean chemicalCompound;

	private Boolean biochemicalCompound;

	private Boolean drug;

	private Boolean poisonousSubstance;

	private Boolean explosive;

	private Boolean gemstone;

	private Boolean alloyOrComposite;

	private Boolean fuel;

	private Boolean mineral;

	private Boolean preciousMaterial;

}
