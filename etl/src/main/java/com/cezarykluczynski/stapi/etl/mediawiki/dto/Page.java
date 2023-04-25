package com.cezarykluczynski.stapi.etl.mediawiki.dto;

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import lombok.Data;
import org.jsoup.nodes.Document;

import java.util.List;

@Data
public class Page {

	private Long pageId;

	private String title;

	private MediaWikiSource mediaWikiSource;

	private String wikitext;

	private Document htmlDocument;

	private List<CategoryHeader> categories = Lists.newArrayList();

	private List<Template> templates = Lists.newArrayList();

	private List<PageHeader> redirectPath = Lists.newArrayList();

	private List<PageSection> sections = Lists.newArrayList();

}
