package com.cezarykluczynski.stapi.etl.reference.processor;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.model.reference.factory.ReferenceFactory;
import com.cezarykluczynski.stapi.model.reference.repository.ReferenceRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

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
	private static final Pattern EAN_PATTERN = Pattern.compile("(EAN)(:)? ([0-9]{8,13})");
	private static final Pattern ISRC_PATTERN = Pattern.compile("(ISRC)(:)? ([0-9A-Z\\-]{12,15})");

	private final ReferenceRepository referenceRepository;

	private final UidGenerator uidGenerator;

	private final ReferenceFactory referenceFactory;

	public ReferencesFromTemplatePartProcessor(ReferenceRepository referenceRepository, UidGenerator uidGenerator,
			ReferenceFactory referenceFactory) {
		this.referenceRepository = referenceRepository;
		this.uidGenerator = uidGenerator;
		this.referenceFactory = referenceFactory;
	}

	@Override
	public Set<Reference> process(Template.Part item) throws Exception {
		if (!TemplateTitle.REFERENCE.equals(item.getKey())) {
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

			Matcher eanMatcher = EAN_PATTERN.matcher(value);

			while (eanMatcher.find()) {
				String ean = eanMatcher.group(3);
				if (StringUtils.length(ean) == 8 || StringUtils.length(ean) == 13) {
					pairs.add(Pair.of(ReferenceType.EAN, StringUtils.trim(ean)));
					found++;
				}
			}

			Matcher isrcMatcher = ISRC_PATTERN.matcher(value);

			while (isrcMatcher.find()) {
				String isrc = StringUtils.trim(StringUtils.replace(isrcMatcher.group(3), "-", ""));
				if (StringUtils.length(isrc) == 12) {
					pairs.add(Pair.of(ReferenceType.ISRC, isrc));
					found++;
				}
			}

			if (found == 0) {
				log.info("Could not parse reference number \"{}\"", value);
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

		if (TemplateTitle.ASIN.equals(templateTitle)) {
			return Pair.of(ReferenceType.ASIN, firstTemplatePart.getValue());
		}

		log.warn("Unrecognized template title {}", templateTitle);
		return null;
	}

	private Set<Reference> pairsToReferenceSet(Set<Pair<ReferenceType, String>> pairs) {
		return pairs.stream()
				.map(uidGenerator::generateForReference)
				.filter(Objects::nonNull)
				.map(this::uidToReference)
				.collect(Collectors.toSet());
	}

	private synchronized Reference uidToReference(String uid) {
		Optional<Reference> referenceOptional = referenceRepository.findByUid(uid);

		if (referenceOptional.isPresent()) {
			return referenceOptional.get();
		}

		Reference reference = referenceFactory.createFromUid(uid);
		return referenceRepository.save(reference);
	}

}
