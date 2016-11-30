package com.cezarykluczynski.stapi.sources.mediawiki.dto;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Page {

	private Long pageId;

	private String title;

	private MediaWikiSource mediaWikiSource;

	private String wikitext;

	private List<CategoryHeader> categories = Lists.newArrayList();

	private List<Template> templates = Lists.newArrayList();

	private List<PageHeader> redirectPath = Lists.newArrayList();

	private List<PageSection> sections = Lists.newArrayList();

}
