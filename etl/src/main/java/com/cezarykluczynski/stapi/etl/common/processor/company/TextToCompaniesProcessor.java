package com.cezarykluczynski.stapi.etl.common.processor.company;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.CompanyAliasFixedValueProvider;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.google.common.collect.Sets;
import liquibase.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TextToCompaniesProcessor implements ItemProcessor<String, Set<Company>> {

	private static final Set<String> INVALID_NAMES = Sets.newHashSet("Unknown", "Various");
	private static final String SPACE = " ";

	private final CompanyRepository companyRepository;

	private final CompanyAliasFixedValueProvider companyAliasFixedValueProvider;

	public TextToCompaniesProcessor(CompanyRepository companyRepository, CompanyAliasFixedValueProvider companyAliasFixedValueProvider) {
		this.companyRepository = companyRepository;
		this.companyAliasFixedValueProvider = companyAliasFixedValueProvider;
	}

	@Override
	public Set<Company> process(String item) throws Exception {
		Set<Company> companySet = Sets.newHashSet();

		if (INVALID_NAMES.contains(item)) {
			return companySet;
		}

		List<String> companiesCandidates = StringUtils.splitAndTrim(item, ",");
		companiesCandidates.forEach(companyCandidate -> tryAddCompanyFromCandidate(companySet, companyCandidate, 0));
		return companySet;
	}

	private void tryAddCompanyFromCandidate(Set<Company> companySet, String companyCandidate, int depth) {
		String internalCompanyCandidate = companyCandidate;
		FixedValueHolder<String> companyCanonicalNameFixedValueProvider = companyAliasFixedValueProvider.getSearchedValue(internalCompanyCandidate);
		boolean fixedValueWasFound = companyCanonicalNameFixedValueProvider.isFound();

		if (fixedValueWasFound) {
			internalCompanyCandidate = companyCanonicalNameFixedValueProvider.getValue();
		}

		Optional<Company> companyOptional = companyRepository.findByName(internalCompanyCandidate);

		if (companyOptional.isPresent()) {
			companySet.add(companyOptional.get());
		} else if (!fixedValueWasFound) {
			int sizeBefore = companySet.size();
			if (depth == 0) {
				boolean hasSpace = org.apache.commons.lang3.StringUtils.contains(internalCompanyCandidate, SPACE);
				if (hasSpace) {
					List<String> companyCandidates = StringUtils.splitAndTrim(internalCompanyCandidate, SPACE);
					companyCandidates.forEach(companyCandidateSingleWord -> tryAddCompanyFromCandidate(companySet, companyCandidateSingleWord, 1));
				}

				if (!hasSpace || sizeBefore == companySet.size()) {
					log.info("Could not find company named \"{}\"", internalCompanyCandidate);
				}
			}
		}
	}

}
