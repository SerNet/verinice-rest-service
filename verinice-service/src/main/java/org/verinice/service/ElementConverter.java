/*
 * Copyright (c) 2016 Daniel Murygin.
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
 * 
 * Contributors:
 *  Daniel Murygin - initial API and implementation
 */
package org.verinice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.verinice.model.Velement;
import org.verinice.persistence.entities.CnATreeElement;
import org.verinice.persistence.entities.Property;

/**
 *
 * @author Daniel Murygin
 */
public class ElementConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ElementConverter.class);

    private static Map<String, String> specialties = new HashMap<>();

    static{
        specialties.put("gefaehrdungs-umsetzung", "gefaehrdungsumsetzung_titel_0");
    }

    static Velement elementForEntity(CnATreeElement dbEntity) {
        if(dbEntity==null) {
            return null;
        }
        Velement element = new Velement();
        element.setUuid(dbEntity.getUuid());   
        element.setType(dbEntity.getType());
        element.setProperties(convertPropertyLists(dbEntity));
        element.setTitle(getTitle(element, dbEntity));

        return element;
    }

    private static String getTitle(Velement element, CnATreeElement dbEntity) {
        if (specialties.containsKey(dbEntity.getType())) {
            return element.getProperties().get(specialties.get(dbEntity.getType()));
        }
        if (element.getProperties() != null && !element.getProperties().isEmpty()) {
            for (String key : element.getProperties().keySet()) {
                if (key.toLowerCase().endsWith("name_0")) {
                    return element.getProperties().get(key);
                }
            }

        }
        return null;
    }

    private static Map<String, String> convertPropertyLists(CnATreeElement dbEntity) {

        Map<String, String> propertyMap = new HashMap<>();
        if (dbEntity.getEntity() != null && dbEntity.getEntity().getPropertyLists() != null) {
        dbEntity.getEntity().getPropertyLists().forEach(propertyList -> {
            Set<Property> properties = propertyList.getProperties();
            if (properties != null) {
                properties.forEach(property -> {
                    propertyMap.put(property.getPropertytype() + "_" + property.getPropertiesIdx(),
                            property.getPropertyvalue());
                });

            } else {
                LOG.error("properties are null for propertyList : " + propertyList.getUuid());

            }
        });
        }

        return propertyMap;
    }
    
}
