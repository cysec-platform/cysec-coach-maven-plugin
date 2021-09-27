/*-
 * #%L
 * Coach Maven Plugin
 * %%
 * Copyright (C) 2021 FHNW (University of Applied Sciences and Arts Northwestern Switzerland)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package eu.smesec.cysec.maven.coachplugin.translation;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link LanguageConfigHelper}
 *
 * @author Matthias Luppi
 */
public class LanguageConfigHelperTest {

    @Test
    public void testParse() {
        final Path configFile = Paths.get("src/test/resources/config-helper-test.xml");
        final List<LanguageConfig> languages = LanguageConfigHelper.parse(configFile);

        assertEquals(2, languages.size());
        assertEquals("de", languages.get(0).getCode());
        assertTrue(languages.get(0).isMerge());
        assertTrue(languages.get(0).isFlatify());
        assertEquals("fr", languages.get(1).getCode());
        assertFalse(languages.get(1).isMerge());
        assertFalse(languages.get(1).isFlatify());
    }
}
