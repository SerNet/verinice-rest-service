/*******************************************************************************
 * Copyright (c) 2018 Alexander Ben Nasrallah.
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

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for table cnalink.
 */
@javax.persistence.Entity
@Table(name = "cnalink")
@IdClass(CnaLink.CompositeId.class)
public class CnaLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dependant_id", nullable = false)
    private long dependantId;

    @Id
    @Column(name = "dependency_id", nullable = false)
    private long dependencyId;

    @Id
    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "linktype")
    private Integer linkType;

    @Column(name = "comment")
    private String comment;

    @Column(name = "riskconfidentiality")
    private Integer riskconfidentiality;

    @Column(name = "riskintegrity")
    private Integer riskintegrity;

    @Column(name = "riskavailability")
    private Integer riskavailability;

    @Column(name = "riskcwithcontrols")
    private Integer riskcwithcontrols;

    @Column(name = "riskiwithcontrols")
    private Integer riskiwithcontrols;

    @Column(name = "riskawithcontrols")
    private Integer riskawithcontrols;

    @Column(name = "risktreatmentvalue")
    private String risktreatmentvalue;

    @Override
    public String toString() {
        return "CnaLink [dependantId=" + dependantId + ", dependencyId=" + dependencyId
                + ", typeId=" + typeId + ",\n link_type=" + linkType + "]";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getDependantId() {
        return dependantId;
    }

    public void setDependantId(long dependantId) {
        this.dependantId = dependantId;
    }

    public long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(long dependencyId) {
        this.dependencyId = dependencyId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRiskconfidentiality() {
        return riskconfidentiality;
    }

    public void setRiskconfidentiality(Integer riskconfidentiality) {
        this.riskconfidentiality = riskconfidentiality;
    }

    public Integer getRiskintegrity() {
        return riskintegrity;
    }

    public void setRiskintegrity(Integer riskintegrity) {
        this.riskintegrity = riskintegrity;
    }

    public Integer getRiskavailability() {
        return riskavailability;
    }

    public void setRiskavailability(Integer riskavailability) {
        this.riskavailability = riskavailability;
    }

    public Integer getRiskcwithcontrols() {
        return riskcwithcontrols;
    }

    public void setRiskcwithcontrols(Integer riskcwithcontrols) {
        this.riskcwithcontrols = riskcwithcontrols;
    }

    public Integer getRiskiwithcontrols() {
        return riskiwithcontrols;
    }

    public void setRiskiwithcontrols(Integer riskiwithcontrols) {
        this.riskiwithcontrols = riskiwithcontrols;
    }

    public Integer getRiskawithcontrols() {
        return riskawithcontrols;
    }

    public void setRiskawithcontrols(Integer riskawithcontrols) {
        this.riskawithcontrols = riskawithcontrols;
    }

    public String getRisktreatmentvalue() {
        return risktreatmentvalue;
    }

    public void setRisktreatmentvalue(String risktreatmentvalue) {
        this.risktreatmentvalue = risktreatmentvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CnaLink cnaLink = (CnaLink) o;
        return dependantId == cnaLink.dependantId && dependencyId == cnaLink.dependencyId
                && Objects.equals(typeId, cnaLink.typeId)
                && Objects.equals(linkType, cnaLink.linkType)
                && Objects.equals(comment, cnaLink.comment)
                && Objects.equals(riskconfidentiality, cnaLink.riskconfidentiality)
                && Objects.equals(riskintegrity, cnaLink.riskintegrity)
                && Objects.equals(riskavailability, cnaLink.riskavailability)
                && Objects.equals(riskcwithcontrols, cnaLink.riskcwithcontrols)
                && Objects.equals(riskiwithcontrols, cnaLink.riskiwithcontrols)
                && Objects.equals(riskawithcontrols, cnaLink.riskawithcontrols)
                && Objects.equals(risktreatmentvalue, cnaLink.risktreatmentvalue);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(dependantId, dependencyId, typeId, linkType, comment, riskconfidentiality,
                        riskintegrity, riskavailability, riskcwithcontrols, riskiwithcontrols,
                        riskawithcontrols, risktreatmentvalue);
    }

    public static class CompositeId implements Serializable {

        private static final long serialVersionUID = 1L;

        private long dependantId;
        private long dependencyId;
        private String typeId;

        public CompositeId() {
        }

        public CompositeId(long dependantId, long dependencyId, String typeId) {
            this.dependantId = dependantId;
            this.dependencyId = dependencyId;
            this.typeId = typeId;
        }

        public long getDependantId() {
            return dependantId;
        }

        public void setDependantId(long dependantId) {
            this.dependantId = dependantId;
        }

        public long getDependencyId() {
            return dependencyId;
        }

        public void setDependencyId(long dependencyId) {
            this.dependencyId = dependencyId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            CompositeId that = (CompositeId) o;
            return dependantId == that.dependantId && dependencyId == that.dependencyId
                    && Objects.equals(typeId, that.typeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dependantId, dependencyId, typeId);
        }
    }
}
