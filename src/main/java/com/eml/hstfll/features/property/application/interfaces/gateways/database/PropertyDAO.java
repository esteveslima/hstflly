package com.eml.hstfll.features.property.application.interfaces.gateways.database;

import com.eml.hstfll.features.property.application.exceptions.PropertyNotFoundRuntimeException;
import com.eml.hstfll.features.property.domain.entities.PropertyEntity;

public interface PropertyDAO {

    public PropertyEntity register(PropertyEntity entity);

    public PropertyEntity findById(Integer id) throws PropertyNotFoundRuntimeException;

}
