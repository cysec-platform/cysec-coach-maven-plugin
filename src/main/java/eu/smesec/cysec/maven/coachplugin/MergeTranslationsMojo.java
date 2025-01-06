/*-
 * #%L
 * Coach Maven Plugin
 * %%
 * Copyright (C) 2021 - 2025 FHNW (University of Applied Sciences and Arts Northwestern Switzerland)
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

import eu.smesec.cysec.maven.coachplugin.translation.LanguageConfig;
import eu.smesec.cysec.maven.coachplugin.translation.LanguageConfigParser;
import eu.smesec.cysec.translationtool.Merger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Mojo providing the 'merge-translations' goal.
 *
 * @author Matthias Luppi
 */
@Mojo(name = "merge-translations", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class MergeTranslationsMojo extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(MergeTranslationsMojo.class);

    /**
     * The original coach file
     */
    @Parameter(property = "coachXml", required = true)
    private File coachXml;

    /**
     * Directory with translations (e.g. de.xlf)
     */
    @Parameter(property = "xlfBaseDir", required = true)
    private File xlfBaseDir;

    /**
     * Base directory for the translated coaches (sub-folders will be created, e.g. outputBaseDir/de/coach.xml)
     */
    @Parameter(property = "outputBaseDir", required = true)
    private File outputBaseDir;

    /**
     * Language configuration file for building multilingual coaches
     */
    @Parameter(property = "languageConfigFile", required = true)
    private File languageConfigFile;

    /**
     * Flag to make the run fail if there are missing translations
     */
    @Parameter(property = "failOnMissingTranslation", defaultValue = "false")
    private boolean failOnMissingTranslation;

    /**
     * Run merge
     *
     * @throws MojoExecutionException if an unexpected problem occurs (results in a "BUILD ERROR")
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (coachXml == null) {
            throw new MojoExecutionException("Parameter 'coachXml' is not defined");
        }
        if (xlfBaseDir == null) {
            throw new MojoExecutionException("Parameter 'xlfBaseDir' is not defined");
        }
        if (outputBaseDir == null) {
            throw new MojoExecutionException("Parameter 'outputBaseDir' is not defined");
        }
        if (languageConfigFile == null) {
            throw new MojoExecutionException("Parameter 'languageConfigFile' is not defined");
        }

        final List<LanguageConfig> languages = LanguageConfigParser.parse(languageConfigFile.toPath());
        boolean allTranslationsFound = true;
        for (LanguageConfig language : languages) {
            if (language.isMerge()) {
                final Path xlfFile = xlfBaseDir.toPath().resolve(language.getCode() + ".xlf");
                final Path outputFile = outputBaseDir.toPath().resolve(language.getCode()).resolve(coachXml.getName());
                final Merger merger = new Merger(coachXml.toPath(), xlfFile, language.getCode(), outputFile);
                try {
                    allTranslationsFound &= merger.merge();
                } catch (Exception e) {
                    throw new MojoExecutionException("Problem while merging translation for '" + language.getCode(), e);
                }
            }
        }
        if (!allTranslationsFound) {
            if (failOnMissingTranslation) {
                throw new MojoExecutionException("Not all translations found and 'failOnMissingTranslation=true'");
            } else {
                log.warn("Not all translations found but continuing the build (configure 'failOnMissingTranslation=true' to make the build fail)");
            }
        } else {
            log.info("All required translations found and merged");
        }
    }
}
