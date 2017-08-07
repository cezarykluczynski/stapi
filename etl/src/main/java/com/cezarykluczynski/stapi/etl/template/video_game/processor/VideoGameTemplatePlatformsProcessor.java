package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.template.common.factory.PlatformFactory;
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VideoGameTemplatePlatformsProcessor implements ItemProcessor<Template.Part, Set<Platform>> {

	private final PlatformFactory platformFactory;

	public VideoGameTemplatePlatformsProcessor(PlatformFactory platformFactory) {
		this.platformFactory = platformFactory;
	}

	@Override
	public Set<Platform> process(Template.Part item) throws Exception {
		Set<Platform> platformSet = Sets.newHashSet();

		if (item == null || item.getTemplates().isEmpty()) {
			return platformSet;
		}

		Set<Template.Part> templatePartSet = Sets.newHashSet();
		item.getTemplates()
				.stream()
				.filter(template -> TemplateTitle.PLATFORM.equals(template.getTitle()))
				.forEach(template -> templatePartSet.addAll(template.getParts()
						.stream()
						.filter(part -> TemplateParam.FIRST.equals(part.getKey()))
						.collect(Collectors.toList())));

		templatePartSet.forEach(part -> {
			Optional<Platform> platformOptional = platformFactory.createForCode(part.getValue());
			platformOptional.ifPresent(platformSet::add);
		});

		return platformSet;
	}

}
