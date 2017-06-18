package com.cezarykluczynski.stapi.etl.video_release.creation.configuration;

import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class VideoReleaseCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public VideoReleaseReader videoReleaseReader() {
		List<PageHeader> videoReleaseList = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_VIDEO_RELEASES)) {
			videoReleaseList.addAll(categoryApi.getPagesIncludingSubcategories(CategoryTitle.VIDEO_RELEASES, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new VideoReleaseReader(Lists.newArrayList(Sets.newHashSet(videoReleaseList)));
	}

}
