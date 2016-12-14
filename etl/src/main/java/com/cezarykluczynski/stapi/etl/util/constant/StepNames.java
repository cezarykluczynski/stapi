package com.cezarykluczynski.stapi.etl.util.constant;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class StepNames {

	public static final Map<String, Set<String>> JOB_STEPS = Maps.newHashMap();

	static {
		JOB_STEPS.put(JobName.JOB_CREATE, Sets.newHashSet(
				StepName.CREATE_SERIES,
				StepName.CREATE_PERFORMERS,
				StepName.CREATE_STAFF,
				StepName.CREATE_CHARACTERS,
				StepName.CREATE_EPISODES
		));
	}

}
