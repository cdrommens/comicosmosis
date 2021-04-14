package be.rommens.cleanup;

import be.rommens.scraper.api.Provider;
import be.rommens.scraper.api.models.ScrapedComic;
import be.rommens.scraper.api.models.ScrapedIssueDetails;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.core.Scraper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CleanupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanupService.class);

    private final Scraper scraper;

    public CleanupService(ScraperFactory scraperFactory) {
        this.scraper = scraperFactory.createScraper(Provider.READCOMICS);
    }

    public List<String> clean(Path sourceFolder) {
        return readDirectory(sourceFolder).stream()
                .filter(this::filterComicsLastIssueOlderThan3Months)
                .collect(Collectors.toList());
    }

    private static List<String> readDirectory(Path folder) {
        LOGGER.info("Reading directory {}", folder);
        try(Stream<Path> stream = Files.walk(folder)) {
            return stream
                    .skip(1)
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Could not read directory " + folder, e);
        }
    }

    private boolean filterComicsLastIssueOlderThan3Months(String comic) {
        ScrapedComic scrapedComic = scraper.scrapeComic(comic);
        LocalDate mostRecentDate = scrapedComic.getIssues().stream()
                .map(ScrapedIssueDetails::getDate)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("No issue found"));
        LOGGER.info("Comic {} has most recent issue published on {}", comic, mostRecentDate);
        return isOlderThan3Months(mostRecentDate);
    }

    private static boolean isOlderThan3Months(LocalDate date) {
        return date.isBefore(LocalDate.now().minusMonths(3));
    }
}
