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

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * TEST.
 *
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@javax.persistence.Entity
@FilterDefs({
    @FilterDef(name = "userReadAccessFilter", parameters =
        @ParamDef(name = "currentAccountGroups", type = "string")),
    @FilterDef(name = "scopeFilter", parameters = @ParamDef(name = "scopeId", type = "int")),
    @FilterDef(name = "testFilter")})
@Filters({
    @Filter(name = "userReadAccessFilter", condition = "(\n"
            + "object_type = 'bsimodel' or \n"
            + "object_type = 'iso27kmodel' or \n"
            + "exists (select p.dbid from permission p where \n"
            + "  p.cte_id = dbId and \n"
            + "  p.role in (:currentAccountGroups))\n"
            + ")"),
    @Filter(name = "scopeFilter", condition = "(\n"
            + "object_type = 'bsimodel' or \n"
            + "object_type = 'iso27kmodel' or \n"
            + "scope_id = :scopeId\n"
            + ")"),
    @Filter(name = "testFilter", condition = "uuid = 'baf0d970-b3f7-464d-86f0-4200f33593cc'")})
@Table(name = "cnatreeelement")
public class CnaTreeElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dbid", nullable = false)
    private long dbid;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "object_type", nullable = false)
    private String type;

    @Column(name = "sourceid", nullable = true)
    private String sourceId;

    @Column(name = "extid", nullable = true)
    private String extId;

    @Column(name = "parent", nullable = true)
    private Integer parentId;

    @Column(name = "scope_id", nullable = true)
    private Integer scopeId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id", referencedColumnName = "dbid")
    private Entity entity;
    
    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (dbid ^ (dbid >>> 32));
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        CnaTreeElement other = (CnaTreeElement) obj;
        if (dbid != other.dbid) {
            return false;
        }
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
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
        return "CnATreeElement [dbid=" + dbid + ", uuid=" + uuid + ", type=" + type + ",\n entity="
                + entity + "]";
    }


}
