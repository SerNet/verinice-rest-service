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
package org.verinice.persistence.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.verinice.model.Velement;

/**
 * This class provides methods to convert an instance of one class to an 
 * instance of a different class.
 * 
 * Do not instantiate this class use public static methods.
 * 
 * @author Daniel Murygin
 */
public final class ElementConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ElementConverter.class);

    private static Map<String, String> specialNamePropertyTypes = new HashMap<>();

    static{
        specialNamePropertyTypes.put("gefaehrdungs-umsetzung", "gefaehrdungsumsetzung_titel");
    }
    
    /**
     * Do not instantiate this class use public static methods.
     */
    private ElementConverter() {
        super();
    }

    public static Velement elementForEntity(CnaTreeElement dbEntity) {
        if(dbEntity==null) {
            return null;
        }
        Velement element = new Velement();
        element.setUuid(dbEntity.getUuid());   
        element.setType(dbEntity.getType());
        element.setProperties(convertPropertyLists(dbEntity));
        element.setTitle(getTitle(element, dbEntity));
        if (dbEntity.getScopeId() != null)
            element.setScopeId(dbEntity.getScopeId());
        element.setExtId(dbEntity.getExtId());
        if (dbEntity.getParentId() != null)
            element.setParentId(dbEntity.getParentId());
        element.setSourceId(dbEntity.getSourceId());

        return element;
    }

    public static Set<Velement> elementsForEntitys(Iterable<CnaTreeElement> dbEntities) {

        HashSet<Velement> velements = new HashSet<>();
        dbEntities.forEach(dbentity -> velements.add(elementForEntity(dbentity)));
        return velements;
    }

    private static String getTitle(Velement element, CnaTreeElement dbEntity) {
        if (specialNamePropertyTypes.containsKey(dbEntity.getType())) {
            return element.getProperties().get(specialNamePropertyTypes.get(dbEntity.getType())).iterator()
                    .next();
        }
        if (element.getProperties() != null && !element.getProperties().isEmpty()) {
            for (String key : element.getProperties().keySet()) {
                if (key.toLowerCase().endsWith("name")) {
                    return element.getProperties().get(key).iterator().next();
                }
            }

        }
        return null;
    }

    private static Map<String, List<String>> convertPropertyLists(CnaTreeElement dbEntity) {
        Map<String, List<String>> propertyMap = new HashMap<>();
        if (dbEntity.getEntity() != null && dbEntity.getEntity().getPropertyLists() != null) {

        dbEntity.getEntity().getPropertyLists().forEach(propertyList -> {
            Set<Property> properties = propertyList.getProperties();
            if (properties != null) {
                    List<String> values = new ArrayList<>();
                    String type = properties.iterator().next().getPropertytype();
                    properties.forEach(property -> values.add(property.getPropertyvalue()));
                    propertyMap.put(type, values);

            } else {
                LOG.error("properties are null for propertyList : " + propertyList.getUuid());

            }
        });

        }
        return propertyMap;
    }
    
}
