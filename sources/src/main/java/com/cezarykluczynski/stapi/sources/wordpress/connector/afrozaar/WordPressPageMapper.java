package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.model.Content;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class WordPressPageMapper {

	public Page map(com.afrozaar.wordpress.wpapi.v2.model.Page input) {
		if (input == null) {
			return null;
		}

		Page page = new Page();
		page.setId(input.getId());
		page.setSlug(input.getSlug());

		Title title = input.getTitle();

		if (title != null) {
			page.setRawTitle(title.getRaw());
			page.setRenderedTitle(title.getRendered());
		}

		Content content = input.getContent();
		if (content != null) {
			page.setRawContent(content.getRaw());
			page.setRenderedContent(content.getRendered());
		}

		return page;
	}

}
