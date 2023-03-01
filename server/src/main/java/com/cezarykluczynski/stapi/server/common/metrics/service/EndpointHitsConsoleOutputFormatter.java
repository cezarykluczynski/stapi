package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonEntitiesDetailsReader;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EndpointHitsConsoleOutputFormatter {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
	private static final Set<TrackedEntityType> ENTITY_TYPES = Set.of(TrackedEntityType.FICTIONAL_PRIMARY, TrackedEntityType.REAL_WORLD_PRIMARY);
	private static final String TOTAL = "Total";
	private static final String SEARCH = "Search";
	private static final String GET = "Get";
	private static final Map<Section, String> SECTION_PREFIXES = Map.of(Section.SEARCH, SEARCH, Section.GET, GET);
	private static final String HITS_TEMPLATE = "Hits %s";
	private static final String V1 = "V1";
	private static final String V = "V";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";
	private static final String DASH = "-";
	private static final String PIPE = "|";
	private static final String DOUBLE_PIPE = "||";
	private static final String REST_ENDPOINT = "RestEndpoint";
	private static final String TEMPLATE_TWO_STRINGS = "%s%s";
	private static final String TEMPLATE_THREE_STRINGS = "%s%s%s";
	private static final String API_BROWSER_HEADER = "Frontend:";
	private static final String NON_API_BROWSER_HEADER = "Outside the frontend:";
	private static final String NEW_LINE = "%n";

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final LocalDateTime applicationStartTime = LocalDateTime.now();

	public EndpointHitsConsoleOutputFormatter(CommonEntitiesDetailsReader commonEntitiesDetailsReader) {
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
	}

	@SuppressWarnings({"NPathComplexity", "CyclomaticComplexity", "MethodLength", "NestedForDepth"})
	public String formatForConsolePrint(Map<MetricsEndpointKeyDTO, Long> endpointsHits) {
		List<String> endpointNames = endpointsHits.entrySet()
				.stream()
				.map(metricsEndpointKeyDTOLongEntry -> metricsEndpointKeyDTOLongEntry.getKey().getEndpointName())
				.map(endpointName -> StringUtils.substringBefore(endpointName, REST_ENDPOINT))
				.collect(Collectors.toList());

		List<String> hitVersions = Lists.newArrayList(V1);
		final List<Integer> versionCandidates = NumberUtil.inclusiveRangeOf(2, 100);
		for (int versionCandidate : versionCandidates) {
			for (String endpointName : endpointNames) {
				String versionCandidateEndpointNamePart = V + versionCandidate;
				if (endpointName.endsWith(versionCandidateEndpointNamePart)) {
					hitVersions.add(versionCandidateEndpointNamePart);
					break;
				}
			}
		}

		List<String> primaryEntitiesNames = commonEntitiesDetailsReader.details().getDetails()
				.stream()
				.filter(restEndpointDetailDTO -> ENTITY_TYPES.contains(restEndpointDetailDTO.getType()))
				.map(RestEndpointDetailDTO::getName)
				.collect(Collectors.toList());

		Map<String, List<String>> primaryEntitiesToVersions = Maps.newLinkedHashMap();
		for (String entityName : primaryEntitiesNames) {
			List<String> entityVersions = Lists.newArrayList();
			for (MetricsEndpointKeyDTO metricsEndpointKeyDTO : endpointsHits.keySet()) {
				String endpointName = metricsEndpointKeyDTO.getEndpointName();
				boolean matchesV1 = endpointName.equals(String.format(TEMPLATE_TWO_STRINGS, entityName, REST_ENDPOINT));
				if (matchesV1 && !entityVersions.contains(V1)) {
					entityVersions.add(V1);
				}
				for (String hitVersion : hitVersions) {
					if (V1.equals(hitVersion)) {
						continue;
					}
					boolean matchesV = endpointName.equals(String.format(TEMPLATE_THREE_STRINGS, entityName, hitVersion, REST_ENDPOINT));
					if (matchesV && !entityVersions.contains(hitVersion)) {
						entityVersions.add(hitVersion);
					}
				}
			}
			primaryEntitiesToVersions.put(entityName, entityVersions);
		}

		Map<MetricsEndpointKeyDTO, Long> apiBrowserYes = endpointsHits.entrySet()
				.stream().filter(metricsEndpointKeyDTOLongEntry -> metricsEndpointKeyDTOLongEntry.getKey().isApiBrowser())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map<MetricsEndpointKeyDTO, Long> apiBrowserNo = endpointsHits.entrySet()
				.stream().filter(metricsEndpointKeyDTOLongEntry -> !metricsEndpointKeyDTOLongEntry.getKey().isApiBrowser())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		apiBrowserYes = applyVersionMatchFilters(apiBrowserYes, primaryEntitiesNames, hitVersions);
		apiBrowserNo = applyVersionMatchFilters(apiBrowserNo, primaryEntitiesNames, hitVersions);

		List<List<String>> rows = Lists.newArrayList();
		for (Section section : Section.values()) {
			if (Section.SUMMARY.equals(section)) {
				List<List<String>> sectionRows = Lists.newArrayList();
				for (SummaryRow summaryRow : SummaryRow.values()) {
					if (SummaryRow.HEADERS.equals(summaryRow)) {
						List<String> rowCells = Lists.newArrayList();
						boolean pipeAdded = false;
						for (boolean apiBrowser : List.of(true, false)) {
							if (!apiBrowser && !pipeAdded) {
								rowCells.add(PIPE);
								pipeAdded = true;
							}
							if (!pipeAdded) {
								rowCells.add(EMPTY_STRING);
							}
							for (String hitVersion : hitVersions) {
								rowCells.add(String.format(HITS_TEMPLATE, hitVersion));
							}
							rowCells.add(TOTAL);
						}
						sectionRows.add(rowCells);
					} else {
						List<String> rowCells = Lists.newArrayList();
						if (SummaryRow.TOTAL.equals(summaryRow)) {
							rowCells.add(TOTAL);
						} else if (SummaryRow.SEARCH.equals(summaryRow)) {
							rowCells.add(SEARCH);
						} else if (SummaryRow.GET.equals(summaryRow)) {
							rowCells.add(GET);
						}
						boolean pipeAdded = false;
						for (boolean apiBrowser : List.of(true, false)) {
							List<String> localRowCells = Lists.newArrayList();
							if (!apiBrowser && !pipeAdded) {
								localRowCells.add(PIPE);
								pipeAdded = true;
							}
							for (String hitVersion : hitVersions) {
								localRowCells.add(getNumbersFor(apiBrowser ? apiBrowserYes : apiBrowserNo, section, summaryRow, null, hitVersion,
										hitVersions, primaryEntitiesNames, primaryEntitiesToVersions));
							}
							localRowCells.add(getNumbersFor(apiBrowser ? apiBrowserYes : apiBrowserNo, section, summaryRow, null, TOTAL, hitVersions,
									primaryEntitiesNames, primaryEntitiesToVersions));
							rowCells.addAll(localRowCells);
						}
						sectionRows.add(rowCells);
					}
				}
				rows.addAll(sectionRows);
			} else {
				List<List<String>> sectionRows = Lists.newArrayList();
				for (int i = 0; i < primaryEntitiesNames.size(); i++) {
					List<String> rowCells = Lists.newArrayList();
					String cell = EMPTY_STRING;
					String primaryEntityName = primaryEntitiesNames.get(i);
					cell += primaryEntityName;
					rowCells.add(cell);
					boolean pipeAdded = false;
					for (boolean apiBrowser : List.of(true, false)) {
						List<String> localRowCells = Lists.newArrayList();
						if (!apiBrowser && !pipeAdded) {
							localRowCells.add(PIPE);
							pipeAdded = true;
						}
						for (String hitVersion : hitVersions) {
							localRowCells.add(getNumbersFor(apiBrowser ? apiBrowserYes : apiBrowserNo, section, null, primaryEntityName, hitVersion,
									hitVersions, primaryEntitiesNames, primaryEntitiesToVersions));
						}
						localRowCells.add(getNumbersFor(apiBrowser ? apiBrowserYes : apiBrowserNo, section, null, primaryEntityName, TOTAL,
								hitVersions, primaryEntitiesNames, primaryEntitiesToVersions));
						if (localRowCells.stream().anyMatch(s -> {
							Integer integer = Ints.tryParse(s);
							return integer != null && integer > 0;
						}) || rowCells.size() > 1 && pipeAdded) {
							rowCells.addAll(localRowCells);
						}
					}
					if (rowCells.size() > 1) {
						sectionRows.add(rowCells);
					}
				}
				sectionRows = sectionRows.stream()
						.sorted(Comparator.comparing(EndpointHitsConsoleOutputFormatter::comparator).reversed())
						.collect(Collectors.toList());
				if (!sectionRows.isEmpty()) {
					List<String> rowCells = sectionRows.get(0);
					rowCells.set(0, String.format("(%s) %s", SECTION_PREFIXES.get(section).toUpperCase(), rowCells.get(0)));
					rows.add(Lists.newArrayList(DASH));
					rows.addAll(sectionRows);
				}
			}
		}

		List<Integer> paddingsPerColumn = Lists.newArrayList();
		int max = rows.stream().mapToInt(List::size).max().getAsInt();
		for (int i = 0; i < max; i++) {
			int maxLength = 0;
			for (List<String> rowCells : rows) {
				if (rowCells.size() > i) {
					maxLength = Math.max(maxLength, rowCells.get(i).length());
				}
			}
			paddingsPerColumn.add(maxLength);
		}
		String report = String.format("%nReports hits since application startup (%s days ago on %s):",
				Duration.between(applicationStartTime, LocalDateTime.now()).toDays(), applicationStartTime.format(DATE_TIME_FORMATTER));
		String rowTemplate = NEW_LINE + NumberUtil.inclusiveRangeOf(1, max).stream().map(integer -> "%s").collect(Collectors.joining(" | "));
		for (List<String> rowCells : rows) {
			if (rowCells.size() > 1) {
				List<String> paddedRowCells = Lists.newArrayList();
				for (int i = 0; i < rowCells.size(); i++) {
					String paddedRowCell = StringUtils.leftPad(rowCells.get(i), paddingsPerColumn.get(i), SPACE);
					paddedRowCells.add(paddedRowCell);
				}
				final Object[] objects = paddedRowCells.stream().toArray();
				report += String.format(rowTemplate, objects);
			} else {
				int totalLength = paddingsPerColumn.stream().mapToInt(value -> value).sum() + ((paddingsPerColumn.size() - 1) * 3);
				report += String.format("%n%s", StringUtils.leftPad(DASH, totalLength, DASH));
			}
		}
		report = report.replaceAll("\\| \\| \\|", DOUBLE_PIPE);
		final List<String> lines = Lists.newArrayList(Arrays.stream(report.split("\\r?\\n|\\r")).toList());
		final String lineWithSeparator = lines.get(2);
		int separatorIndex = lineWithSeparator.indexOf(DOUBLE_PIPE);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (i == 2) {
				String secondHeader = StringUtils.rightPad(API_BROWSER_HEADER, separatorIndex) + DOUBLE_PIPE + SPACE + NON_API_BROWSER_HEADER;
				lines.add(2, secondHeader);
			}
			if (line.length() > 0 && DASH.equals(line.substring(0, 1))) {
				// 3 is the number of spaces in "\\| \\| \\|"
				String fixedSeparatorLine = line.substring(0, separatorIndex) + "++" + line.substring(separatorIndex + DOUBLE_PIPE.length() + 3);
				lines.set(i, fixedSeparatorLine);
			}
		}

		return lines.stream().collect(Collectors.joining(String.format(NEW_LINE)));
	}

	private Map<MetricsEndpointKeyDTO, Long> applyVersionMatchFilters(Map<MetricsEndpointKeyDTO, Long> endpointsHits,
			List<String> primaryEntitiesNames, List<String> hitVersions) {
		return endpointsHits.entrySet().stream()
				.filter(entry -> {
					String endpointName = entry.getKey().getEndpointName();
					for (String entityName : primaryEntitiesNames) {
						for (String hitVersion : hitVersions) {
							boolean matchesV1 = endpointName.equals(String.format(TEMPLATE_TWO_STRINGS, entityName, REST_ENDPOINT));
							boolean matchesVX = endpointName.equals(String.format(TEMPLATE_THREE_STRINGS, entityName, hitVersion, REST_ENDPOINT));
							if (matchesV1 || matchesVX) {
								return true;
							}
						}
					}
					return false;
				})
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@SuppressWarnings({"NPathComplexity", "CyclomaticComplexity", "ParameterNumber"})
	private String getNumbersFor(Map<MetricsEndpointKeyDTO, Long> endpointsHits, Section section, SummaryRow summaryRow, String entityName,
			String hitVersion, List<String> hitVersions, List<String> primaryEntitiesNames, Map<String, List<String>> primaryEntitiesToVersions) {
		if (entityName != null && !primaryEntitiesToVersions.get(entityName).contains(hitVersion) && !TOTAL.equals(hitVersion)) {
			return DASH;
		}
		return String.format("%d", endpointsHits.entrySet().stream()
				.filter(entry -> {
					if (SummaryRow.SEARCH.equals(summaryRow) || Section.SEARCH.equals(section)) {
						return entry.getKey().getMethodName().startsWith("search");
					} else if (SummaryRow.GET.equals(summaryRow) || Section.GET.equals(section)) {
						return entry.getKey().getMethodName().startsWith("get");
					} else {
						return true;
					}
				})
				.filter(entry -> {
					String endpointName = entry.getKey().getEndpointName();
					boolean isVersionTotal = TOTAL.equals(hitVersion);
					boolean isVersion = hitVersion.startsWith(V);
					boolean isSectionSummary = Section.SUMMARY.equals(section);
					boolean matchesV1 = endpointName.equals(String.format(TEMPLATE_TWO_STRINGS, entityName, REST_ENDPOINT));
					boolean matchesVX = endpointName.equals(String.format(TEMPLATE_THREE_STRINGS, entityName, hitVersion, REST_ENDPOINT));
					if (isVersionTotal) {
						for (String localHitVersion : hitVersions) {
							if (endpointName.equals(String.format(TEMPLATE_THREE_STRINGS, entityName, localHitVersion, REST_ENDPOINT))) {
								matchesVX = true;
								break;
							}
						}
					}
					boolean isV1 = isVersion && V1.equals(hitVersion) && matchesV1;
					boolean isVX = isVersion && !isV1 && matchesVX;

					if (isSectionSummary) {
						if (SummaryRow.TOTAL.equals(summaryRow) || SummaryRow.SEARCH.equals(summaryRow) || SummaryRow.GET.equals(summaryRow)) {
							if (isVersion) {
								for (String localEntityName : primaryEntitiesNames) {
									boolean localMatchesV1 = endpointName.equals(String
											.format(TEMPLATE_TWO_STRINGS, localEntityName, REST_ENDPOINT));
									boolean localMatchesVX = endpointName.equals(String
											.format(TEMPLATE_THREE_STRINGS, localEntityName, hitVersion, REST_ENDPOINT));
									boolean localIsV1 = V1.equals(hitVersion) && localMatchesV1;
									boolean localIsVX = !isV1 && localMatchesVX;
									if (localIsV1 || localIsVX) {
										return true;
									}
								}
								return false;
							}
						}
						return true;
					}

					if (isVersion) {
						return isV1 || isVX;
					} else {
						return matchesV1 || matchesVX;
					}
				})
				.filter(entry -> entry.getValue() > 0L)
				.mapToLong(Map.Entry::getValue)
				.sum());
	}

	private static Integer comparator(List<String> rowCells) {
		return rowCells.stream()
				.mapToInt(value -> {
					Integer integer = Ints.tryParse(value);
					return integer != null ? integer : 0;
				})
				.sum();
	}

	private enum Section {

		SUMMARY,
		SEARCH,
		GET

	}

	private enum SummaryRow {

		HEADERS,
		TOTAL,
		SEARCH,
		GET

	}

}
