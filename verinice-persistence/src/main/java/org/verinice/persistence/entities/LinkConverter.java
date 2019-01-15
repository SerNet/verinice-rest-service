/*
 * Copyright (c) 2018 Alexander Ben Nasrallah
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
package org.verinice.persistence.entities;

import org.verinice.model.Vlink;

import java.util.HashSet;
import java.util.Set;

/**
 * This class provides methods to convert an instance of one class to an
 * instance of a different class.
 *
 * Do not instantiate this class use public static methods.
 */
public final class LinkConverter {

    private LinkConverter() {
        super();
    }

    public static Vlink toVlink(CnaLink link) {
        if (link == null) {
            return null;
        }
        Vlink vlink = new Vlink();
        vlink.setDependantId(link.getDependantId());
        vlink.setDependencyId(link.getDependencyId());
        vlink.setTypeId(link.getTypeId());
        vlink.setComment(link.getComment());
        vlink.setLinkType(link.getLinkType());
        vlink.setRiskavailability(link.getRiskavailability());
        vlink.setRiskconfidentiality(link.getRiskconfidentiality());
        vlink.setRiskintegrity(link.getRiskintegrity());
        vlink.setRiskawithcontrols(link.getRiskawithcontrols());
        vlink.setRiskcwithcontrols(link.getRiskcwithcontrols());
        vlink.setRiskiwithcontrols(link.getRiskiwithcontrols());
        vlink.setRisktreatmentvalue(link.getRisktreatmentvalue());
        return vlink;
    }

    public static Set<Vlink> toVlinks(Iterable<CnaLink> links) {
        HashSet<Vlink> elements = new HashSet<>();
        links.forEach(link -> elements.add(toVlink(link)));
        return elements;
    }
}
