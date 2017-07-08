package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar;

import com.afrozaar.wordpress.wpapi.v2.model.Content;
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

		Content content = input.getContent();
		if (content != null) {
			page.setRawContent(content.getRaw());
			page.setRenderedContent(content.getRendered());
		}

		return page;
	}

}
