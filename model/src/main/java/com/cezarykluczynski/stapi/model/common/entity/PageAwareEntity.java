package com.cezarykluczynski.stapi.model.common.entity;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = "page")
@ToString
public class PageAwareEntity {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "page_id")
	private Page page;

	@Column(length = 14)
	private String guid;

}
