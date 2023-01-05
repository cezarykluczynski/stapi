package com.cezarykluczynski.stapi.etl.company.creation.processor;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyWriter implements ItemWriter<Company> {

	private final CompanyRepository companyRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public CompanyWriter(CompanyRepository companyRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.companyRepository = companyRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(Chunk<? extends Company> items) throws Exception {
		companyRepository.saveAll(process(items));
	}

	private List<Company> process(Chunk<? extends Company> companyList) {
		List<Company> companyListWithoutExtends = fromExtendsListToCompanyList(companyList);
		return filterDuplicates(companyListWithoutExtends);
	}

	private List<Company> fromExtendsListToCompanyList(Chunk<? extends Company> companyList) {
		return companyList
				.getItems()
				.stream()
				.map(pageAware -> (Company) pageAware)
				.collect(Collectors.toList());
	}

	private List<Company> filterDuplicates(List<Company> companyList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(companyList.stream()
				.map(company -> (PageAware) company)
				.collect(Collectors.toList()), Company.class).stream()
				.map(pageAware -> (Company) pageAware)
				.collect(Collectors.toList());
	}

}
