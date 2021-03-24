package be.rommens.hades.model;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.scraper.api.Provider;
import be.rommens.scraper.core.Scraper;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:58
 */
public class IssueAssemblyContextTestObjectFactory {

    public static IssueAssemblyContext createTestContext(String baseUrl, Scraper scraper) {
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage(1, "comickey", Provider.READCOMICS, "1", null);
        IssueAssemblyContext context = new IssueAssemblyContext(downloadIssueMessage, baseUrl, scraper);
        if (StringUtils.isNotEmpty(baseUrl)) {
            context.setIssueFolder(Paths.get(baseUrl, "comickey", "comickey-1").toString());
        }
        return context;
    }
}
