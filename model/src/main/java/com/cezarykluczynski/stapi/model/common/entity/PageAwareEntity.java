package com.cezarykluczynski.stapi.model.common.entity;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = "page")
@ToString
public class PageAwareEntity {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id")
	private Page page;

	@Column(length = 14, name = "u_id")
	private String uid;

}
