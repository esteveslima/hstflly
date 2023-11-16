package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.domain.entities.PropertyEntity;
import com.eml.hstfll.features.property.application.interfaces.gateways.database.PropertyDAO;
import com.eml.hstfll.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.dtos.RegisterPropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("registerPropertyUseCase")
public class RegisterPropertyUseCase implements UseCase<RegisterPropertyUseCaseDTO.Params, RegisterPropertyUseCaseDTO.Result>  {

    private PropertyDAO propertyDAO;

    @Autowired
    public RegisterPropertyUseCase(@Qualifier("propertyJpaDAO") PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Transactional
    public RegisterPropertyUseCaseDTO.Result execute(RegisterPropertyUseCaseDTO.Params params) {
        PropertyEntity entity = new PropertyEntity();
        entity.setHostId(params.userId);
        entity.setName(params.payload.name);
        entity.setLocation(params.payload.location);

        PropertyEntity createdProperty = this.propertyDAO.register(entity);

        return new RegisterPropertyUseCaseDTO.Result(createdProperty.getId());
    }

}
