package com.cezarykluczynski.stapi.etl.platform.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlatformCodeToNameMapper {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put("32x", "Sega 32X");
		MAPPINGS.put("360", "Xbox 360");
		MAPPINGS.put("3do", "3DO Interactive Multiplayer");
		MAPPINGS.put("arcade", "Arcade");
		MAPPINGS.put("amiga", "Commodore Amiga");
		MAPPINGS.put("apple2", "Apple II");
		MAPPINGS.put("atari-st", "Atari ST");
		MAPPINGS.put("atari2600", "Atari 2600");
		MAPPINGS.put("atari5200", "Atari 5200");
		MAPPINGS.put("bb", "Blackberry");
		MAPPINGS.put("browser", "Web Browsers");
		MAPPINGS.put("c64", "Commodore 64");
		MAPPINGS.put("cell", "Cell phones");
		MAPPINGS.put("dos", "DOS");
		MAPPINGS.put("droid", "Android");
		MAPPINGS.put("ds", "Nintendo DS");
		MAPPINGS.put("gameboy", "Nintendo Game Boy");
		MAPPINGS.put("gamegear", "Sega Game Gear");
		MAPPINGS.put("genesis", "Sega Genesis/Mega Drive");
		MAPPINGS.put("ios", "iOS");
		MAPPINGS.put("linux", "Linux");
		MAPPINGS.put("macos", "Mac OS Classic");
		MAPPINGS.put("microvision", "Microvision");
		MAPPINGS.put("nes", "Nintendo Entertainment System");
		MAPPINGS.put("osx", "Mac OS X");
		MAPPINGS.put("psx", "PlayStation");
		MAPPINGS.put("ps2", "PlayStation 2");
		MAPPINGS.put("ps3", "PlayStation 3");
		MAPPINGS.put("ps4", "PlayStation 4");
		MAPPINGS.put("ps5", "PlayStation 5");
		MAPPINGS.put("psp", "PlayStation Portable");
		MAPPINGS.put("psvr", "PlayStation VR");
		MAPPINGS.put("rift", "Oculus Rift");
		MAPPINGS.put("snes", "Super Nintendo Entertainment System");
		MAPPINGS.put("stadia", "Stadia");
		MAPPINGS.put("steam", "Steam");
		MAPPINGS.put("switch", "Nintendo Switch");
		MAPPINGS.put("vectrex", "Vectrex");
		MAPPINGS.put("vive", "HTC Vive");
		MAPPINGS.put("wii", "Nintendo Wii");
		MAPPINGS.put("wiiu", "Nintendo Wii U");
		MAPPINGS.put("win", "Windows");
		MAPPINGS.put("win9x", "Windows 9x; ME");
		MAPPINGS.put("winxp", "Windows 2000 or XP (NT5+)");
		MAPPINGS.put("win7", "Windows Vista or 7 (DX10+)");
		MAPPINGS.put("win8", "Windows 8");
		MAPPINGS.put("win10", "Windows 10");
		MAPPINGS.put("win11", "Windows 11");
		MAPPINGS.put("xbone", "Xbox One");
		MAPPINGS.put("xbox", "Xbox");
		MAPPINGS.put("xbs", "Xbox Series X/S");
	}


	public String map(String code) {
		return MAPPINGS.getOrDefault(code, null);
	}

}
