package be.rommens.cleanup;

import static be.rommens.scraper.utils.MyCollectionUtils.getOnlyElement;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

/**
 *
 */
@SpringBootApplication
public class CleanupApplication implements ApplicationRunner {

    private static final String SOURCE_ARGUMENT = "source";

    private final CleanupService cleanupService;
    private final CommandFileWriter writer;

    public CleanupApplication(CleanupService cleanupService, CommandFileWriter writer) {
        this.cleanupService = cleanupService;
        this.writer = writer;
    }

    public static void main(String[] args) {
        SpringApplication.run(CleanupApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (CollectionUtils.isEmpty(args.getOptionNames()) || CollectionUtils.isEmpty(args.getOptionValues(SOURCE_ARGUMENT))) {
            throw new IllegalArgumentException("Please specify option -source=/path/to/source");
        }
        var sourceFolder = getOnlyElement(args.getOptionValues(SOURCE_ARGUMENT));
        if (StringUtils.isEmpty(sourceFolder)) {
            throw new IllegalArgumentException("Source is empty");
        }
        var sourceFolderPath = Paths.get(sourceFolder);
        if (Files.notExists(sourceFolderPath) || !Files.isDirectory(sourceFolderPath)) {
            throw new IllegalArgumentException("Source does not exist or is not a directory");
        }
        List<String> foldersToClean = cleanupService.clean(sourceFolderPath);
        writer.writeToFile(sourceFolderPath, foldersToClean);
    }
}
