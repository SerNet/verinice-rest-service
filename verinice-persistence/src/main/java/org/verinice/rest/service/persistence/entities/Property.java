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
package org.verinice.rest.service.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.verinice.rest.service.persistence.entities.Property.PropertyId;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Entity
@Table(name = "properties")
@IdClass(PropertyId.class)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "properties_id", nullable = false)
    private long propertiesId;

    @Id
    @Column(name = "propertiesIdx", nullable = false)
    private int propertiesIdx;

    @Column(name = "propertytype", nullable = false)
    private String propertytype;

    @Column(name = "propertyvalue", nullable = false)
    private String propertyvalue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "properties_id", referencedColumnName = "dbid", insertable = false, updatable = false)
    // @Transient
    private PropertyList propertyList;

    public long getPropertiesId() {
        return propertiesId;
    }

    public void setPropertiesId(long propertiesId) {
        this.propertiesId = propertiesId;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public String getPropertyvalue() {
        return propertyvalue;
    }

    public void setPropertyvalue(String propertyvalue) {
        this.propertyvalue = propertyvalue;
    }

    public PropertyList getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(PropertyList propertyList) {
        this.propertyList = propertyList;
    }

    public int getPropertiesIdx() {
        return propertiesIdx;
    }

    public void setPropertiesIdx(int propertiesIdx) {
        this.propertiesIdx = propertiesIdx;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (propertiesId ^ (propertiesId >>> 32));
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
        if (propertiesId != other.propertiesId)
            return false;
        if (propertiesIdx != other.propertiesIdx)
            return false;
        if (propertyList == null) {
            if (other.propertyList != null)
                return false;
        } else if (!propertyList.equals(other.propertyList))
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

    @Override
    public String toString() {
        return "Property [propertiesId=" + propertiesId + ", propertytype=" + propertytype + ", propertyvalue="
                + propertyvalue + ", propertiesIdx="
                + propertiesIdx + "]";
    }

    /**
     * Needed to support multiple primary keys
     * 
     * @author Ruth Motza <rm[at]sernet[dot]de>
     */
    public static class PropertyId implements Serializable {

        private static final long serialVersionUID = 1L;

        long propertiesId;

        int propertiesIdx;

        public PropertyId() {
        }

        public PropertyId(long propertiesId, int propertiesIdx) {
            super();
            this.propertiesId = propertiesId;
            this.propertiesIdx = propertiesIdx;
        }

        public long getPropertiesId() {
            return propertiesId;
        }

        public void setPropertiesId(long propertiesId) {
            this.propertiesId = propertiesId;
        }

        public int getPropertiesIdx() {
            return propertiesIdx;
        }

        public void setPropertiesIdx(int propertiesIdx) {
            this.propertiesIdx = propertiesIdx;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (propertiesId ^ (propertiesId >>> 32));
            result = prime * result + propertiesIdx;
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
            if (propertiesId != other.propertiesId)
                return false;
            if (propertiesIdx != other.propertiesIdx)
                return false;
            return true;
        }

    }

}
