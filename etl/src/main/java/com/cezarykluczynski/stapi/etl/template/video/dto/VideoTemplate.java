package com.cezarykluczynski.stapi.etl.template.video.dto;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class VideoTemplate {

	private String title;

	private Page page;

	private Boolean amazonDigitalRelease;

	private Boolean dailymotionDigitalRelease;

	private Boolean googlePlayDigitalRelease;

	@SuppressWarnings("memberName")
	private Boolean iTunesDigitalRelease;

	private Boolean ultraVioletDigitalRelease;

	private Boolean vimeoDigitalRelease;

	private Boolean vuduDigitalRelease;

	private Boolean xboxSmartGlassDigital;

	private Boolean youTubeDigitalRelease;

	private Boolean netflixDigitalRelease;

}
