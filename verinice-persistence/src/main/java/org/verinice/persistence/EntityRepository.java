package org.verinice.persistence;

import org.springframework.data.repository.CrudRepository;
import org.verinice.persistence.entities.Entity;

interface EntityRepository extends CrudRepository<Entity, Long> {
}
