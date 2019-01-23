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
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package org.verinice.model;

import java.util.Objects;

/**
 * Represent a link in the dynamic object model of verinice.
 * This is a pojo class to transfer data only without other methods.
 */
public class Vlink {
    private long dependantId;
    private long dependencyId;
    private String typeId;
    private Integer linkType;
    private String comment;
    private Integer riskconfidentiality;
    private Integer riskintegrity;
    private Integer riskavailability;
    private Integer riskcwithcontrols;
    private Integer riskiwithcontrols;
    private Integer riskawithcontrols;
    private String risktreatmentvalue;

    @Override public String toString() {
        return "Vlink [dependantId=" + dependantId + ", dependencyId=" + dependencyId
                + ", typeId=" + typeId + ",\n link_type=" + linkType + "]";
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vlink vlink = (Vlink) o;
        return dependantId == vlink.dependantId && dependencyId == vlink.dependencyId && Objects
                .equals(typeId, vlink.typeId) && Objects.equals(linkType, vlink.linkType) && Objects
                .equals(comment, vlink.comment) && Objects
                .equals(riskconfidentiality, vlink.riskconfidentiality) && Objects
                .equals(riskintegrity, vlink.riskintegrity) && Objects
                .equals(riskavailability, vlink.riskavailability) && Objects
                .equals(riskcwithcontrols, vlink.riskcwithcontrols) && Objects
                .equals(riskiwithcontrols, vlink.riskiwithcontrols) && Objects
                .equals(riskawithcontrols, vlink.riskawithcontrols) && Objects
                .equals(risktreatmentvalue, vlink.risktreatmentvalue);
    }

    @Override public int hashCode() {
        return Objects
                .hash(dependantId, dependencyId, typeId, linkType, comment, riskconfidentiality,
                        riskintegrity, riskavailability, riskcwithcontrols, riskiwithcontrols,
                        riskawithcontrols, risktreatmentvalue);
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
}
