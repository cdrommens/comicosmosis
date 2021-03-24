package be.rommens.hades.command;

import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import net.lingala.zip4j.ZipFile;
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
 * Time : 14:52
 */
class ZipFolderCommandTest {

    @TempDir
    Path tempDir;

    @Test
    void whenNotDir_thenReturnErrorAndZipNotExist() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.touch(newDir);

        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.ERROR);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1.cbz"))).isFalse();
    }

    @Test
    void whenEmptyDir_thenReturnErrorAndZipNotExist() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);

        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.ERROR);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1.cbz"))).isFalse();
    }

    @Test
    void whenIsDir_thenReturnCompletedAndZipExists() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        File page1 = new File(newDir, "page1");
        File page2 = new File(newDir, "page2");
        FileUtils.touch(page1);
        FileUtils.touch(page2);
        assertThat(Files.exists(page1.toPath())).isTrue();
        assertThat(Files.exists(page2.toPath())).isTrue();

        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));

        //when
        CommandResult result = command.body();

        //then
        assertThat(result).isEqualTo(CommandResult.COMPLETED);

        ZipFile expected = new ZipFile(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1.cbz").toFile());
        assertThat(expected.isValidZipFile()).isTrue();

        assertThat(expected.getFileHeaders()).extracting("fileName").containsAnyOf("comickey-1/page1", "comickey-1/page2");
        //assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/page1"));
        //assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/page2"));
    }

    @Test
    void testRollback() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        boolean result = command.rollback();
        //then
        assertThat(result).isTrue();
        assertThat(Files.exists(newDir.toPath())).isFalse();
    }
}
