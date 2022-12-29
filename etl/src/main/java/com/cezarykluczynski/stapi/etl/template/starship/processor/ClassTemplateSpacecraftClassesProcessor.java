package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassTemplateSpacecraftClassesProcessor implements ItemProcessor<Template.Part, Pair<List<SpacecraftClass>, List<SpacecraftType>>> {

	private static final String ALT = "alt";
	private static final String ALTERNATE_REALITY = " (alternate reality)";
	private static final String CLASS = " class";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";

	private final EntityLookupByNameService entityLookupByNameService;

	@Override
	@NonNull
	public Pair<List<SpacecraftClass>, List<SpacecraftType>> process(Template.Part item) throws Exception {
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList();
		List<SpacecraftType> spacecraftTypeList = Lists.newArrayList();
		Optional<Template> classTemplateOptional = getFirstClassTemplate(item);
		Optional<Template> typeTemplateOptional = getFirstTypeTemplate(item);

		if (classTemplateOptional.isPresent()) {
			Template template = classTemplateOptional.get();
			List<Template.Part> templatePartList = template.getParts();

			Optional<Template.Part> firstTemplatePartOptional = getTemplatePart(templatePartList, TemplateParam.FIRST);
			Optional<Template.Part> secondTemplatePartOptional = getTemplatePart(templatePartList, TemplateParam.SECOND);

			String pageTitleCandidate = null;

			if (secondTemplatePartOptional.isPresent() && firstTemplatePartOptional.isPresent()) {
				pageTitleCandidate = pageTitleCandidateFromTwoParts(firstTemplatePartOptional.get(), secondTemplatePartOptional.get());
			} else if (firstTemplatePartOptional.isPresent()) {
				pageTitleCandidate = wrapWithClassSuffix(firstTemplatePartOptional.get());
			} else {
				log.warn("Class template found, but no parts with right content: {}", template);
			}

			if (pageTitleCandidate != null) {
				entityLookupByNameService.findSpacecraftClassByName(pageTitleCandidate, MediaWikiSource.MEMORY_ALPHA_EN)
						.ifPresent(spacecraftClassList::add);
			}
		}

		if (typeTemplateOptional.isPresent()) {
			final Set<String> nameCandidates = item.getTemplates().stream()
					.filter(template -> TemplateTitle.TYPE.equals(template.getTitle()))
					.map(template -> String.format("%s type", template.getParts().get(0).getValue()))
					.collect(Collectors.toSet());

			nameCandidates.forEach(nameCandidate -> {
				final Optional<SpacecraftClass> spacecraftClassByName = entityLookupByNameService
						.findSpacecraftClassByName(nameCandidate, MediaWikiSource.MEMORY_ALPHA_EN);
				spacecraftClassByName.ifPresent(spacecraftClassList::add);
				entityLookupByNameService.findSpacecraftTypeByName(nameCandidate, MediaWikiSource.MEMORY_ALPHA_EN)
						.ifPresent(spacecraftTypeList::add);
			});
		}

		return Pair.of(spacecraftClassList, spacecraftTypeList);
	}

	private Optional<Template> getFirstClassTemplate(Template.Part item) {
		return item.getTemplates().stream()
				.filter(template -> TemplateTitle.CLASS.equals(template.getTitle()))
				.findFirst();
	}

	private Optional<Template> getFirstTypeTemplate(Template.Part item) {
		return item.getTemplates().stream()
				.filter(template -> TemplateTitle.TYPE.equals(template.getTitle()))
				.findFirst();
	}

	private String pageTitleCandidateFromTwoParts(Template.Part firstTemplatePart, Template.Part secondTemplatePart) {
		String pageTitleCandidate;
		if (StringUtils.equalsIgnoreCase(ALT, secondTemplatePart.getValue())) {
			pageTitleCandidate = wrapWithClassSuffix(firstTemplatePart) + ALTERNATE_REALITY;
		} else {
			pageTitleCandidate = wrapWithClassSuffix(firstTemplatePart) + StringUtils.SPACE + LEFT_BRACKET + secondTemplatePart.getValue()
					+ RIGHT_BRACKET;
		}
		return pageTitleCandidate;
	}

	private String wrapWithClassSuffix(Template.Part templatePart) {
		return templatePart.getValue() + CLASS;
	}

	private Optional<Template.Part> getTemplatePart(List<Template.Part> templatePartList, String paramName) {
		return templatePartList.stream()
				.filter(templatePart -> paramName.equals(templatePart.getKey()))
				.findFirst();
	}

}
