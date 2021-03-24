package be.rommens.scraper.providers;

import be.rommens.scraper.api.Status;
import be.rommens.scraper.providers.readcomics.mappers.StatusMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:16
 */
class StatusMapperTest {

    @Test
    void testMapToOngoing() {
        assertThat(new StatusMapper().mapTo("Ongoing")).isEqualTo(Status.ONGOING);
        assertThat(new StatusMapper().mapTo("ongoing")).isEqualTo(Status.ONGOING);
        assertThat(new StatusMapper().mapTo("ONGOING")).isEqualTo(Status.ONGOING);
    }

    @Test
    void testMapToFinished() {
        assertThat(new StatusMapper().mapTo("Complete")).isEqualTo(Status.FINISHED);
        assertThat(new StatusMapper().mapTo("complete")).isEqualTo(Status.FINISHED);
        assertThat(new StatusMapper().mapTo("COMPLETE")).isEqualTo(Status.FINISHED);
    }

    @Test
    void testEmptyOrNull() {
        assertThat(new StatusMapper().mapTo(null)).isNull();
        assertThat(new StatusMapper().mapTo("")).isNull();
    }
}
