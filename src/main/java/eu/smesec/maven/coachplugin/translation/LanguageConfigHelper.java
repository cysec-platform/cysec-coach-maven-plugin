package eu.smesec.maven.coachplugin.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper to parse a language configuration file
 *
 * @author Matthias Luppi
 */
public class LanguageConfigHelper {

    private static final Logger log = LoggerFactory.getLogger(LanguageConfigHelper.class);

    /**
     * Parses a language configuration file
     *
     * @param configFile path to the language configuration file in XML-format
     * @return list with a {@link LanguageConfig} per language
     */
    public static List<LanguageConfig> parse(final Path configFile) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document doc = db.parse(configFile.toFile());

            final List<LanguageConfig> languages = new ArrayList<>();

            final NodeList nodes = doc.getElementsByTagName("language");
            for (int i = 0; i < nodes.getLength(); i++) {
                final Element language = (Element) nodes.item(i);
                languages.add(new LanguageConfig(
                        language.getFirstChild().getNodeValue(),
                        Boolean.parseBoolean(language.getAttribute("merge")),
                        Boolean.parseBoolean(language.getAttribute("flatify"))
                ));
            }
            log.info("Parsed language configuration: {}", languages);
            return languages;
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not parse configuration file", e);
        }
    }

    private LanguageConfigHelper() {
    }

}
