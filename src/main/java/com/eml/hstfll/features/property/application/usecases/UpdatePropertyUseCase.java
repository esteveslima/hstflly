package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.application.interfaces.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.RegisterPropertyUseCaseDTO;
import com.eml.hstfll.features.property.application.interfaces.usecases.UpdatePropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("updatePropertyUseCase")
public class UpdatePropertyUseCase implements UseCase<UpdatePropertyUseCaseDTO.Params, UpdatePropertyUseCaseDTO.Result> {


    @Autowired
    public UpdatePropertyUseCase() {

    }

    @Transactional
    public UpdatePropertyUseCaseDTO.Result execute(UpdatePropertyUseCaseDTO.Params params) {

        return new UpdatePropertyUseCaseDTO.Result(params.id);
    }

}
