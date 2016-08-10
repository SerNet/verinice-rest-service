/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza.
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
 *     Ruth Motza <rm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package org.verinice.persistence.entities;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@javax.persistence.Entity
@Table(name = "entity")
public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dbid", nullable = false)
    private long dbid;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "entitytype")
    private String entitytype;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entity")
    private Set<PropertyList> propertyLists;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public Set<PropertyList> getPropertyLists() {
        return propertyLists;
    }

    public void setPropertyLists(Set<PropertyList> propertyLists) {
        this.propertyLists = propertyLists;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(String entitytype) {
        this.entitytype = entitytype;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (dbid ^ (dbid >>> 32));
        result = prime * result + ((entitytype == null) ? 0 : entitytype.hashCode());
        result = prime * result + ((propertyLists == null) ? 0 : propertyLists.hashCode());
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
        Entity other = (Entity) obj;
        if (dbid != other.dbid) {
            return false;
        }
        if (entitytype == null) {
            if (other.entitytype != null) {
                return false;
            }
        } else if (!entitytype.equals(other.entitytype)) {
            return false;
        }
        if (propertyLists == null) {
            if (other.propertyLists != null) {
                return false;
            }
        } else if (!propertyLists.equals(other.propertyLists)) {
            return false;
        }
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
        return "Entity [dbid=" + dbid + ", uuid=" + uuid + ", entitytype=" + entitytype
                + ",\n propertyLists="
                + StringUtils.join(propertyLists, "\n\t")
                + "\n]";
    }

}
