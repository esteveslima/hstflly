package com.eml.hstflly.features.property.application.usecases;

import com.eml.hstflly.features.property.domain.entities.PropertyEntity;
import com.eml.hstflly.features.property.application.interfaces.gateways.database.PropertyDAO;
import com.eml.hstflly.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.GetPropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("getPropertyUseCase")
public class GetPropertyUseCase implements UseCase<GetPropertyUseCaseDTO.Params, GetPropertyUseCaseDTO.Result> {


    private PropertyDAO propertyDAO;

    @Autowired
    public GetPropertyUseCase(@Qualifier("propertyJpaDAO") PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Transactional
    public GetPropertyUseCaseDTO.Result execute(GetPropertyUseCaseDTO.Params params) {
        PropertyEntity entityFound = this.propertyDAO.findById(params.id);

        return new GetPropertyUseCaseDTO.Result(
                entityFound.getName(),
                entityFound.getLocation()
        );
    }

}
