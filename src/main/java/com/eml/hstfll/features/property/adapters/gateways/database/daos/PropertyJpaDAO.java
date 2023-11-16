package com.eml.hstfll.features.property.adapters.gateways.database.daos;

import com.eml.hstfll.features.property.domain.entities.PropertyEntity;
import com.eml.hstfll.features.property.application.exceptions.PropertyNotFoundException;
import com.eml.hstfll.features.property.application.interfaces.gateways.database.PropertyDAO;
import jakarta.persistence.*;
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
    public PropertyEntity findById(Integer id) throws PropertyNotFoundException {
        String jpqlQuery = """
        SELECT properties 
        FROM PropertyEntity properties 
        WHERE properties.id = :idValue
        """;
//TODO: load with bookings relations
        TypedQuery<PropertyEntity> typedQuery = entityManager.createQuery(jpqlQuery,PropertyEntity.class)
                .setParameter("idValue", id);

        try{
            PropertyEntity result = typedQuery.getSingleResult();
            return result;
        } catch(NoResultException exception) {
            throw new PropertyNotFoundException(new PropertyNotFoundException.ExceptionPayload(id), exception);
        }
    }

    @Override
    public PropertyEntity updateProperty(PropertyEntity entity, Integer requesterUserId) throws PropertyNotFoundException {
        String jpqlQuery = """
        UPDATE PropertyEntity p
        SET
        p.name = :nameValue,
        p.location = :locationValue
        WHERE p.id = :idValue and p.host.id = :hostIdValue
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", entity.getId())
                .setParameter("nameValue", entity.getName())
                .setParameter("locationValue", entity.getLocation())
                .setParameter("hostIdValue", requesterUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new PropertyNotFoundException();
        }

        return entity;
    }

    @Override
    public void deleteProperty(Integer id, Integer requesterUserId) throws PropertyNotFoundException {
        String jpqlQuery = """
        DELETE FROM PropertyEntity properties
        WHERE properties.id = :idValue and properties.host.id = :hostIdValue
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", id)
                .setParameter("hostIdValue", requesterUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new PropertyNotFoundException();
        }

        return;
    }

}