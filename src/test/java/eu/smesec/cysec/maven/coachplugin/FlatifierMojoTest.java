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
 * Test the 'flatify' goal of the {@link FlatifierMojo}.
 *
 * @author Matthias Luppi
 */
public class FlatifierMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testFlatify() throws Exception {
        Files.createDirectories(Paths.get("target", "test-output-flatifier-mojo"));

        final String pom = "src/test/resources/flatifier-testproject/pom.xml";
        final File pomFile= new File(pom);
        assertNotNull(pomFile);
        assertTrue(pomFile.exists());

        final FlatifierMojo flatifierMojo = (FlatifierMojo) rule.lookupMojo("flatify", pom);
        assertNotNull(flatifierMojo);
        flatifierMojo.execute();
    }

    @Test
    public void testFlatifyMultilingual() throws Exception {
        Files.createDirectories(Paths.get("target", "test-output-flatifier-mojo-multi"));

        final String pom = "src/test/resources/flatifier-multilingual-testproject/pom.xml";
        final File pomFile= new File(pom);
        assertNotNull(pomFile);
        assertTrue(pomFile.exists());

        final FlatifierMojo flatifierMojo = (FlatifierMojo) rule.lookupMojo("flatify", pom);
        assertNotNull(flatifierMojo);
        flatifierMojo.execute();
    }

}
