package com.cezarykluczynski.stapi.model.page.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Entity
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = PageRepository.class, apiEntity = false, singularName = "page", pluralName = "pages")
public class Page {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_sequence_generator")
	@SequenceGenerator(name = "page_sequence_generator", sequenceName = "page_sequence", allocationSize = 1)
	private Long id;

	private Long pageId;

	@Enumerated(EnumType.STRING)
	private MediaWikiSource mediaWikiSource;

	private String title;

}
