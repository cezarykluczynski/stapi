package com.cezarykluczynski.stapi.etl.reference.processor;

import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.model.reference.factory.ReferenceFactory;
import com.cezarykluczynski.stapi.model.reference.repository.ReferenceRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReferencesFromTemplatePartProcessor implements ItemProcessor<Template.Part, Set<Reference>> {

	private static final Pattern ISBN_PATTERN = Pattern.compile("(ISBN )([0-9\\-\\s]{9,17}[0-9X]?)");

	private ReferenceRepository referenceRepository;

	private GuidGenerator guidGenerator;

	private ReferenceFactory referenceFactory;

	@Inject
	public ReferencesFromTemplatePartProcessor(ReferenceRepository referenceRepository, GuidGenerator guidGenerator,
			ReferenceFactory referenceFactory) {
		this.referenceRepository = referenceRepository;
		this.guidGenerator = guidGenerator;
		this.referenceFactory = referenceFactory;
	}

	@Override
	public Set<Reference> process(Template.Part item) throws Exception {
		if (!TemplateName.REFERENCE.equals(item.getKey())) {
			return Sets.newHashSet();
		}

		Set<Pair<ReferenceType, String>> pairs = Sets.newLinkedHashSet();

		String value = item.getValue();
		List<Template> templateList = item.getTemplates();

		if (StringUtils.isNotBlank(value)) {
			Matcher isbnMatcher = ISBN_PATTERN.matcher(value);

			int found = 0;
			while (isbnMatcher.find()) {
				pairs.add(Pair.of(ReferenceType.ISBN, StringUtils.trim(isbnMatcher.group(2))));
				found++;
			}

			if (found == 0) {
				log.warn("Could not parse reference number {}", value);
			}
		} else if (CollectionUtils.isNotEmpty(templateList)) {
			templateList.stream()
					.map(this::templateToPair)
					.filter(Objects::nonNull)
					.forEach(pairs::add);
		}

		return pairsToReferenceSet(pairs);
	}

	private Pair<ReferenceType, String> templateToPair(Template template) {
		String templateTitle = template.getTitle();
		List<Template.Part> templatePartList = template.getParts();

		if (CollectionUtils.isEmpty(templatePartList)) {
			log.warn("Template part list is empty for template {}", template);
			return null;
		}

		Template.Part firstTemplatePart = templatePartList.get(0);

		if (TemplateName.ASIN.equals(templateTitle)) {
			return Pair.of(ReferenceType.ASIN, firstTemplatePart.getValue());
		}

		log.warn("Unrecognized template title {}", templateTitle);
		return null;
	}

	private Set<Reference> pairsToReferenceSet(Set<Pair<ReferenceType, String>> pairs) {
		return pairs.stream()
				.map(guidGenerator::generateFromReference)
				.filter(Objects::nonNull)
				.map(this::guidToReference)
				.collect(Collectors.toSet());
	}

	private synchronized Reference guidToReference(String guid) {
		Optional<Reference> referenceOptional = referenceRepository.findByGuid(guid);

		if (referenceOptional.isPresent()) {
			return referenceOptional.get();
		}

		Reference reference = referenceFactory.createFromGuid(guid);
		return referenceRepository.save(reference);
	}

}
