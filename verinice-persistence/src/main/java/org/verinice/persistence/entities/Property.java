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

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;

import org.verinice.persistence.entities.Property.PropertyId;

/**
 * Entity class for table properties.
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Entity
@Table(name = "properties")
@IdClass(PropertyId.class)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "properties_id", referencedColumnName = "dbid", insertable = false, updatable = false)
    private PropertyList propertyList;

    @Id
    @Column(name = "propertiesIdx", nullable = false)
    private int propertiesIdx;

    @Column(name = "propertytype", nullable = false)
    private String propertytype;

    @Column(name = "propertyvalue", nullable = false)
    private String propertyvalue;

    public PropertyList getPropertyList() {
        return propertyList;
    }

    public Property setPropertyList(PropertyList propertyList) {
        this.propertyList = propertyList;
        return this;
    }

    public int getPropertiesIdx() {
        return propertiesIdx;
    }

    public Property setPropertiesIdx(int propertiesIdx) {
        this.propertiesIdx = propertiesIdx;
        return this;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public Property setPropertytype(String propertytype) {
        this.propertytype = propertytype;
        return this;
    }

    public String getPropertyvalue() {
        return propertyvalue;
    }

    public Property setPropertyvalue(String propertyvalue) {
        this.propertyvalue = propertyvalue;
        return this;
    }

    @Override
    public String toString() {
        return "Property ["
//                + "propertiesId=" + propertiesId
                + ", propertiesIdx=" + propertiesIdx
                + ", propertytype=" + propertytype
                + ", propertyvalue=" + propertyvalue
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + propertiesIdx;
        result = prime * result + ((propertytype == null) ? 0 : propertytype.hashCode());
        result = prime * result + ((propertyvalue == null) ? 0 : propertyvalue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Property other = (Property) obj;
        if (propertiesIdx != other.propertiesIdx)
            return false;
        if (propertytype == null) {
            if (other.propertytype != null)
                return false;
        } else if (!propertytype.equals(other.propertytype))
            return false;
        if (propertyvalue == null) {
            if (other.propertyvalue != null)
                return false;
        } else if (!propertyvalue.equals(other.propertyvalue))
            return false;
        return true;
    }


    /**
     * Needed to support multiple primary keys
     * 
     * @author Ruth Motza <rm[at]sernet[dot]de>
     */
    public static class PropertyId implements Serializable {


        private static final long serialVersionUID = 1L;

        PropertyList propertyList;

        int propertiesIdx;

        public PropertyId() {
            // Empty constructor for JPA
        }

        public PropertyId(PropertyList propertyList, int propertiesIdx) {
            super();
            this.propertyList = propertyList;
            this.propertiesIdx = propertiesIdx;
        }

        public PropertyList getPropertyList() {
            return propertyList;
        }

        public PropertyId setPropertyList(PropertyList propertyList) {
            this.propertyList = propertyList;
            return this;
        }

        public int getPropertiesIdx() {
            return propertiesIdx;
        }

        public PropertyId setPropertiesIdx(int propertiesIdx) {
            this.propertiesIdx = propertiesIdx;
            return this;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + propertiesIdx;
            result = prime * result + ((propertyList == null) ? 0 : propertyList.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PropertyId other = (PropertyId) obj;
            if (propertiesIdx != other.propertiesIdx)
                return false;
            if (propertyList == null) {
                if (other.propertyList != null)
                    return false;
            } else if (!propertyList.equals(other.propertyList))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "PropertyId{" +
                    "propertyList=" + propertyList +
                    ", propertiesIdx=" + propertiesIdx +
                    '}';
        }
    }

}
