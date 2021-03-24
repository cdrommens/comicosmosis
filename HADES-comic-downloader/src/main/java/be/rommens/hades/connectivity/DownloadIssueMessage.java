package be.rommens.hades.connectivity;

import be.rommens.scraper.api.Provider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadIssueMessage {

    private Integer issueId;
    private String comicKey;
    private Provider provider;
    private String issueNumber;
    private LocalDate dateOfRelease;

    public String getIssueFolder() {
        return comicKey + "-" + issueNumber;
    }

    public String getComicFolder() {
        return comicKey;
    }
}
