package be.rommens.hades.assembler;

import be.rommens.hades.command.CleanUpCommand;
import be.rommens.hades.command.CreateFolderCommand;
import be.rommens.hades.command.DownloadIssuePagesCommand;
import be.rommens.hades.command.PublishIssueDownloadedMessage;
import be.rommens.hades.command.ScrapeIssueCommand;
import be.rommens.hades.command.ZipFolderCommand;
import be.rommens.hades.command.ZipNotExistCommand;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AssemblyChainFactory;
import be.rommens.scraper.api.service.ScraperFactory;
import be.rommens.scraper.core.Scraper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO : rework to nio

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:24
 */
@Component
public class IssueAssemblyChainFactory implements AssemblyChainFactory<DownloadIssueMessage> {

    private final ScraperFactory scraperFactory;
    private final String baseUrl;

    public IssueAssemblyChainFactory(ScraperFactory scraperFactory,
                                     @Value("${download.folder.base}") String baseUrl) {
        this.scraperFactory = scraperFactory;
        this.baseUrl = baseUrl;
    }

    @Override
    public IssueAssemblyChain createAssemblyChain(DownloadIssueMessage downloadIssueMessage) {
        IssueAssemblyContext context = createContextObject(downloadIssueMessage);
        return createChain(context);
    }

    private IssueAssemblyChain createChain(IssueAssemblyContext context) {
        return IssueAssemblyChainBuilder.createInstance()
            .withPrecondition(ZipNotExistCommand.class)
            .onFailed(PublishIssueDownloadedMessage.class)
            .orElse()
                .withContext(context)
                .startChain(CreateFolderCommand.class)
                .next(ScrapeIssueCommand.class)
                .next(DownloadIssuePagesCommand.class)
                .next(ZipFolderCommand.class)
                .next(CleanUpCommand.class)
                .next(PublishIssueDownloadedMessage.class)
            //TODO: update issue in db command
            //TODO: notify mongodb change
            .build();
    }

    private IssueAssemblyContext createContextObject(DownloadIssueMessage downloadIssueMessage) {
        Scraper scraper = scraperFactory.createScraper(downloadIssueMessage.getProvider());
        return new IssueAssemblyContext(downloadIssueMessage, baseUrl, scraper);
    }
}
