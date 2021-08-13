package eu.smesec.maven.coachplugin;

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
