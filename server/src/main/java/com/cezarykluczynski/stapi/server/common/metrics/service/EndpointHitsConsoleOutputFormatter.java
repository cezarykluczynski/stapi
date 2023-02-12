package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonEntitiesDetailsReader;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	private static final String REST_ENDPOINT = "RestEndpoint";
	private static final String TEMPLATE_TWO_STRINGS = "%s%s";
	private static final String TEMPLATE_THREE_STRINGS = "%s%s%s";

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final LocalDateTime applicationStartTime = LocalDateTime.now();

	public EndpointHitsConsoleOutputFormatter(CommonEntitiesDetailsReader commonEntitiesDetailsReader) {
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
	}

	@SuppressWarnings({"NPathComplexity", "CyclomaticComplexity"})
	public String formatForConsolePrint(Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser) {
		Map<MetricsEndpointKeyDTO, Long> filteredEndpointsHits = endpointsHits.entrySet()
				.stream().filter(metricsEndpointKeyDTOLongEntry -> metricsEndpointKeyDTOLongEntry.getKey().isApiBrowser() == apiBrowser)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		List<String> endpointNames = filteredEndpointsHits.entrySet()
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

		filteredEndpointsHits = filteredEndpointsHits.entrySet().stream()
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

		List<List<String>> rows = Lists.newArrayList();
		for (Section section : Section.values()) {
			if (Section.SUMMARY.equals(section)) {
				List<List<String>> sectionRows = Lists.newArrayList();
				for (SummaryRow summaryRow : SummaryRow.values()) {
					if (SummaryRow.HEADERS.equals(summaryRow)) {
						List<String> rowCells = Lists.newArrayList(EMPTY_STRING);
						for (String hitVersion : hitVersions) {
							rowCells.add(String.format(HITS_TEMPLATE, hitVersion));
						}
						rowCells.add(TOTAL);
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
						List<String> localRowCells = Lists.newArrayList();
						for (String hitVersion : hitVersions) {
							localRowCells.add(getNumbersFor(filteredEndpointsHits, section, summaryRow, null, hitVersion, hitVersions,
									primaryEntitiesNames));
						}
						localRowCells.add(getNumbersFor(filteredEndpointsHits, section, summaryRow, null, TOTAL, hitVersions, primaryEntitiesNames));
						rowCells.addAll(localRowCells);
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
					List<String> localRowCells = Lists.newArrayList();
					for (String hitVersion : hitVersions) {
						localRowCells.add(getNumbersFor(filteredEndpointsHits, section, null, primaryEntityName, hitVersion, hitVersions,
								primaryEntitiesNames));
					}
					localRowCells.add(getNumbersFor(filteredEndpointsHits, section, null, primaryEntityName, TOTAL, hitVersions,
							primaryEntitiesNames));
					if (localRowCells.stream().anyMatch(s -> Ints.tryParse(s) > 0)) {
						rowCells.addAll(localRowCells);
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
		String source = apiBrowser ? "from stapi.co frontend" : "outside of the stapi.co frontend";
		String report = String.format("%nReports hits since application startup (%s days ago on %s, %s):",
				Duration.between(applicationStartTime, LocalDateTime.now()).toDays(), applicationStartTime.format(DATE_TIME_FORMATTER), source);

		String rowTemplate = "%n" + NumberUtil.inclusiveRangeOf(1, max).stream().map(integer -> "%s").collect(Collectors.joining(" | "));
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

		return report;
	}

	@SuppressWarnings({"NPathComplexity", "CyclomaticComplexity", "ParameterNumber"})
	private String getNumbersFor(Map<MetricsEndpointKeyDTO, Long> endpointsHits, Section section, SummaryRow summaryRow, String entityName,
			String hitVersion, List<String> hitVersions, List<String> primaryEntitiesNames) {
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
		Integer integer = Ints.tryParse(rowCells.get(rowCells.size() - 1));
		if (integer == null) {
			return Integer.MAX_VALUE;
		}
		return integer;
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
