package be.rommens.cleanup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CommandFileWriter {

    private static final String COMMAND = "rm -rf %s\n";
    private static final String OUTPUT_FILENAME = "cleanup.sh";

    public void writeToFile(Path sourceFolder, List<String> folders) {
        try {
            File file = Paths.get(sourceFolder.toAbsolutePath().toString(), OUTPUT_FILENAME).toFile();
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            folders.forEach(folder -> printWriter.printf(COMMAND, folder));
            printWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException("Could not open file for writing", e);
        }
    }
}
