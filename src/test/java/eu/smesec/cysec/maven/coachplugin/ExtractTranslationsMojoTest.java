/*-
 * #%L
 * Coach Maven Plugin
 * %%
 * Copyright (C) 2021 - 2024 FHNW (University of Applied Sciences and Arts Northwestern Switzerland)
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
package eu.smesec.cysec.maven.coachplugin;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the 'extract-translations' goal of the {@link ExtractTranslationsMojo}.
 *
 * @author Matthias Luppi
 */
public class ExtractTranslationsMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testExtractTranslations() throws Exception {
        Files.createDirectories(Paths.get("target", "test-output-mojo-extract-translations"));

        final String pom = "src/test/resources/extract-translations-testproject/pom.xml";
        final File pomFile= new File(pom);
        assertNotNull(pomFile);
        assertTrue(pomFile.exists());

        final ExtractTranslationsMojo extractTranslationsMojo = (ExtractTranslationsMojo) rule.lookupMojo("extract-translations", pom);
        assertNotNull(extractTranslationsMojo);
        extractTranslationsMojo.execute();
    }

}
