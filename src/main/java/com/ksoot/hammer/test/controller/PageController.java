package com.ksoot.hammer.test.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ksoot.framework.common.boot.config.pagination.PaginatedResource;
import com.ksoot.framework.common.boot.config.pagination.PaginatedResourceAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(description = "<b>Endpoint for config testing</b>")
//@ConditionalOnBean(name = PaginationAutoConfiguration.PAGINATED_RESOURCE_ASSEMBLER_BEAN_NAME)
@RestController
@RequestMapping("/api")
public class PageController {

	@Autowired
	private PaginatedResourceAssembler pageAssembler;

	@GetMapping("/albums")
	// @formatter:off
	@ApiOperation(nickname = "get-albums-page", consumes = APPLICATION_JSON_VALUE,
		produces = APPLICATION_JSON_VALUE,
		value = "Gets a page of Albums matching the selection filters and sort criteria",
		notes = "", response = PaginatedResource.class
	)
	public ResponseEntity<PaginatedResource<Album>> getAllAlbums(
			@ApiParam(name = "artist", value = "Artist Name, ex. Sidhu Mooseaala, Babbu Maan", example = "Sidhu Mooseaala")
			@RequestParam(value = "artist", required = false) final String artist,
			@ApiIgnore @PageableDefault(page = 0, size = 2, sort = {
					"title" }, direction = Sort.Direction.ASC) final Pageable pageable) {
		// @formatter:on

		Page<Album> albums = this.findAlbums(artist, pageable);

		if (albums.isEmpty()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-info", "No Albums found matching given filters");
			return ResponseEntity.ok().headers(headers).build();
		}
		else {
			return ResponseEntity.ok(this.pageAssembler.assemble(albums));
		}
	}

	private Page<Album> findAlbums(final String artist, final Pageable pageable) {
		List<Album> albums = new ArrayList<>();
		
		OffsetDateTime now = OffsetDateTime.now();
		int pageSize = pageable.getPageSize();
		int pageNo = pageable.getPageNumber();
		int start = pageSize * pageNo;
		for(int i = start; i < start+pageSize; i++) {
			albums.add(Album.of(i, "Title " + i, "Description " + i, "Artist " + i, now));
		}
		return new PageImpl<>(albums, pageable, pageSize * 4);
	}
	
	@AllArgsConstructor(staticName = "of")
	@Getter
	@ToString
	private static class Album {
		
		private int id;
		
		private String title;
		
		private String description;
		
		private String artist;
		
		private OffsetDateTime releaseDate;
	}
}
