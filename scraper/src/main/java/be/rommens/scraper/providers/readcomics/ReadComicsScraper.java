package be.rommens.scraper.providers.readcomics;

import static be.rommens.scraper.utils.CollectionUtils.getOnlyElement;

import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssue;
import be.rommens.scraper.api.models.ScrapedIssueDetails;
import be.rommens.scraper.core.AbstractScraper;
import be.rommens.scraper.core.ScrapingConfig;
import be.rommens.scraper.core.ScrapingConfigParams;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 13:53
 */
public class ReadComicsScraper extends AbstractScraper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadComicsScraper.class);

    private static final Map<String, Month> MONTHS = Map.ofEntries(
            Map.entry("JAN", Month.JANUARY),
            Map.entry("FEB", Month.FEBRUARY),
            Map.entry("MAR", Month.MARCH),
            Map.entry("APR", Month.APRIL),
            Map.entry("MAY", Month.MAY),
            Map.entry("JUN", Month.JUNE),
            Map.entry("JUL", Month.JULY),
            Map.entry("AUG", Month.AUGUST),
            Map.entry("SEP", Month.SEPTEMBER),
            Map.entry("OCT", Month.OCTOBER),
            Map.entry("NOV", Month.NOVEMBER),
            Map.entry("DEC", Month.DECEMBER));

    public ReadComicsScraper(ScrapingConfig config) {
        super(config);
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) {
        try {
            Document source = getSource(buildUrlForComic(technicalComicName));
            ScrapedComic scrapedComic = new ScrapedComic();

            String title =
                source.getElementsByClass("listmanga-header").stream()
                    .map(Element::text).filter(text -> !StringUtils.contains(text.trim(), "Chapter"))
                    .findFirst()
                .orElse(null);
            scrapedComic.setTitle(title);

            Elements cover = source.select("img[src*=cover]");
            if (cover != null) {
                scrapedComic.setCover(cover.attr("src"));
            }

            String type = findTextOfSiblingOfElementByTagAndText(source, "dt", "Type");
            scrapedComic.setPublisher(type);

            String dateOfRelease = findTextOfSiblingOfElementByTagAndText(source, "dt", "Date of release");
            scrapedComic.setDateOfRelease(dateOfRelease);

            String status = findTextOfSiblingOfElementByTagAndText(source, "dt", "Status");
            scrapedComic.setStatus(status);

            String author = findTextOfSiblingOfElementByTagAndText(source, "dt", "Author(s)");
            scrapedComic.setAuthor(author);

            String summary = findTextOfSiblingOfElementByTagAndText(source, "h5", "Summary");
            scrapedComic.setSummary(summary);

            Element chapters = getOnlyElement(source.getElementsByClass("chapters"));
            Elements chapterListItem = chapters.getElementsByTag("li");
            for (Element issue : chapterListItem) {
                String key = getOnlyElement(issue.getElementsByTag("a")).text();
                String chapterUrl = getOnlyElement(issue.getElementsByTag("a")).attr("href");
                String date = getOnlyElement(issue.getElementsByClass("date-chapter-title-rtl")).text();
                scrapedComic.addIssue(new ScrapedIssueDetails(key, chapterUrl, parseDateString(date)));
            }

            LOGGER.trace(scrapedComic.toString());

            return scrapedComic;
        }
        catch (HttpStatusException ex) {
            throw throwComicNotFound(technicalComicName, ex);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        try {
            Document source = getSource(buildUrlForIssue(technicalComicName, issue));
            Elements images = source.select("img[data-src]");
            if (!images.isEmpty()) {
                List<String> pages = images.stream().map(element -> element.attributes().get("data-src").trim()).collect(Collectors.toList());
                return new ScrapedIssue(technicalComicName, issue, pages.size(), pages);
            }
            return null;
        }
        catch(HttpStatusException ex) {
            throw throwIssueNotFound(technicalComicName, issue, ex);
        }
    }

    @Override
    public byte[] downloadPage(String url) throws FileNotFoundException {
        try {
            return IOUtils.toByteArray(getUrlConnection(url));
        } catch (Exception e) {
            throw throwPageNotFound(url);
        }
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL) + technicalComicName;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + technicalComicName + "/" + issue;
    }

    private String findTextOfSiblingOfElementByTagAndText(Document source, String htmlTag, String searchText) {
        Elements tagList = source.getElementsContainingText(searchText);
        Element tag = tagList.stream().filter(s -> htmlTag.equals(s.tag().getName())).findFirst().orElse(null);
        if (tag != null) {
            int indexInParent = tag.parent().children().indexOf(tag);
            Element tagValue = tag.parent().child(indexInParent + 1);
            return tagValue.text();
        }
        return null;
    }

    private LocalDate parseDateString(String date) {
        String[] split = date.split(" ");
        if (split.length != 3) {
            throw new IllegalStateException("Can't parse date " + date);
        }
        return LocalDate.of(Integer.parseInt(split[2]), MONTHS.get(split[1].substring(0,3).toUpperCase()), Integer.parseInt(split[0]));
    }
}
