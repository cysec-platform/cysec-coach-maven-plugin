/*-
 * #%L
 * Coach Maven Plugin
 * %%
 * Copyright (C) 2021 - 2022 FHNW (University of Applied Sciences and Arts Northwestern Switzerland)
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

import eu.smesec.cysec.flatifier.Flatifier;
import eu.smesec.cysec.maven.coachplugin.translation.LanguageConfig;
import eu.smesec.cysec.maven.coachplugin.translation.LanguageConfigParser;
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
 * Mojo providing the 'flatify' goal.
 *
 * @author Matthias Luppi
 */
@Mojo(name = "flatify", defaultPhase = LifecyclePhase.PACKAGE)
public class FlatifierMojo extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(FlatifierMojo.class);

    /**
     * Path to the folder containing the coach and its resources
     */
    @Parameter(property = "inputDirectory", defaultValue = "${project.build.directory}/flatify-input")
    private File inputDirectory;

    /**
     * Destination of the flattened XML file
     */
    @Parameter(property = "outputFile", defaultValue = "${project.build.directory}/${project.artifactId}.xml")
    private File outputFile;

    /**
     * Language configuration file for building multilingual coaches
     */
    @Parameter(property = "languageConfigFile")
    private File languageConfigFile;

    /**
     * Run flatifier
     *
     * @throws MojoExecutionException if an unexpected problem occurs (results in a "BUILD ERROR")
     */
    @Override
    public void execute() throws MojoExecutionException {
        if (inputDirectory == null) {
            throw new MojoExecutionException("Parameter 'inputDirectory' is not defined");
        }
        if (outputFile == null) {
            throw new MojoExecutionException("Parameter 'outputFile' is not defined");
        }
        try {
            if (languageConfigFile != null) {
                log.info("Using multilingual flatifying process");
                executeMultiLanguage();
            } else {
                executeSingleLanguage();
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Problem while flatifying", e);
        }
    }

    private void executeSingleLanguage() throws Exception {
        final Flatifier flatifier = new Flatifier(inputDirectory.toPath(), outputFile.toPath());
        flatifier.flatify();
    }

    private void executeMultiLanguage() throws Exception {
        final List<LanguageConfig> languages = LanguageConfigParser.parse(languageConfigFile.toPath());

        final Path baseInputDir = inputDirectory.toPath();
        log.info("Using inputDirectory as base path for multilingual process: '{}'", inputDirectory);

        // process original
        final Path originalCoachDir = baseInputDir.resolve("original");
        final Flatifier of = new Flatifier(originalCoachDir, outputFile.toPath());
        of.flatify();

        // process languages
        final Path outputDir = outputFile.toPath().getParent();
        log.info("Using parent of outputFile as base path for translated coaches: '{}'", outputDir);
        for (final LanguageConfig language : languages) {
            if (language.isFlatify()) {
                final String fileName = getTranslatedCoachFileName(outputFile, language);
                final Flatifier tf = new Flatifier(
                        baseInputDir.resolve(language.getCode()),
                        outputDir.resolve(fileName),
                        originalCoachDir
                );
                tf.flatify();
            }
        }
    }

    private static String getTranslatedCoachFileName(final File mainCoachOutputFile, final LanguageConfig language) {
        return mainCoachOutputFile.getName().replace(".xml", "-" + language.getCode() + ".xml");
    }
}
