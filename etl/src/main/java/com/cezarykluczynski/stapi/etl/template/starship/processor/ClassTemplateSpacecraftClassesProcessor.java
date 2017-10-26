package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassTemplateSpacecraftClassesProcessor implements ItemProcessor<Template.Part, List<SpacecraftClass>> {

	private static final String ALT = "alt";
	private static final String ALTERNATE_REALITY = " (alternate reality)";
	private static final String CLASS = " class";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";

	private final SpacecraftClassRepository spacecraftClassRepository;

	public ClassTemplateSpacecraftClassesProcessor(SpacecraftClassRepository spacecraftClassRepository) {
		this.spacecraftClassRepository = spacecraftClassRepository;
	}

	@Override
	public List<SpacecraftClass> process(Template.Part item) throws Exception {
		List<SpacecraftClass> spacecraftClassList = Lists.newArrayList();
		Optional<Template> templateOptional = getFirstClassTemplate(item);

		if (!templateOptional.isPresent()) {
			return spacecraftClassList;
		}

		Template template = templateOptional.get();
		List<Template.Part> templatePartList = template.getParts();

		Optional<Template.Part> firstTemplatePartOptional = getTemplatePart(templatePartList, TemplateParam.FIRST);
		Optional<Template.Part> secondTemplatePartOptional = getTemplatePart(templatePartList, TemplateParam.SECOND);

		String pageTitleCandidate;

		if (secondTemplatePartOptional.isPresent() && firstTemplatePartOptional.isPresent()) {
			pageTitleCandidate = pageTitleCandidateFromTwoParts(firstTemplatePartOptional.get(), secondTemplatePartOptional.get());
		} else if (firstTemplatePartOptional.isPresent()) {
			pageTitleCandidate = wrapWithClassSuffix(firstTemplatePartOptional.get());
		} else {
			log.warn("Class template found, but no parts with right content: {}", template);
			return spacecraftClassList;
		}

		spacecraftClassRepository.findByPageTitleAndPageMediaWikiSource(pageTitleCandidate, MediaWikiSource.MEMORY_ALPHA_EN)
				.ifPresent(spacecraftClassList::add);

		return spacecraftClassList;
	}

	private Optional<Template> getFirstClassTemplate(Template.Part item) {
		return item.getTemplates().stream()
				.filter(template -> TemplateTitle.CLASS.equals(template.getTitle()))
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
