/*
 * Copyright (c) 2018 Alexander Ben Nasrallah.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.verinice.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.verinice.exceptions.LinkValidationException;
import org.verinice.interfaces.HitroValidationService;
import org.verinice.interfaces.LinkService;
import org.verinice.model.Vlink;
import org.verinice.persistence.CnaLinkDao;
import org.verinice.persistence.CnaTreeElementDao;
import org.verinice.persistence.entities.CnaLink;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.LinkConverter;

@Service
public class LinkServiceImpl implements LinkService {

    private static final Logger LOG = LoggerFactory.getLogger(LinkServiceImpl.class);

    @Autowired
    private CnaLinkDao linkDao;

    @Autowired
    private CnaTreeElementDao elementDao;

    @Autowired
    private HitroValidationService hitroValidationService;

    private final String veriniceServerUrl;

    public LinkServiceImpl(@Value("${veriniceserver.url:}") final String veriniceServerUrl) {
        this.veriniceServerUrl = veriniceServerUrl;
    }

    @Override
    public Set<Vlink> loadLinks(Map<String, String> queryParams) {
        LOG.debug("loadLinks with queryParams {}:", queryParams);
        List<CnaLink> links = linkDao.find(queryParams);
        return LinkConverter.toVlinks(links);
    }

    @Override
    public Vlink insertLinks(Vlink link) throws LinkValidationException {
        CnaTreeElement dependant = elementDao.findByDbid(link.getDependantId());
        CnaTreeElement dependency = elementDao.findByDbid(link.getDependencyId());
        LOG.debug("creating link for {} {} {}", dependant.getType(), dependency.getType(),
                link.getTypeId());
        hitroValidationService.validateLink(dependant.getType(), dependency.getType(),
                link.getTypeId());

        CnaLink dblink = LinkConverter.toCnaLink(link);
        dblink = linkDao.insert(dblink);
        notifyVeriniceServer();
        return LinkConverter.toVlink(dblink);
    }

    private void notifyVeriniceServer() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(veriniceServerUrl + "/veriniceserver/evict-links"))
                .header("Content-Type", "application/json")
                .GET().build();
        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(request, BodyHandlers.ofString())
              .handleAsync((ignore, e) -> {
                  if (e != null) {
                      LOG.error("unable to notify veriniceserver about changed links: {}", e.getMessage());
                  } else {
                      LOG.info("notified veriniceserver about changed links");
                  }
                  return null;
              });
    }
}
