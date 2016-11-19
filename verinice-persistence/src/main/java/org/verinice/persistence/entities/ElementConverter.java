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

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.verinice.model.Velement;

/**
 * This class provides methods to convert an instance of one class to an
 * instance of a different class.
 * <p>
 * Do not instantiate this class use public static methods.
 *
 * @author Daniel Murygin
 */
public final class ElementConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ElementConverter.class);

    private static Map<String, String> specialNamePropertyTypes = new HashMap<>();

    static {
        specialNamePropertyTypes.put("gefaehrdungs-umsetzung", "gefaehrdungsumsetzung_titel");
    }

    /**
     * Do not instantiate this class use public static methods.
     */
    private ElementConverter() {
        super();
    }

    public static Velement elementForEntity(CnaTreeElement dbEntity) {
        if (dbEntity == null) {
            return null;
        }
        Velement element = new Velement();
        element.setUuid(dbEntity.getUuid());
        element.setDbId(dbEntity.getDbid());
        element.setType(dbEntity.getType());
        final Map<String, List<String>> convertPropertyLists = convertPropertyLists(dbEntity);
        element.setProperties(convertPropertyLists);
        element.setTitle(getTitle(convertPropertyLists, dbEntity.getType()));
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

    private static String getTitle(Map<String, List<String>> convertPropertyLists, String type) {
        String nameProperty = getName(type);
        if (convertPropertyLists != null && !convertPropertyLists.isEmpty()) {
            for (String key : convertPropertyLists.keySet()) {
                if (key.equalsIgnoreCase(nameProperty)) {
                    return convertPropertyLists.get(key).iterator().next();
                }
            }

        }
        return null;
    }

    private static String getName(String type) {
        if (specialNamePropertyTypes.containsKey(type)) {
            return specialNamePropertyTypes.get(type);
        }
        return type + "_name";
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

    public static CnaTreeElement entityForElement(Velement velement) {
        CnaTreeElement cnaElement = new CnaTreeElement();
        Entity cnaEntity = new Entity();
        cnaEntity.setEntitytype(velement.getType()).setUuid(velement.getUuid());

        cnaElement.setEntity(cnaEntity);
        cnaElement.setDbid(velement.getDbId());
        cnaElement.setUuid(velement.getUuid());
        cnaElement.setType(velement.getType());
        cnaElement.setScopeId(velement.getScopeId());
        cnaElement.setParentId(velement.getParentId());
        cnaElement.setExtId(velement.getExtId());
        cnaElement.setSourceId(velement.getSourceId());
        cnaEntity.setPropertyLists(convertProperties(velement, cnaEntity));

        return cnaElement;
    }

    public static Set<PropertyList> convertProperties(Velement velement, Entity cnaEntity) {
        if (velement.getProperties() == null) {
            return new HashSet<>();
        }

        velement.getProperties().put(getName(velement.getType()),
                Arrays.asList(new String[]{velement.getTitle()}));
        Set<PropertyList> lists = new HashSet<>();

        for (Entry<String, List<String>> entry : velement.getProperties().entrySet()) {
            PropertyList list = new PropertyList()
                    .setEntity(cnaEntity)
                    .setUuid(cnaEntity.getUuid()).setListIdx(entry.getKey());
            int i = 0;
            list.setProperties(new HashSet<>());
            for (String p : entry.getValue()) {
                Property property = new Property()
                        .setPropertytype(entry.getKey())
                        .setPropertiesIdx(i++)
                        .setPropertyvalue(p)
                        .setPropertyList(list);
                list.getProperties().add(property);
            }
            lists.add(list);
        }
        return lists;
    }

}
