/*
 * Copyright 2016 Moritz Reiter
 *
 * This file is part of Verinice.
 *
 * Verinice is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Verinice is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Verinice. If not, see http://www.gnu.org/licenses/.
 */

package org.verinice.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Java Bean for representing the {@code configuration} table in the Verinice database.
 *
 * @author Moritz Reiter
 */
@Entity
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dbid", nullable = false)
    private long dbid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "dbid")
    private CnaTreeElement cnaTreeElement;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_id", referencedColumnName = "dbid")
    private org.verinice.persistence.entities.Entity entity;

    @Column(name = "person_id", insertable=false, updatable=false)
    private long personId;

    @Column(name = "entity_id", insertable=false, updatable=false)
    private long entityId;

    protected Configuration() {}

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public CnaTreeElement getCnaTreeElement() {
        return cnaTreeElement;
    }

    public void setCnaTreeElement(CnaTreeElement cnaTreeElement) {
        this.cnaTreeElement = cnaTreeElement;
    }

    public org.verinice.persistence.entities.Entity getEntity() {
        return entity;
    }

    public void setEntity(org.verinice.persistence.entities.Entity entity) {
        this.entity = entity;
    }
}
