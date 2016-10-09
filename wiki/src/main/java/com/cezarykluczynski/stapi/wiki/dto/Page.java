package com.cezarykluczynski.stapi.wiki.dto;

import lombok.Data;

import java.util.List;

@Data
public class Page {

	private Long pageId;

	private String title;

	private List<CategoryHeader> categories;

	private List<Template> templates;

}
