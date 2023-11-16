package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.application.interfaces.gateways.database.PropertyDAO;
import com.eml.hstfll.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.dtos.UpdatePropertyUseCaseDTO;
import com.eml.hstfll.features.property.domain.entities.PropertyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("updatePropertyUseCase")
public class UpdatePropertyUseCase implements UseCase<UpdatePropertyUseCaseDTO.Params, UpdatePropertyUseCaseDTO.Result> {

    private PropertyDAO propertyDAO;

    @Autowired
    public UpdatePropertyUseCase(@Qualifier("propertyJpaDAO") PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Transactional
    public UpdatePropertyUseCaseDTO.Result execute(UpdatePropertyUseCaseDTO.Params params) {

        PropertyEntity propertyToUpdate = this.propertyDAO.findById(params.id);
        if(params.payload.name != null) propertyToUpdate.setName(params.payload.name);
        if(params.payload.location != null) propertyToUpdate.setLocation(params.payload.location);

        PropertyEntity updatedProperty = this.propertyDAO.updateProperty(propertyToUpdate, params.hostUserId);

        //OBS.: at this point it would be good to notify users about this change
        return new UpdatePropertyUseCaseDTO.Result(updatedProperty.getId());
    }

}
