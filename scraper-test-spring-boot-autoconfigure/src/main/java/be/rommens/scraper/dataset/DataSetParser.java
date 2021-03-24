package be.rommens.scraper.dataset;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * User : cederik
 * Date : 15/05/2020
 * Time : 13:49
 */
@RequiredArgsConstructor
public class DataSetParser {

    private final String dataset;

    public List<Comic> parseDataSet() {
        if (StringUtils.isNotEmpty(dataset)) {
            InputStream inputStream = this.getClass().getResourceAsStream(dataset);
            Assert.notNull(inputStream, dataset + " is not found");
            ComicsGroup comicsGroup = parseYaml(inputStream);
            if (comicsGroup != null) {
                return comicsGroup.getComics();
            }
        }
        return Collections.emptyList();
    }

    private ComicsGroup parseYaml(InputStream inputStream) {
        final Constructor comicsConstructor = new Constructor(ComicsGroup.class);
        final TypeDescription comicsDescription = new TypeDescription(Comic.class);
        comicsDescription.addPropertyParameters("comics", Comic.class);
        comicsConstructor.addTypeDescription(comicsDescription);

        final TypeDescription issuesDescription = new TypeDescription(Issue.class);
        issuesDescription.addPropertyParameters("issues", Issue.class);
        comicsConstructor.addTypeDescription(comicsDescription);

        Yaml yaml = new Yaml(comicsConstructor);
        return yaml.load(inputStream);
    }
}
