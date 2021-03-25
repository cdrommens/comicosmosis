package be.rommens.scraper.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.rommens.scraper.dataset.Comic;
import be.rommens.scraper.dataset.DataSetParser;
import be.rommens.scraper.dataset.Issue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:50
 */
class DataSetParserTest {

    @Test
    void whenDataSetNotFOund_thenReturnThrowError() {
        //given
        DataSetParser parser = new DataSetParser("/datasets/unknown.yml");

        //when / then
        assertThatThrownBy(parser::parseDataSet).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNoDataSet_thenReturnEmptyList() {
        //given
        DataSetParser parser = new DataSetParser(null);

        //when / then
        assertThat(parser.parseDataSet()).isEmpty();
    }

    @Test
    void whenEmptyDataSet_thenReturnEmptyList() {
        //given
        DataSetParser parser = new DataSetParser("/datasets/scraper-empty.yml");

        //when / then
        assertThat(parser.parseDataSet()).isEmpty();
    }

    @Test
    void whenDataSetWithWrongFormat_thenReturnThrowError() {
        //given
        DataSetParser parser = new DataSetParser("/datasets/scraper-wrong-format.yml");

        //when / then
        assertThatThrownBy(parser::parseDataSet).isInstanceOf(YAMLException.class);
    }

    @Test
    void whenDataSetWith1Comic_thenReturnListWith1Comic() {
        //given
        DataSetParser parser = new DataSetParser("/datasets/scraper-input.yml");
        List<Comic> expected = Collections.singletonList(
            Comic.builder()
                .key("batman-2016")
                .title("batman-2016")
                .cover("cover1.jpg")
                .publisher("DC Comics")
                .dateOfRelease("2016")
                .status("ONGOING")
                .author("Tom King")
                .summary("summary")
                .issues(Arrays.asList(
                    Issue.builder()
                        .issueNumber("1")
                        .url("url1")
                        .date("2016-01-01")
                        .pages(Arrays.asList("page1.txt", "page2.jpg", "page3.jpg")).build(),
                    Issue.builder()
                        .issueNumber("2")
                        .url("url2")
                        .date("2018-01-01")
                        .pages(Arrays.asList("page1.jpg", "page2.jpg")).build()))
                .build());

        //when
        List<Comic> result = parser.parseDataSet();

        //then
        assertThat(result).hasSize(1);
        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    @Test
    void whenDataSetWith2Comics_thenReturnListWith2Comics() {
        //given
        DataSetParser parser = new DataSetParser("/datasets/scraper-multiple-input.yml");
        List<Comic> expected = Arrays.asList(
            Comic.builder()
                .key("batman-2016")
                .title("batman-2016")
                .cover("cover1.jpg")
                .publisher("DC Comics")
                .dateOfRelease("2016")
                .status("ONGOING")
                .author("Tom King")
                .summary("summary")
                .issues(Arrays.asList(
                    Issue.builder()
                        .issueNumber("1")
                        .url("url1")
                        .date("2016-01-01")
                        .pages(Arrays.asList("page1.jpg", "page2.jpg", "page3.jpg")).build(),
                    Issue.builder()
                        .issueNumber("2")
                        .url("url2")
                        .date("2018-01-01")
                        .pages(Arrays.asList("page1.jpg", "page2.jpg")).build()))
                .build(),
            Comic.builder()
                .key("superman-2016")
                .title("superman-2016")
                .cover("cover1.jpg")
                .publisher("DC Comics")
                .dateOfRelease("2016")
                .status("ONGOING")
                .author("Tom King")
                .summary("summary")
                .issues(Arrays.asList(
                    Issue.builder()
                        .issueNumber("1")
                        .url("url1")
                        .date("2016-01-01")
                        .pages(Arrays.asList("page1.jpg", "page2.jpg", "page3.jpg")).build(),
                    Issue.builder()
                        .issueNumber("2")
                        .url("url2")
                        .date("2018-01-01")
                        .pages(Arrays.asList("page1.jpg", "page2.jpg")).build()))
                .build());

        //when
        List<Comic> result = parser.parseDataSet();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
