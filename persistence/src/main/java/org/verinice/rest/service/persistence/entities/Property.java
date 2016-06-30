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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dbid", nullable = false)
    private long dbid;

    @Column(name = "propertytype", nullable = true)
    private String propertytype;

    @Column(name = "propertyvalue", nullable = true)
    private String propertyvalue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "properties_id", referencedColumnName = "dbid")
    private PropertyList propertyList;

    @Column(name = "properties_idx", nullable = false)
    private int properties_idx;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
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

    public int getProperties_idx() {
        return properties_idx;
    }

    public void setProperties_idx(int properties_idx) {
        this.properties_idx = properties_idx;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (dbid ^ (dbid >>> 32));
        result = prime * result + properties_idx;
        result = prime * result + ((propertyList == null) ? 0 : propertyList.hashCode());
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
        if (dbid != other.dbid)
            return false;
        if (properties_idx != other.properties_idx)
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
        return "Property [dbid=" + dbid + ", propertytype=" + propertytype + ", propertyvalue="
                + propertyvalue + ", properties_idx="
                + properties_idx + "]";
    }

}
