package com.cezarykluczynski.stapi.etl.platform.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlatformCodeToNameMapper {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put("3do", "3DO Interactive Multiplayer");
		MAPPINGS.put("arcade", "Arcade");
		MAPPINGS.put("amiga", "Commodore Amiga");
		MAPPINGS.put("atari-st", "Atari ST");
		MAPPINGS.put("atari2600", "Atari 2600");
		MAPPINGS.put("atari5200", "Atari 5200");
		MAPPINGS.put("browser", "Web Browsers");
		MAPPINGS.put("c64", "Commodore 64");
		MAPPINGS.put("gameboy", "Nintendo Game Boy");
		MAPPINGS.put("dos", "DOS");
		MAPPINGS.put("apple2", "Apple II");
		MAPPINGS.put("macos", "Mac OS Classic");
		MAPPINGS.put("osx", "Mac OS X");
		MAPPINGS.put("microvision", "Microvision");
		MAPPINGS.put("nes", "Nintendo Entertainment System");
		MAPPINGS.put("ds", "Nintendo DS");
		MAPPINGS.put("psx", "PlayStation");
		MAPPINGS.put("ps2", "PlayStation 2");
		MAPPINGS.put("ps3", "PlayStation 3");
		MAPPINGS.put("ps4", "PlayStation 4");
		MAPPINGS.put("psp", "PlayStation Portable");
		MAPPINGS.put("psvr", "PlayStation VR");
		MAPPINGS.put("snes", "Super Nintendo Entertainment System");
		MAPPINGS.put("32x", "Sega 32X");
		MAPPINGS.put("gamegear", "Sega Game Gear");
		MAPPINGS.put("genesis", "Sega Genesis/Mega Drive");
		MAPPINGS.put("vectrex", "Vectrex");
		MAPPINGS.put("wii", "Nintendo Wii");
		MAPPINGS.put("wiiu", "Nintendo Wii U");
		MAPPINGS.put("win", "Windows");
		MAPPINGS.put("win9x", "Windows 9x; ME");
		MAPPINGS.put("winxp", "Windows 2000 or XP (NT5+)");
		MAPPINGS.put("win7", "Windows Vista or 7 (DX10+)");
		MAPPINGS.put("win8", "Windows 8");
		MAPPINGS.put("win10", "Windows 10");
		MAPPINGS.put("xbox", "Xbox");
		MAPPINGS.put("360", "Xbox 360");
		MAPPINGS.put("xbone", "Xbox One");
		MAPPINGS.put("cell", "Cell phones");
		MAPPINGS.put("bb", "Blackberry");
		MAPPINGS.put("ios", "iOS");
		MAPPINGS.put("droid", "Android");
		MAPPINGS.put("linux", "Linux");
		MAPPINGS.put("rift", "Oculus Rift");
		MAPPINGS.put("vive", "HTC Vive");
	}


	public String map(String code) {
		return MAPPINGS.getOrDefault(code, null);
	}

}
