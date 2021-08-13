package eu.smesec.maven.coachplugin;

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
