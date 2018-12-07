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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.LinkService;
import org.verinice.model.Vlink;
import org.verinice.persistence.CnaLinkDao;
import org.verinice.persistence.entities.CnaLink;
import org.verinice.persistence.entities.LinkConverter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LinkServiceImpl implements LinkService {

    private static final Logger LOG = LoggerFactory.getLogger(LinkServiceImpl.class);

    @Autowired
    private CnaLinkDao dao;

    @Override
    public Set<Vlink> loadLinks(Map<String, String> queryParams) {
        LOG.debug("loadLinks with queryParams:", queryParams);
        List<CnaLink> links = dao.find(queryParams);
        return LinkConverter.toVlinks(links);
    }
}
