# Scraper

## How to add a new scraper provider ?

First add the url of the site to scrape in `provider.properties`:
```
providers.url.example=http://localhost/
```

Under *provider* package, add a new package (for example **Example**).
The scraper can look like this :
````java
public class ExampleScraper extends AbstractScraper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleScraper.class);

    public ExampleScraper(ScrapingConfig scrapingConfig) {
        super(scrapingConfig);
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic/" + technicalComicName;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic" + technicalComicName + "/issue/" + issue;
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) {
        ScrapedComic scrapedComic = new ScrapedComic();
        scrapedComic.setTitle(scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL) );
        return scrapedComic;
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        return null;
    }

    @Override
    public byte[] downloadPage(String url) throws FileNotFoundException {
        return new byte[0];
    }
}
````

Then add this provider to the enum `Provder`:
````java
EXAMPLE("example", ExampleScraper.class)
````
Make sure that the property (in this case `"example"` is the same as the name of the property).

In *ScraperAutoConfiguration*, add a new bean :
````java
@Bean
@ConditionalOnMissingBean(ExampleScraper.class)
public Scraper exampleScraper() {
    ScrapingConfig config = new ScrapingConfig();
    config.put(ScrapingConfigParams.BASE_URL, providerProperties.getUrl().get(Provider.EXAMPLE.getPropertyName()));
    return new ExampleScraper(config);
}
````

> Do not forget to add tests for this scraper in `ScraperAutoConfigurationTest` and `ScraperFactoryTest`