package be.rommens.cleanup;

import static org.assertj.core.api.Assertions.assertThat;

import be.rommens.scraper.autoconfigure.AutoConfigureScraperMock;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 */
@SpringBootTest(classes = CleanupService.class)
@AutoConfigureScraperMock(value = "/datasets/cleanup-scraper-input.yml")
public class CleanupServiceTest {

    @TempDir
    Path tempDir;

    @Autowired
    private CleanupService cleanupService;

    @Test
    void testCleanup() throws IOException {
        FileUtils.forceMkdir(Paths.get(tempDir.toAbsolutePath().toString(), "comic-old").toFile());
        FileUtils.forceMkdir(Paths.get(tempDir.toAbsolutePath().toString(), "comic-new").toFile());

        //when
        List<String> result = cleanupService.clean(tempDir);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result).containsExactly("comic-old");
    }

}
