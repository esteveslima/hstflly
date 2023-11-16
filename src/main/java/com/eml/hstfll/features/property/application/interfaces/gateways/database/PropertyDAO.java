package com.eml.hstfll.features.property.application.interfaces.gateways.database;

import com.eml.hstfll.features.property.application.exceptions.PropertyNotFoundException;
import com.eml.hstfll.features.property.domain.entities.PropertyEntity;

public interface PropertyDAO {

    public PropertyEntity register(PropertyEntity entity);

    public PropertyEntity findById(Integer id) throws PropertyNotFoundException;

    public PropertyEntity updateProperty(PropertyEntity entity, Integer hostUserId) throws PropertyNotFoundException;

    public void deleteProperty(Integer id, Integer hostUserId) throws PropertyNotFoundException;

}
