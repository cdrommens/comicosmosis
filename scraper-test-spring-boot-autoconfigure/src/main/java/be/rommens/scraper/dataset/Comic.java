package be.rommens.scraper.dataset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:39
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Comic {

    private String key;
    private String title;
    private String publisher;
    private String author;
    private String dateOfRelease;
    private String status;
    private String cover;
    private String summary;
    private List<Issue> issues;
}
