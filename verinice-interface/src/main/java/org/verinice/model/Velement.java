/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
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
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package org.verinice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represent a element/object in the dynamic object model of verinice.
 * Based an the attribute type instances can be every object type in verinice.
 * This is a pojo class to transfer data only without other methods.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
public class Velement {

    @Pattern(regexp = "[a-fA-F\\d]{8}(-[a-fA-F\\d]{4}){3}-[a-fA-F\\d]{12}",
            message = "String has to be a valid UUID.")
    @Schema(description = "UUID of the element", example = "c755fdd5-44d7-46b0-a014-19a57e098ccb")
    private String uuid;

    @Schema(example = "person-iso")
    private String type;

    @Schema(example = "Musterfrau")
    private String title;

    private String sourceId;

    private String extId;

    private int parentId;

    private int scopeId;

    private long dbid;
    @Schema(example = "{\n" +
            "        \"person-iso_document\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person_abbr\": [\n" +
            "            \"MM\"\n" +
            "        ],\n" +
            "        \"schrift_bestell_document\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"schrift_bestell\": [\n" +
            "            \"0\"\n" +
            "        ],\n" +
            "        \"person-iso_telefon\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_email\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_surname\": [\n" +
            "            \"Musterfrau\"\n" +
            "        ],\n" +
            "        \"person_info_dsb\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_erlaeuterung\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_tag\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"fachkunde\": [\n" +
            "            \"0\"\n" +
            "        ],\n" +
            "        \"person-iso_anrede\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_orgeinheit\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"Kontaktdaten_DSB\": [\n" +
            "            \"\"\n" +
            "        ],\n" +
            "        \"person-iso_name\": [\n" +
            "            \"Maria\"\n" +
            "        ],\n" +
            "        \"Datum_bestell\": [\n" +
            "            \"1511793317251\"\n" +
            "        ],\n" +
            "        \"ausbild_sonstiges\": [\n" +
            "            \"\"\n" +
            "        ]\n" +
            "    }")
    private Map<String, List<String>> properties;

    public Velement() {
        super();
    }

    public Velement(String uuid, String type) {
        super();
        this.uuid = uuid;
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }

    public Map<String, List<String>> getProperties() {
        if(properties==null) {
            properties = createPropertyMap();
        }
        return properties;
    }
    
    public void setProperties(Map<String, List<String>> properties) {
        this.properties = properties;
    }
    
    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public void addProperty(String key, List<String> value) {
        getProperties().put(key, value);
    }

    private Map<String, List<String>> createPropertyMap() {
        return new HashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Velement other = (Velement) obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Velement [type=" + type + ", title=" + title + ", properties=" + properties
                + ", uuid=" + uuid + "]";
    }
   
}
