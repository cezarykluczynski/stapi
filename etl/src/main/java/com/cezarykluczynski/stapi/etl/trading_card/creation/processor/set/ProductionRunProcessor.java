package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetValueWithName;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ProductionRunProcessor implements ItemProcessor<TradingCardSetValueWithName, ProductionRunDTO> {

	private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d{1,3}(,\\d{3}|\\d)?)");
	private static final Pattern SETS_PATTERN = Pattern.compile("(Sets|Packs)");
	private static final String BOXES = "Boxes";

	private final BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProvider;

	public ProductionRunProcessor(BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProvider) {
		this.boxesPerCaseFixedValueProvider = boxesPerCaseFixedValueProvider;
	}

	@Override
	public ProductionRunDTO process(TradingCardSetValueWithName item) throws Exception {
		if (item == null) {
			return null;
		}

		FixedValueHolder<ProductionRunDTO> productionRunDTOFixedValueHolder = boxesPerCaseFixedValueProvider.getSearchedValue(item.getName());
		if (productionRunDTOFixedValueHolder.isFound()) {
			return productionRunDTOFixedValueHolder.getValue();
		}

		String value = item.getValue();
		Integer productionRun = getProductionRun(value);
		ProductionRunUnit productionRunUnit = getProductionRunUnit(value);
		return getProductionRunDTO(productionRun, productionRunUnit);
	}

	private Integer getProductionRun(String value) {
		List<String> numbers = getNumbers(value);

		if (numbers.isEmpty()) {
			return null;
		}

		return sumProductionRun(value, numbers);
	}

	private ProductionRunUnit getProductionRunUnit(String value) {
		boolean isBoxes = StringUtils.contains(value, BOXES);
		boolean isSets = SETS_PATTERN.matcher(value).matches();

		if (isSets && isBoxes) {
			log.warn("Could not determine if \"{}\" is sets or boxes", value);
			return null;
		}

		return isBoxes ? ProductionRunUnit.BOX : ProductionRunUnit.SET;
	}

	private ProductionRunDTO getProductionRunDTO(Integer productionRun, ProductionRunUnit productionRunUnit) {
		return productionRun == null || productionRunUnit == null ? null : ProductionRunDTO.of(productionRun, productionRunUnit);
	}

	private List<String> getNumbers(String value) {
		Matcher numberMatcher = NUMBER_PATTERN.matcher(value);

		List<String> numbers = Lists.newArrayList();

		while (numberMatcher.find()) {
			numbers.add(StringUtils.remove(numberMatcher.group(1), ","));
		}
		return numbers;
	}

	private Integer sumProductionRun(String value, List<String> numbers) {
		Integer productionRun;

		try {
			productionRun = numbers.stream()
					.mapToInt(Ints::tryParse)
					.sum();
		} catch (NullPointerException e) {
			log.error("Could not parse \"{}\" into ProductionRunDTO, {}", value, e);
			return null;
		}

		return productionRun;
	}

}
