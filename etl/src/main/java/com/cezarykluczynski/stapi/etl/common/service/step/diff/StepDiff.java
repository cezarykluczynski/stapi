package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StepDiff {

	private String stepName;

	private List<String> uniquePreviousNames;

	private List<String> uniqueCurrentNames;

	private int previousNamesSize;

	private int currentNamesSize;

}
