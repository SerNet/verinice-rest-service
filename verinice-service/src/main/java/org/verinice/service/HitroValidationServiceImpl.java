package org.verinice.service;

import de.sernet.hitro.Huientities;
import de.sernet.hitro.Huientitytype;
import de.sernet.hitro.Huirelationtype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.exceptions.LinkValidationException;
import org.verinice.interfaces.HitroValidationService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class HitroValidationServiceImpl implements HitroValidationService {

    private static Logger logger = LoggerFactory.getLogger(HitroValidationServiceImpl.class);

    private final Supplier<Optional<Huientities>> hitroConfigProvider;

    @Autowired
    public HitroValidationServiceImpl(final Supplier<Optional<Huientities>> hitroConfigProvider) {
        this.hitroConfigProvider = hitroConfigProvider;
    }

    @Override
    public void validateLink(String dependantType, String dependencyType, String linkId)
            throws LinkValidationException {

        Huientities config = hitroConfigProvider.get().orElseThrow(() -> {
            var msg = "could not validate link, because no hitro config has been loaded on startup.";
            logger.error(msg);
            return new LinkValidationException(msg);
        });

        // @formatter:off
        config.getHuientity().stream()
                .filter(x -> x.getId().equals(dependantType))
                .map(Huientitytype::getHuipropertyAndHuipropertygroupAndHuirelation)
                .flatMap(List::stream)
                .filter(Huirelationtype.class::isInstance)
                .map(Huirelationtype.class::cast)
                .filter(relation -> relation.getId().equals(linkId))
                .map(Huirelationtype::getTo)
                .filter(dependency -> dependency.getId().equals(dependencyType))
                .findFirst()
                .orElseThrow(() -> {
                    var msg = "invalid link of id " + linkId + " between " + dependantType + " and " + dependencyType;
                    logger.error(msg);
                    return new LinkValidationException(msg);
                });
        // @formatter:on
    }
}
