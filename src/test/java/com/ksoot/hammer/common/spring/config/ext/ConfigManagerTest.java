/**Copyright 2020 the original author or authors.**Licensed under the Apache License,Version 2.0(the"License");*you may not use this file except in compliance with the License.*You may obtain a copy of the License at**https://www.apache.org/licenses/LICENSE-2.0
**Unless required by applicable law or agreed to in writing,software*distributed under the License is distributed on an"AS IS"BASIS,*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.*See the License for the specific language governing permissions and*limitations under the License.*/

package com.ksoot.hammer.common.spring.config.ext;

import java.util.Arrays;

import com.ksoot.framework.common.boot.config.SpringProfiles;
import com.ksoot.framework.common.boot.config.external.ConfigManager;
import com.ksoot.framework.common.boot.config.external.ExternalConfigProperties;
import com.ksoot.framework.common.boot.config.external.ExternalPropertiesAutoConfiguration;
import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/***
 * @author Rajveer Singh
 */
@SpringBootTest
@ActiveProfiles(profiles = SpringProfiles.LOCAL)
@ContextConfiguration(classes = { ExternalPropertiesAutoConfiguration.class },
		locations = { "classpath:config//external-conf.xml" })
@EnableConfigurationProperties(ExternalConfigProperties.class)
class ConfigManagerTest {

	@Autowired
	private ConfigManager configManager;

	@Test
	void testGetSystemPropSpel() {
		System.out.println(this.configManager.getString("test.prop.system.spel"));
	}

	@Test
	void testGetEnum() {
		System.out.println(this.configManager.getEnum("test.prop.enum", CountryCode.class));
	}

	@Test
	void testGetList() {
		System.out.println(this.configManager.getList("test.prop.list.string"));
		System.out.println(this.configManager.getList("test.prop.list.float", Float.class));
	}

	@Test
	void testGetArray() {
		System.out.println(Arrays.asList(this.configManager.getStringArray("test.prop.array")));
	}

	@Test
	void testSpelExpression() {
		System.out.println(this.configManager.getInt("test.prop.spel.expression"));
	}

}
