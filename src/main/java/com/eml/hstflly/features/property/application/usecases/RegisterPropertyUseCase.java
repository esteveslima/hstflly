package com.eml.hstflly.features.property.application.usecases;

import com.eml.hstflly.features.property.domain.entities.PropertyEntity;
import com.eml.hstflly.features.property.application.interfaces.gateways.database.PropertyDAO;
import com.eml.hstflly.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.RegisterPropertyUseCaseDTO;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
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
        UserEntity hostEntity = new UserEntity();
        hostEntity.setId(params.hostUserId);
        entity.setHostUser(hostEntity);
        entity.setName(params.payload.name);
        entity.setLocation(params.payload.location);

        PropertyEntity createdProperty = this.propertyDAO.register(entity);

        return new RegisterPropertyUseCaseDTO.Result(createdProperty.getId());
    }

}
