package com.cezarykluczynski.stapi.model.animal.entity;

import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
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
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = AnimalRepository.class, singularName = "animal", pluralName = "animals")
public class Animal extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_sequence_generator")
	@SequenceGenerator(name = "animal_sequence_generator", sequenceName = "animal_sequence", allocationSize = 1)
	private Long id;

	private String name;

	private Boolean earthAnimal;

	private Boolean earthInsect;

	private Boolean avian;

	private Boolean canine;

	private Boolean feline;

}
