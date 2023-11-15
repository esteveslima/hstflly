package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.application.interfaces.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.DeletePropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("deletePropertyUseCase")
public class DeletePropertyUseCase implements UseCase<DeletePropertyUseCaseDTO.Params, DeletePropertyUseCaseDTO.Result> {


    @Autowired
    public DeletePropertyUseCase() {

    }

    @Transactional
    public DeletePropertyUseCaseDTO.Result execute(DeletePropertyUseCaseDTO.Params params) {

        return new DeletePropertyUseCaseDTO.Result(params.id);
    }

}
