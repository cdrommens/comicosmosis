package be.rommens.hades.command;

import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:27
 */
public class CleanUpCommandTest {

    @TempDir
    Path tempDir;

    @Test
    void whenIsDir_thenReturnCompleteAndDirDeleted() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        CleanUpCommand command = new CleanUpCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        CommandResult result = command.body();
        //then
        assertThat(result).isEqualTo(CommandResult.COMPLETED);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isFalse();
    }

    @Test
    void whenIsNotDir_thenReturnErrorAndDirNotCleanUp() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.touch(newDir);
        CleanUpCommand command = new CleanUpCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        CommandResult result = command.body();
        //then
        assertThat(result).isEqualTo(CommandResult.ERROR);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
    }

    @Test
    void testRollback() {
        //given
        CleanUpCommand command = new CleanUpCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        boolean result = command.rollback();
        //then
        assertThat(result).isTrue();
    }
}
