package com.cezarykluczynski.stapi.etl.template.publishable.dto;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class PublishableTemplate {

	private String title;

	private Page page;

	private Integer publishedYear;

	private Integer publishedMonth;

	private Integer publishedDay;

	private Integer coverYear;

	private Integer coverMonth;

	private Integer coverDay;

}
