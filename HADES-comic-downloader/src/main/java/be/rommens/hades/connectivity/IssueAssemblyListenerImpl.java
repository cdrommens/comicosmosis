package be.rommens.hades.connectivity;

import be.rommens.hades.core.AssemblyChainFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:25
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IssueAssemblyListenerImpl implements IssueAssemblyListener {

    private final AssemblyChainFactory<DownloadIssueMessage> issueAssemblyChainFactory;

    @StreamListener(Sink.INPUT)
    @Override
    public void processIssue(DownloadIssueMessage downloadIssueMessage) {
        log.info("message received {} - {}", downloadIssueMessage.getComicKey(), downloadIssueMessage.getIssueNumber());
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();
    }
}
