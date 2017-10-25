package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.DocumentType;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DocumentationReader {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DocumentationReader.class);

	List<DocumentDTO> readDirectory(String path) {
		List<DocumentDTO> documentDTOList = Lists.newArrayList();
		privateReadDocument(path, documentDTOList, null);
		documentDTOList = filterAndSort(documentDTOList);
		return documentDTOList;
	}

	private List<DocumentDTO> filterAndSort(List<DocumentDTO> documentDTOList) {
		Set<String> paths = Sets.newHashSet();
		return documentDTOList.stream()
				.filter(documentDTO -> {
					if (paths.contains(documentDTO.getPath())) {
						return false;
					}

					paths.add(documentDTO.getPath());
					return true;
				})
				.sorted(Comparator.comparing(DocumentDTO::getPath))
				.collect(Collectors.toList());
	}

	private void privateReadDocument(String path, List<DocumentDTO> documentDTOList, Path basePath) {
		try {
			Path currentPath = Paths.get(path);
			Path rootBasePath = basePath == null ? currentPath : basePath;
			Stream<Path> paths = Files.walk(currentPath);
			paths.forEach(filePath -> {
				try {
					if (Files.isSameFile(currentPath, filePath)) {
						return;
					}
				} catch (Exception e) {
					// do nothing
				}
				if (Files.isRegularFile(filePath)) {
					DocumentDTO documentDTO;

					try {
						documentDTO = fromFile(filePath, rootBasePath);
					} catch (StapiRuntimeException e) {
						return;
					}

					documentDTOList.add(documentDTO);
				} else if (Files.isDirectory(filePath)) {
					privateReadDocument(filePath.toAbsolutePath().toString(), documentDTOList, rootBasePath);
				}
			});
		} catch (Exception e) {
			LOG.error("Exception while reading directory {}, exception was:", path, e);
		}
	}

	private DocumentDTO fromFile(Path filePath, Path rootBasePath) {
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setType(filePath.toString().endsWith(".yaml") ? DocumentType.YAML : DocumentType.XML);
		try {
			documentDTO.setContent(StringUtils.join(Files.readAllLines(filePath, Charset.forName("UTF-8")), "\n"));
		} catch (Exception e) {
			LOG.error("Could not get content for file {}, exception was: {}", filePath.toString(), e);
		}
		try {
			documentDTO.setPath(rootBasePath.relativize(filePath).toString());
		} catch (IllegalArgumentException e) {
			throw new StapiRuntimeException(e);
		}

		return documentDTO;
	}

}
