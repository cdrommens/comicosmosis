package be.rommens.cleanup;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 */
public class CommandFileWriterTest {

    @TempDir
    Path tempDir;

    @Test
    public void testOutputFile() throws IOException {
        List<String> foldersToClean = List.of("folder1", "folder2", "folder3");

        //when
        CommandFileWriter writer = new CommandFileWriter();
        writer.writeToFile(tempDir, foldersToClean);

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "cleanup.sh"))).isTrue();
        List<String> content = Files.readAllLines(Paths.get(tempDir.toAbsolutePath().toString(), "cleanup.sh"));
        assertThat(content).containsExactly("rm -rf folder1", "rm -rf folder2", "rm -rf folder3");
    }

}