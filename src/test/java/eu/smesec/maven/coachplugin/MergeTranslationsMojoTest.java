package eu.smesec.maven.coachplugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test the 'merge-translations' goal of the {@link MergeTranslationsMojo}.
 *
 * @author Matthias Luppi
 */
public class MergeTranslationsMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testMergeTranslations() throws Exception {
        Files.createDirectories(Paths.get("target", "test-output-mojo-merge-translations"));

        final String pom = "src/test/resources/merge-translations-testproject/pom.xml";
        final File pomFile = new File(pom);
        assertNotNull(pomFile);
        assertTrue(pomFile.exists());

        final MergeTranslationsMojo mergeTranslationsMojo = (MergeTranslationsMojo) rule.lookupMojo("merge-translations", pom);
        assertNotNull(mergeTranslationsMojo);
        mergeTranslationsMojo.execute();
    }

    @Test
    public void testMergeTranslationsFailOnMissingTranslation() throws Exception {
        Files.createDirectories(Paths.get("target", "test-output-mojo-merge-translations"));

        final String pom = "src/test/resources/merge-translations-fail-testproject/pom.xml";
        final File pomFile = new File(pom);
        assertNotNull(pomFile);
        assertTrue(pomFile.exists());

        final MergeTranslationsMojo mergeTranslationsMojo = (MergeTranslationsMojo) rule.lookupMojo("merge-translations", pom);
        assertNotNull(mergeTranslationsMojo);
        assertThrows(MojoExecutionException.class, mergeTranslationsMojo::execute);
    }

}
