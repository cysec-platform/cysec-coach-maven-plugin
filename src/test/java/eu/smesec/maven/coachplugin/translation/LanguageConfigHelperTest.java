package eu.smesec.maven.coachplugin.translation;

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
