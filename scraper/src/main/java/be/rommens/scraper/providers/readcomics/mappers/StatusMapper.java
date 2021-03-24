package be.rommens.scraper.providers.readcomics.mappers;

import be.rommens.scraper.api.Status;
import be.rommens.scraper.core.Mapper;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:13
 */
public class StatusMapper implements Mapper<Status> {

    @Override
    public Status mappingLogic(String valueInUppercase) {
        switch (valueInUppercase) {
            case "ONGOING":
                return Status.ONGOING;
            case "COMPLETE":
                return Status.FINISHED;
            default:
                return null;
        }
    }
}
