package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoReleaseFormatProcessor implements ItemProcessor<String, VideoReleaseFormat> {

	@Override
	@SuppressWarnings("returnCount")
	public VideoReleaseFormat process(String item) throws Exception {
		if (item == null) {
			log.info("null value passed to VideoReleaseFormatProcessor");
			return null;
		}

		switch (item) {
			case "":
				return null;
			case "s8":
				return VideoReleaseFormat.SUPER_8;
			case "bm":
				return VideoReleaseFormat.BETAMAX;
			case "vhs":
			case "usvhs":
			case "ukvhs":
				return VideoReleaseFormat.VHS;
			case "ced":
				return VideoReleaseFormat.CED;
			case "ld":
				return VideoReleaseFormat.LD;
			case "vhd":
				return VideoReleaseFormat.VHD;
			case "vcd":
				return VideoReleaseFormat.VCD;
			case "v8":
				return VideoReleaseFormat.VIDEO_8;
			case "dvd":
				return VideoReleaseFormat.DVD;
			case "umd":
				return VideoReleaseFormat.UMD;
			case "hddvd":
				return VideoReleaseFormat.HD_DVD;
			case "bd":
				return VideoReleaseFormat.BLU_RAY;
			case "4kuhd":
				return VideoReleaseFormat.BLU_RAY_4K_UHD;
			case "df":
				return VideoReleaseFormat.DIGITAL_FORMAT;
			default:
				break;
		}

		log.warn("Unknown value {} passed to VideoReleaseFormatProcessor", item);
		return null;
	}

}
