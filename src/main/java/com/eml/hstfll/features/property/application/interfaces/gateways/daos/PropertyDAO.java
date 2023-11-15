package com.eml.hstfll.features.property.application.interfaces.gateways.daos;

import com.eml.hstfll.features.property.adapters.gateways.database.models.PropertyEntity;

public interface PropertyDAO {

    public PropertyEntity register(PropertyEntity entity);

    public PropertyEntity findById(Integer id);

}
