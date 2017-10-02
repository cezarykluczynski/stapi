package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.material.creation.service.MaterialPageFilter;
import com.cezarykluczynski.stapi.etl.material.creation.service.MaterialsAndSubstancesDetectorService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MaterialPageProcessor implements ItemProcessor<Page, Material> {

	private final MaterialPageFilter materialPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final MaterialsAndSubstancesDetectorService materialsAndSubstancesDetectorService;

	private final TemplateFinder templateFinder;

	public MaterialPageProcessor(MaterialPageFilter materialPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor,
			MaterialsAndSubstancesDetectorService materialsAndSubstancesDetectorService, TemplateFinder templateFinder) {
		this.materialPageFilter = materialPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.materialsAndSubstancesDetectorService = materialsAndSubstancesDetectorService;
		this.templateFinder = templateFinder;
	}

	@Override
	public Material process(Page item) throws Exception {
		if (materialPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Material material = new Material();

		material.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		material.setPage(pageBindingService.fromPageToPageEntity(item));
		material.setUid(uidGenerator.generateFromPage(material.getPage(), Material.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		String pageTitle = item.getTitle();
		boolean hasChemicalCompoundsCategory = categoryTitleList.contains(CategoryTitle.CHEMICAL_COMPOUNDS);
		boolean hasMaterialsCategory = categoryTitleList.contains(CategoryTitle.MATERIALS);
		boolean hasExplosivesCategory = categoryTitleList.contains(CategoryTitle.EXPLOSIVES);
		boolean isAmongListedExplosives = materialsAndSubstancesDetectorService.isExplosive(pageTitle);

		if (hasExplosivesCategory && !isAmongListedExplosives && !hasMaterialsCategory && !hasChemicalCompoundsCategory) {
			log.info("Page \"{}\" has explosives category, but no related categories, and is not listed among explosives", pageTitle);
			return null;
		}

		material.setBiochemicalCompound(categoryTitleList.contains(CategoryTitle.BIOCHEMICAL_COMPOUNDS));
		material.setDrug(categoryTitleList.contains(CategoryTitle.DRUGS));
		material.setPoisonousSubstance(categoryTitleList.contains(CategoryTitle.POISONOUS_SUBSTANCES));
		material.setChemicalCompound(hasChemicalCompoundsCategory || material.getBiochemicalCompound() || material.getDrug()
				|| material.getPoisonousSubstance());
		material.setExplosive(hasExplosivesCategory || isAmongListedExplosives);
		material.setPreciousMaterial(materialsAndSubstancesDetectorService.isPreciousMaterial(pageTitle));
		material.setGemstone(material.getPreciousMaterial() || categoryTitleList.contains(CategoryTitle.GEMSTONES));
		material.setAlloyOrComposite(materialsAndSubstancesDetectorService.isAlloyOrComposite(pageTitle));
		material.setFuel(materialsAndSubstancesDetectorService.isFuel(pageTitle));
		material.setMineral(templateFinder.hasTemplate(item, TemplateTitle.MINERALS));

		return material;
	}

}
