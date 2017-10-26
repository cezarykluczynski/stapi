package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SeriesTemplateCompanyProcessor implements ItemProcessor<Template.Part, Company> {

	private final WikitextApi wikitextApi;

	private final CompanyRepository companyRepository;

	public SeriesTemplateCompanyProcessor(WikitextApi wikitextApi, CompanyRepository companyRepository) {
		this.wikitextApi = wikitextApi;
		this.companyRepository = companyRepository;
	}

	@Override
	public Company process(Template.Part item) throws Exception {
		String pageLinkTitle = getCompanyNameFromWikitext(item.getValue(), item.getTemplates());

		if (pageLinkTitle == null) {
			log.debug("No company can be found in template part \"{}\"", item);
			return null;
		}

		return companyRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN)
				.orElse(null);
	}

	private String getCompanyNameFromWikitext(String wikitext, List<Template> templateList) {
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(wikitext);
		if (pageLinkList.size() == 1) {
			return pageLinkList.get(0).getTitle();
		}

		return templateList
				.stream()
				.filter(template -> TemplateTitle.DIS.equals(template.getTitle()))
				.map(wikitextApi::disTemplateToPageTitle)
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
	}

}
