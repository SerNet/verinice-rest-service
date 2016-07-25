
package org.verinice.persistence;

import org.verinice.persistence.entities.CnaTreeElement;

import java.util.List;

public interface VeriniceElementDao {

    /**
     * TODO.
     *
     * @param uuid TODO
     * @return TODO
     */
    CnaTreeElement findByUuid(String uuid);

    /**
     * TODO.
     *
     * @param firstResult TODO
     * @param size TODO
     * @param key TODO
     * @param value TODO
     * @param scopeId TODO
     * @return TODO
     */
    List<CnaTreeElement> findByCriteria(Integer firstResult, Integer size, String key, String value,
            Integer scopeId);

}