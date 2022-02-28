package com.ksoot.hammer.test.controller;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksoot.framework.common.error.ApplicationProblem;
import com.ksoot.framework.common.error.resolver.GeneralErrorResolver;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

	// @Autowired
	// private Configuration configuration;

	@Autowired
	private ApplicationContext context;

	// @Value("${test.prop.xml.exclusive}")
	private String injectedValue;

	// @Qualifier("myEnvironment")
	@Autowired
	private Environment environment;

	@Autowired
	private MessageSource messageSource;

	// @Qualifier(value = "xml-properties")
	// @Autowired
	// private List<Properties> xmlProperties;
	//
	// @Qualifier(value = "externalProperties")
	@Autowired
	private Map<String, Properties> externalProperties;

	private boolean throwException = true;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/findByKey")
	@ApiOperation(nickname = "get-config", value = "Gets config value by supplied key", notes = "",
			response = String.class)
	public ResponseEntity<String> getConfig(@RequestParam(name = "key", required = true) final String key)
			throws JsonProcessingException {

		// System.out.println("###############################################################");
		// System.out.println(ErrorHelper.currentRequest());
		// System.out.println(ErrorHelper.getUriTemplate());
		// System.out.println(ErrorHelper.getInstance());
		// System.out.println("###############################################################");
		System.out.println(this.objectMapper.writeValueAsString(OffsetDateTime.now()));

		if (this.throwException) {
			throw new IllegalStateException("Illegal state exception thrown for testing");
			// throw new ApplicationProblem(GeneralErrorResolver.INTERNAL_SERVER_ERROR);
		}
		// Object[] args = { "TTT" };
		// System.out.println(
		// "message -->" + this.messageSource.getMessage("placeholder.test.message", args,
		// Locale.ENGLISH));
		System.out.println(
				"message -->" + this.messageSource.getMessage("placeholder.test.message", null, Locale.ENGLISH));
		System.out.println("injectedValue -->" + this.injectedValue);
		// System.out.println("key -->" + factory.get);
		// System.out.println(this.derivedProperties);
		// return ResponseEntity.ok(this.configuration.getString(key, "No value found for
		// key: " + key));
		// Properties properties = this.factory.getBean("derivedProperties",
		// Properties.class);
		// List<String> value = this.context.getBean("emails", java.util.List.class);
		// List<String> value = this.environment.getProperty("list.prop", List.class);
		String value = this.environment.getProperty(key);
		System.out.println("??????  value --->>" + value);
		return ResponseEntity.ok(this.environment.getProperty(key));
	}

	@GetMapping("/pagination")
	@ApiOperation(nickname = "pagination", value = "Example API to check pagination request parameters", notes = "",
			response = Void.class)
	public void paginationParametersTest(final Pageable pageable) {
		System.out.println("pageable -->" + pageable);
	}
	
	// public void paginationParametersTest(@PageableDefault(page = 0, size = 12, sort = {
	// "root.prop" },
	// direction = Sort.Direction.ASC) final Pageable pageable) {
	// System.out.println("pageable -->" + pageable);
	// }

	@GetMapping("/testApplicationProblem")
	public String testProblem() {
//		throw new IllegalStateException("Illegal state exception thrown for testing");
		throw new ApplicationProblem(GeneralErrorResolver.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/testThrowable")
	public String testThrowable() {
		throw new IllegalCallerException("Illegal caller", 
				new IllegalStateException("Illegal state exception thrown for testing"));
//		throw new IllegalStateException("Illegal state exception thrown for testing");
//		throw new ApplicationProblem(GeneralErrorResolver.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/dateTimeString")
	public String testOffsetDateTimeString() {
		return OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	@GetMapping("/dateTime")
	public OffsetDateTime testOffsetDateTime() {
		return OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	@GetMapping("/dateTimeNested")
	public DateTimeWrapper testOffsetDateTimeWrapped() {
		return new DateTimeWrapper();
	}
	
	@Data
	private static class DateTimeWrapper {
		private OffsetDateTime now = OffsetDateTime.now();
	}
}
