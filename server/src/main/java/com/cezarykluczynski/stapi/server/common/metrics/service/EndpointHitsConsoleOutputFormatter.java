package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO;
import com.cezarykluczynski.stapi.server.common.reader.CommonEntitiesDetailsReader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EndpointHitsConsoleOutputFormatter {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
	private static final Set<TrackedEntityType> ENTITY_TYPES = Set.of(TrackedEntityType.FICTIONAL_PRIMARY, TrackedEntityType.REAL_WORLD_PRIMARY);
	private static final String TOTAL = "Total";
	private static final String ROW_STRING_TEMPLATE = "%n%s | %s | %s | %s | %s | %s";
	private static final String HEADER_ENTITY = "Entity";
	private static final String HEADER_REST_HITS = "REST hits";
	private static final String HEADER_SOAP_HITS = "SOAP hits";
	private static final String HEADER_TOTAL_HITS = "Total hits";
	private static final String HEADER_REST_HITS_PERCENTAGE = "REST hits %";
	private static final String HEADER_SOAP_HITS_PERCENTAGE = "SOAP hits %";
	private static final String PAD_CHAR = " ";
	private static final String DASH = "-";
	private static final String PERCENTAGE = "%";
	private static final String REST_ENDPOINT = "RestEndpoint";
	private static final String SOAP_ENDPOINT = "SoapEndpoint";

	private final CommonEntitiesDetailsReader commonEntitiesDetailsReader;

	private final LocalDateTime applicationStartTime = LocalDateTime.now();

	public EndpointHitsConsoleOutputFormatter(CommonEntitiesDetailsReader commonEntitiesDetailsReader) {
		this.commonEntitiesDetailsReader = commonEntitiesDetailsReader;
	}

	@SuppressWarnings("NPathComplexity")
	public String formatForConsolePrint(Map<MetricsEndpointKeyDTO, Long> endpointsHits) {
		List<String> primaryEntitiesNames = commonEntitiesDetailsReader.details().getDetails()
				.stream()
				.filter(restEndpointDetailDTO -> ENTITY_TYPES.contains(restEndpointDetailDTO.getType()))
				.map(RestEndpointDetailDTO::getName)
				.collect(Collectors.toList());

		final Map<String, ReportRow> entitiesToReportRows = primaryEntitiesNames.stream()
				.map(primaryEntityName -> {
					ReportRow reportRow = new ReportRow();
					reportRow.entityName = primaryEntityName;
					return reportRow;
				})
				.collect(Collectors.toMap(reportRow -> reportRow.entityName, Function.identity()));

		endpointsHits.forEach((key, value) -> {
			final String endpointName = key.getEndpointName();
			EndpointType endpointType = endpointTypeFromEndpointName(endpointName);
			String possibleEntityName = toPossiblePrimaryEntityName(endpointName);
			if (entitiesToReportRows.containsKey(possibleEntityName)) {
				final ReportRow reportRow = entitiesToReportRows.get(possibleEntityName);
				if (EndpointType.REST.equals(endpointType)) {
					reportRow.restHits += value;
				} else if (EndpointType.SOAP.equals(endpointType)) {
					reportRow.soapHits += value;
				}
			}
		});

		AtomicInteger totalRestHits = new AtomicInteger();
		AtomicInteger totalSoapHits = new AtomicInteger();
		entitiesToReportRows.forEach((key, reportRow) -> {
			totalRestHits.addAndGet(reportRow.restHits);
			totalSoapHits.addAndGet(reportRow.soapHits);
		});

		entitiesToReportRows.computeIfAbsent(TOTAL, s -> {
			ReportRow reportRow = new ReportRow();
			reportRow.entityName = s;
			reportRow.restHits = totalRestHits.get();
			reportRow.soapHits = totalSoapHits.get();
			return reportRow;
		});

		AtomicInteger entityNameColumnLength = new AtomicInteger();
		entitiesToReportRows.entrySet()
				.forEach(stringReportRowEntry -> {
					String entityName = stringReportRowEntry.getKey();
					if (entityName.length() > entityNameColumnLength.get()) {
						entityNameColumnLength.set(entityName.length());
					}
					final ReportRow reportRow = stringReportRowEntry.getValue();
					final int restHits = reportRow.restHits;
					reportRow.totalHits = reportRow.restHits + reportRow.soapHits;
					if (reportRow.totalHits > 0) {
						@SuppressFBWarnings("ICAST_IDIV_CAST_TO_DOUBLE")
						int restHitsPercentage = 100 * restHits / reportRow.totalHits;
						int soapHitsPercentage = 100 - restHitsPercentage;
						reportRow.restHitsPercentage = restHitsPercentage;
						reportRow.soapHitsPercentage = soapHitsPercentage;
					}
				});

		String report = String.format("%nReports hits since application startup (%s days ago on %s):",
				Duration.between(applicationStartTime, LocalDateTime.now()).toDays(), applicationStartTime.format(DATE_TIME_FORMATTER));
		StringBuilder stringBuilder = new StringBuilder(report);
		stringBuilder.append(String.format(ROW_STRING_TEMPLATE,
				StringUtils.rightPad(HEADER_ENTITY, entityNameColumnLength.get(), PAD_CHAR),
				StringUtils.leftPad(HEADER_REST_HITS, HEADER_REST_HITS.length(), PAD_CHAR),
				StringUtils.leftPad(HEADER_SOAP_HITS, HEADER_SOAP_HITS.length(), PAD_CHAR),
				StringUtils.leftPad(HEADER_TOTAL_HITS, HEADER_TOTAL_HITS.length(), PAD_CHAR),
				StringUtils.leftPad(HEADER_REST_HITS_PERCENTAGE, HEADER_REST_HITS_PERCENTAGE.length(), PAD_CHAR),
				StringUtils.leftPad(HEADER_SOAP_HITS_PERCENTAGE, HEADER_SOAP_HITS_PERCENTAGE.length(), PAD_CHAR)
		));
		final Map<String, ReportRow> nonEmptyEntitiesToReportRows = entitiesToReportRows.entrySet()
				.stream()
				.filter(stringReportRowEntry -> stringReportRowEntry.getValue().totalHits > 0 || TOTAL.equals(stringReportRowEntry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		nonEmptyEntitiesToReportRows.entrySet()
				.stream()
				.sorted((o1, o2) -> {
					if (TOTAL.equals(o1.getKey())) {
						return -1;
					} else if (TOTAL.equals(o2.getKey())) {
						return 1;
					} else {
						final int o1Total = o1.getValue().totalHits;
						final int o2Total = o2.getValue().totalHits;
						int compare = Integer.compare(o2Total, o1Total);
						if (compare == 0) {
							final int o1RestHits = o1.getValue().restHits;
							final int o2RestHits = o2.getValue().restHits;
							return Integer.compare(o2RestHits, o1RestHits);
						} else {
							return compare;
						}
					}
				})
				.forEach(stringReportRowEntry -> {
					final ReportRow reportRow = stringReportRowEntry.getValue();
					final String entityName = stringReportRowEntry.getKey();
					final String restHitsPercentage = reportRow.restHitsPercentage == null ? DASH : reportRow.restHitsPercentage + PERCENTAGE;
					final String soapHitsPercentage = reportRow.soapHitsPercentage == null ? DASH : reportRow.soapHitsPercentage + PERCENTAGE;

					String line = String.format(ROW_STRING_TEMPLATE,
							StringUtils.rightPad(entityName, entityNameColumnLength.get(), PAD_CHAR),
							StringUtils.leftPad(String.valueOf(reportRow.restHits), HEADER_REST_HITS.length(), PAD_CHAR),
							StringUtils.leftPad(String.valueOf(reportRow.soapHits), HEADER_SOAP_HITS.length(), PAD_CHAR),
							StringUtils.leftPad(String.valueOf(reportRow.totalHits), HEADER_TOTAL_HITS.length(), PAD_CHAR),
							StringUtils.leftPad(restHitsPercentage, HEADER_REST_HITS_PERCENTAGE.length(), PAD_CHAR),
							StringUtils.leftPad(soapHitsPercentage, HEADER_SOAP_HITS_PERCENTAGE.length(), PAD_CHAR)
					);
					stringBuilder.append(line);
					if (TOTAL.equals(entityName) && nonEmptyEntitiesToReportRows.size() > 1) {
						stringBuilder.append(String.format("%n%s", StringUtils.leftPad(DASH, StringUtils.trim(line).length(), DASH)));
					}
				});

		return stringBuilder.toString();
	}

	private String toPossiblePrimaryEntityName(String endpointName) {
		return endpointName.replace(SOAP_ENDPOINT, "")
				.replace("V2RestEndpoint", "")
				.replace(REST_ENDPOINT, "");
	}

	private EndpointType endpointTypeFromEndpointName(String endpointName) {
		if (endpointName.contains(REST_ENDPOINT)) {
			return EndpointType.REST;
		} else if (endpointName.contains(SOAP_ENDPOINT)) {
			return EndpointType.SOAP;
		}

		return null;
	}

	private static class ReportRow {

		String entityName;
		int restHits;
		int soapHits;
		int totalHits;
		Integer restHitsPercentage;
		Integer soapHitsPercentage;

	}

}
