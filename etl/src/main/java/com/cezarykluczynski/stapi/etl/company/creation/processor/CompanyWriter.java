package com.cezarykluczynski.stapi.etl.company.creation.processor;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class CompanyWriter implements ItemWriter<Company> {

	private final CompanyRepository companyRepository;

	public CompanyWriter(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void write(Chunk<? extends Company> items) throws Exception {
		companyRepository.saveAll(items.getItems());
	}

}
