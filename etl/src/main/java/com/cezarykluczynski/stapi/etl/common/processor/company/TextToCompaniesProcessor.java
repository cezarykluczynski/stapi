package com.cezarykluczynski.stapi.etl.common.processor.company;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TextToCompaniesProcessor implements ItemProcessor<String, Set<Company>> {

	@Override
	public Set<Company> process(String item) throws Exception {
		Set<Company> companySet = Sets.newHashSet();
		// TODO
		return companySet;
	}

}
