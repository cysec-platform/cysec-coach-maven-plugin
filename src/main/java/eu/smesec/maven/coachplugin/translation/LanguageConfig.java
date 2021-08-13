package eu.smesec.maven.coachplugin.translation;

/**
 * Represents a single language configuration
 *
 * @author Matthias Luppi
 */
public class LanguageConfig {

    private final String code;
    private final boolean merge;
    private final boolean flatify;

    public LanguageConfig(final String code, final boolean merge, final boolean flatify) {
        this.code = code;
        this.merge = merge;
        this.flatify = flatify;
    }

    public String getCode() {
        return code;
    }

    public boolean isMerge() {
        return merge;
    }

    public boolean isFlatify() {
        return flatify;
    }

    @Override
    public String toString() {
        return "{code=" + code + ", " + "merge=" + merge + ", flatify=" + flatify + "}";
    }
}
