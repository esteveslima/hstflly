package com.eml.hstfll.features.property.adapters.gateways.database.daos;

import com.eml.hstfll.features.property.domain.entities.PropertyEntity;
import com.eml.hstfll.features.property.application.exceptions.PropertyNotFoundRuntimeException;
import com.eml.hstfll.features.property.application.interfaces.gateways.database.PropertyDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
@Qualifier("propertyJpaDAO")
public class PropertyJpaDAO implements PropertyDAO {

    private EntityManager entityManager;

    @Autowired
    public PropertyJpaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PropertyEntity register(PropertyEntity entity) {
        int idInsertOperation = 0;
        entity.setId(idInsertOperation);

        PropertyEntity result = this.entityManager.merge(entity);

        return result;
    }

    @Override
    public PropertyEntity findById(Integer id) throws PropertyNotFoundRuntimeException {
        String jpqlQuery = "SELECT properties FROM PropertyEntity properties WHERE properties.id = :idValue";

        TypedQuery<PropertyEntity> typedQuery = entityManager.createQuery(jpqlQuery,PropertyEntity.class)
                .setParameter("idValue", id);

        try{
            PropertyEntity result = typedQuery.getSingleResult();
            return result;
        } catch(NoResultException exception) {
            throw new PropertyNotFoundRuntimeException(new PropertyNotFoundRuntimeException.ExceptionPayload(id), exception);
        }
    }

}