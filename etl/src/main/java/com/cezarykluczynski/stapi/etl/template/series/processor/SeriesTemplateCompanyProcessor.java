package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SeriesTemplateCompanyProcessor implements ItemProcessor<Template.Part, Company> {

	private final WikitextApi wikitextApi;

	private final PageApi pageApi;

	private final CompanyRepository companyRepository;

	public SeriesTemplateCompanyProcessor(WikitextApi wikitextApi, PageApi pageApi, CompanyRepository companyRepository) {
		this.wikitextApi = wikitextApi;
		this.pageApi = pageApi;
		this.companyRepository = companyRepository;
	}

	@Override
	public Company process(Template.Part item) throws Exception {
		String pageLinkTitle = getCompanyNameFromWikitext(item.getValue(), item.getTemplates());

		if (pageLinkTitle == null) {
			final Optional<PageLink> pageLink = item.getTemplates().stream()
					.flatMap(template -> template.getParts().stream())
					.flatMap(part -> wikitextApi.getPageLinksFromWikitext(part.getValue()).stream())
					.findFirst();
			if (pageLink.isPresent()) {
				pageLinkTitle = pageLink.get().getTitle();
			} else {
				List<PageLink> pageLinksFromWikitext = wikitextApi.getPageLinksFromWikitext(item.getContent());
				if (!pageLinksFromWikitext.isEmpty()) {
					pageLinkTitle = pageLinksFromWikitext.get(0).getTitle();
				} else {
					log.info("No company can be found in template part \"{}\"", item);
					return null;
				}
			}
		}

		Optional<Company> companyOptional = companyRepository.findByPageTitleWithPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN);
		if (!companyOptional.isPresent()) {
			final Page page = pageApi.getPage(pageLinkTitle, com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN);
			if (page != null && !page.getTitle().equals(pageLinkTitle)) {
				companyOptional = companyRepository.findByPageTitleWithPageMediaWikiSource(page.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
				if (!companyOptional.isPresent()) {
					log.info("No company found for page link title {} nor redirected page {}.", pageLinkTitle, page.getTitle());
					return null;
				}
			} else {
				log.info("No company found for page link title {}.", pageLinkTitle);
				return null;
			}
		}
		return companyOptional.get();
	}

	private String getCompanyNameFromWikitext(String wikitext, List<Template> templateList) {
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(wikitext);
		if (!pageLinkList.isEmpty()) {
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
