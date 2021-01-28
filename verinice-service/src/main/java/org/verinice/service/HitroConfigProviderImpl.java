package org.verinice.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.sernet.hitro.Huientities;

/**
 * Loads the verinice hitro config, aka. SNCA.xml, from the verinice server.
 * If present the property veriniceserver.url.hitroconfig is used, otherwise
 * it falls back to the property veriniceserver.url using the path /veriniceserver/GetHitroConfig.
 * If neither is present, no configuration is loaded.
 */
@Component
public class HitroConfigProviderImpl implements Supplier<Optional<Huientities>> {

    private static Logger logger = LoggerFactory.getLogger(HitroConfigProviderImpl.class);

    private final Optional<Huientities> hitroConfig;

    @Autowired
    public HitroConfigProviderImpl(@Value("${veriniceserver.url:}") final String veriniceServerUrl,
            @Value("${veriniceserver.url.hitroconfig:}") final String veriniceServerHitroConfigUrl) {
        Huientities fetchedHitroConfig = null;
        String hitroUrl = StringUtils.isNotBlank(veriniceServerHitroConfigUrl)
            ? veriniceServerHitroConfigUrl : veriniceServerUrl + "/veriniceserver/GetHitroConfig";
        if (StringUtils.isNotBlank(veriniceServerUrl)) {
            try {
                fetchedHitroConfig = parserHuientitites(fetch(hitroUrl));
                logger.debug("fetched hitro config with {} defined entities", fetchedHitroConfig.getHuientity().size());
            } catch (IOException e) {
                logger.error("unable to fetch hitro config from '{}': {}", hitroUrl, e.getMessage());
            } catch (JAXBException | IllegalArgumentException e) {
                logger.error("unable to parse hitro config from '{}': {}", hitroUrl, e.getMessage());
            }
        }
        this.hitroConfig = Optional.ofNullable(fetchedHitroConfig);
    }

    @Override
    public Optional<Huientities> get() {
        return hitroConfig;
    }

    private Huientities parserHuientitites(InputStream is) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Huientities.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Huientities) jaxbUnmarshaller.unmarshal(is);
    }

    private InputStream fetch(String url) throws IOException {
        return new URL(url).openStream();
    }
}
