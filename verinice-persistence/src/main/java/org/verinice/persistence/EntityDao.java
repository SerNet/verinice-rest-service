package org.verinice.persistence;

import org.verinice.persistence.entities.Entity;

/**
 * Data access object (DAO) for database entity {@link org.verinice.persistence.entities.Entity}
 * which provides methods to read entities.
 *
 * @author Alexander Ben Nasrallah
 */
public interface EntityDao {
    Entity findByElementId(long elementId);
    Entity findByDbid(long dbid);
}
