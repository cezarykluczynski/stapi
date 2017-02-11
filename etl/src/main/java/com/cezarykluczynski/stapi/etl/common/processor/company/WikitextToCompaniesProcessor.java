package com.cezarykluczynski.stapi.etl.common.processor.company;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WikitextToCompaniesProcessor implements ItemProcessor<String, Set<Company>> {

	private WikitextApi wikitextApi;

	private CompanyRepository companyRepository;

	@Inject
	public WikitextToCompaniesProcessor(WikitextApi wikitextApi, CompanyRepository companyRepository) {
		this.wikitextApi = wikitextApi;
		this.companyRepository = companyRepository;
	}

	@Override
	public Set<Company> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> companyRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
