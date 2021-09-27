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

import eu.smesec.library.translationtool.Extractor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.File;

/**
 * Mojo providing the 'extract-translations' goal.
 *
 * @author Matthias Luppi
 */
@Mojo(name = "extract-translations", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ExtractTranslationsMojo extends AbstractMojo {

    /**
     * The original coach file (XML)
     */
    @Parameter(property = "coachXml", required = true)
    private File coachXml;

    /**
     * Destination of the translation source file (XLF)
     */
    @Parameter(property = "outputXlf", required = true)
    private File outputXlf;

    /**
     * Run extractor
     *
     * @throws MojoExecutionException if an unexpected problem occurs (results in a "BUILD ERROR")
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (coachXml == null) {
            throw new MojoExecutionException("Parameter 'coachXml' is not defined");
        }
        if (outputXlf == null) {
            throw new MojoExecutionException("Parameter 'outputXlf' is not defined");
        }

        final Extractor extractor = new Extractor(coachXml.toPath(), outputXlf.toPath());
        try {
            extractor.extract();
        } catch (Exception e) {
            throw new MojoExecutionException("Problem while extracting translation source", e);
        }
    }
}
