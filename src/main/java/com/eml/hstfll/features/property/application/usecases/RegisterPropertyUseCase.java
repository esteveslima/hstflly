package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.application.interfaces.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.GetPropertyUseCaseDTO;
import com.eml.hstfll.features.property.application.interfaces.usecases.RegisterPropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("registerPropertyUseCase")
public class RegisterPropertyUseCase implements UseCase<RegisterPropertyUseCaseDTO.Params, RegisterPropertyUseCaseDTO.Result>  {


    @Autowired
    public RegisterPropertyUseCase() {

    }

    @Transactional
    public RegisterPropertyUseCaseDTO.Result execute(RegisterPropertyUseCaseDTO.Params params) {

        int mockId = 123;
        return new RegisterPropertyUseCaseDTO.Result(mockId);
    }

}
