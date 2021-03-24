package be.rommens.hades.assembler;

import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AssemblyChainFactory;
import be.rommens.scraper.api.Provider;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 27/04/2020
 * Time : 09:07
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "providers.url.readcomics=http://localhost:${wiremock.server.port}/",
    }
)
@AutoConfigureWireMock(port = 8977)
class IssueAssemblyChainFactoryIT {

    private static final String BASE_URL = Paths.get(FileUtils.getTempDirectoryPath(),"junit5/").toString();

    @Autowired
    private AssemblyChainFactory<DownloadIssueMessage> issueAssemblyChainFactory;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("download.folder.base", () -> BASE_URL);
    }

    @BeforeEach
    void setUp() throws IOException {
        FileUtils.forceMkdir(Paths.get(BASE_URL).toFile());
    }

    @AfterEach
    void TearDown() throws IOException {
        FileUtils.deleteDirectory(Paths.get(BASE_URL).toFile());
    }

    @Test
    void testIssueAssemblyChain_withZipAlreadyExists() throws IOException {
        //given
        FileUtils.forceMkdir(Paths.get(BASE_URL, "comickey", "comickey-1.cbz").toFile());
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);

        //when
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        //then
        assertThat(Files.exists(Paths.get(BASE_URL, "comickey", "comickey-1"))).isFalse();
        //TODO : test that message is published
    }

    @Test
    void testIssueAssemblyChain_withNoErrors() throws ZipException {
        //given
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);

        //when
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        //then
        ZipFile expected = new ZipFile(Paths.get(BASE_URL,"comickey","comickey-1.cbz").toFile());
        assertThat(expected.isValidZipFile()).isTrue();
        assertThat(FileUtils.sizeOf(expected.getFile())).isGreaterThan(10L);
        assertThat(expected.getFileHeaders()).extracting("fileName").containsAnyOf("comickey-1/02.jpg", "comickey-1/03.jpg");
        assertThat(Files.exists(Paths.get(BASE_URL, "comickey", "comickey-1"))).isFalse();
    }

    @Test
    void testIssueAssemblyChain_withErrorsDuringCreateFolder() throws IOException {
        //given
        FileUtils.touch(Paths.get(BASE_URL, "comickey", "comickey-1").toFile());
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);

        //when
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        //then
        assertThat(Files.exists(Paths.get(BASE_URL, "comickey", "comickey-1"))).isTrue();
        assertThat(Files.isDirectory(Paths.get(BASE_URL, "comickey", "comickey-1"))).isFalse();
    }

    @Test
    void testIssueAssemblyChain_withErrorsDuringScrapeIssue() throws IOException {
        //given
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);

        //when
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        //then
        assertThat(Files.exists(Paths.get(BASE_URL, "nonexistent", "nonexistent-1"))).isFalse();
    }

    @Test
    void testIssueAssemblyChain_withErrorsDuringDownloadPages() throws IOException {
        //given
        FileUtils.forceMkdir(Paths.get(BASE_URL, "comickey", "comickey-1","02.jpg").toFile());
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);

        //when
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        //then
        assertThat(Files.exists(Paths.get(BASE_URL, "comickey", "comickey-1"))).isFalse();
    }
}
