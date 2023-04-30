package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import com.cezarykluczynski.stapi.client.rest.StapiRestClient;
import io.micrometer.common.util.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepDiffProvider {

	private final StepDiffAllNamesProvider stepDiffAllNamesProvider;

	private final StapiRestClient previousVersionClient;

	private final StapiRestClient currentVersionClient;

	public StepDiffProvider(StepDiffProperties stepDiffProperties, StepDiffAllNamesProvider stepDiffAllNamesProvider,
			@Value("${server.port}") Integer port) {
		this.stepDiffAllNamesProvider = stepDiffAllNamesProvider;
		String previousVersionUrl = stepDiffProperties.getPreviousVersionUrl();
		if (StringUtils.isBlank(previousVersionUrl)) {
			previousVersionUrl = "https://stapi.co/";
		}
		previousVersionClient = new StapiRestClient(previousVersionUrl);

		String currentVersionUrl = stepDiffProperties.getCurrentVersionUrl();
		if (StringUtils.isBlank(currentVersionUrl)) {
			currentVersionUrl = String.format("http://localhost:%d/", port);
		}
		currentVersionClient = new StapiRestClient(currentVersionUrl);
	}


	public StepDiff provide(StepExecution stepExecution) {
		final String stepName = stepExecution.getStepName();
		final List<String> previousNames = stepDiffAllNamesProvider.get(stepName, previousVersionClient);
		final List<String> currentNames = stepDiffAllNamesProvider.get(stepName, currentVersionClient);
		final List<String> uniquePreviousNames = Lists.newArrayList();
		final List<String> uniqueCurrentNames = Lists.newArrayList();
		for (String previousName : previousNames) {
			if (!currentNames.contains(previousName)) {
				uniquePreviousNames.add(previousName);
			}
		}
		for (String currentName : currentNames) {
			if (!previousNames.contains(currentName)) {
				uniqueCurrentNames.add(currentName);
			}
		}

		return new StepDiff(stepName, uniquePreviousNames, uniqueCurrentNames);
	}

}
