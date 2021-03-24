package be.rommens.scraper.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * User : cederik
 * Date : 31/03/2020
 * Time : 14:23
 */
@ConfigurationProperties(prefix = "providers")
public class ProviderProperties {

    private Map<String, String> url;

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }
}
