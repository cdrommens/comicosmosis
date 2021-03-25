package be.rommens.cleanup;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

/**
 *
 */
public class CleanupApplicationTest {

    @TempDir
    Path tempDir;

    @Test
    public void testNoSourceProvided() {
        ApplicationArguments args = new DefaultApplicationArguments();
        CleanupApplication application = new CleanupApplication(null, null);
        assertThrows(IllegalArgumentException.class, () -> application.run(args), "Please specify option -source=/path/to/source");
    }

    @Test
    public void testEmptySourceProvided() {
        ApplicationArguments args = new DefaultApplicationArguments("--source=");
        CleanupApplication application = new CleanupApplication(null, null);
        assertThrows(IllegalArgumentException.class, () -> application.run(args), "Source is empty");
    }

    @Test
    public void testFolderDoesNotExist() {
        ApplicationArguments args = new DefaultApplicationArguments("--source=notexist");
        CleanupApplication application = new CleanupApplication(null, null);
        assertThrows(IllegalArgumentException.class, () -> application.run(args), "Source does not exist or is not a directory");
    }

    @Test
    public void testFolderIsAFile() {
        File file = Paths.get(tempDir.toAbsolutePath().toString(), "file").toFile();
        ApplicationArguments args = new DefaultApplicationArguments("--source=" + file.toString());
        CleanupApplication application = new CleanupApplication(null, null);
        assertThrows(IllegalArgumentException.class, () -> application.run(args), "Source does not exist or is not a directory");
    }

}
