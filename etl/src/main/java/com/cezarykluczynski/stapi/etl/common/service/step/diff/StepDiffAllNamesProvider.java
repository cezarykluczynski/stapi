package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import com.cezarykluczynski.stapi.client.rest.StapiRestClient;
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage;
import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StepDiffAllNamesProvider {

	private static final String TITLE = "title";
	private static final String NAME = "name";
	private static final String PAGE_NUMBER = "pageNumber";

	private final StepNameToApiProvider stepNameToApiProvider;

	@SneakyThrows
	@SuppressWarnings("NPathComplexity")
	public List<String> get(String stepName, StapiRestClient stapiRestClient) {
		Field field = stepNameToApiProvider.provide(stepName);
		if (field == null) {
			log.info("No search method configured for step {}, so no step diff will be provided.", stepName);
			throw new StepDiffUnavailableException();
		}
		Object api = field.get(stapiRestClient);
		final Method searchMethod = Arrays.stream(api.getClass().getDeclaredMethods())
				.filter(method -> method.getName().startsWith("search"))
				.filter(method -> method.getAnnotation(Deprecated.class) == null)
				.filter(method -> method.getParameterCount() == 1)
				.findFirst().orElse(null);

		Object criteria = Class.forName(searchMethod.getParameterTypes()[0].getName()).getConstructors()[0].newInstance();
		ReflectionUtil.setFieldValue(criteria, PAGE_NUMBER, 0);
		ReflectionUtil.setFieldValue(criteria, "pageSize", 100);

		List<Object> results = Lists.newArrayList();
		boolean hasNext = true;
		Integer pageNumber = 0;
		while (hasNext) {
			Object baseResponse;
			try {
				baseResponse = searchMethod.invoke(api, criteria);
			} catch (Exception e) {
				log.error("Error searching for entities using search method {}. Not returning diff for step {}.", searchMethod.getName(), stepName);
				throw new StepDiffUnavailableException();
			}
			Field[] baseResponseFields = ReflectionUtil.getDeclaredAccessibleFields(baseResponse.getClass());
			ResponsePage responsePage;
			for (Field baseResponseField : baseResponseFields) {
				Object candidate = baseResponseField.get(baseResponse);
				if ("page".equals(baseResponseField.getName())) {
					responsePage = (ResponsePage) candidate;
					hasNext = !responsePage.getLastPage();
				}
				if (List.class.isAssignableFrom(candidate.getClass())) {
					results.addAll((List) candidate);
				}
			}
			pageNumber++;
			ReflectionUtil.setFieldValue(criteria, PAGE_NUMBER, pageNumber);
		}

		Field[] restEntityFields = ReflectionUtil.getDeclaredAccessibleFields(results.get(0).getClass());
		Map<String, Field> restEntityFieldsMap = Arrays.stream(restEntityFields).collect(Collectors.toMap(Field::getName, Function.identity()));
		AtomicReference<Field> nameField = new AtomicReference<>();
		if (restEntityFieldsMap.containsKey(TITLE)) {
			nameField.set(restEntityFieldsMap.get(TITLE));
		} else if (restEntityFieldsMap.containsKey(NAME)) {
			nameField.set(restEntityFieldsMap.get(NAME));
		} else {
			return List.of();
		}

		return results.stream()
				.map(object -> {
					try {
						return nameField.get().get(object).toString();
					} catch (IllegalAccessException e) {
						log.error("Error mapping getting field name {} from object. Not returning diff for step {}.", nameField.get(), stepName);
						throw new StepDiffUnavailableException();
					}
				})
				.collect(Collectors.toList());
	}

}
