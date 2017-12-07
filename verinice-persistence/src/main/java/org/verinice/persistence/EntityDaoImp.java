package org.verinice.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.verinice.persistence.entities.Entity;

@Service
@EnableWebSecurity
public class EntityDaoImp extends Dao implements EntityDao {

    @Autowired
    private EntityRepository elementRepository;

    @Override
    public Entity findByElementId(long dbid) {
        enableAccessControlFilters();
        long entityId = selectEntityIdWithElement(dbid);
        return findByDbid(entityId);
    }

    @Override
    public Entity findByDbid(long dbid) {
        enableAccessControlFilters();
        return elementRepository.findOne(dbid);
    }

    private long selectEntityIdWithElement(long elementDbid) {
        return entityManager.createQuery("SELECT c.entity.dbid FROM CnaTreeElement c WHERE c.dbid = :dbid", Long.class)
                .setParameter("dbid", elementDbid)
                .getSingleResult();
    }
}
