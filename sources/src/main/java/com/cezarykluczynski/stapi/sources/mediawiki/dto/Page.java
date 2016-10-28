package com.cezarykluczynski.stapi.sources.mediawiki.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Page {

	private Long pageId;

	private String title;

	private String wikitext;

	private List<CategoryHeader> categories;

	private List<Template> templates;

	private List<PageHeader> redirectPath = Lists.newArrayList();

}
