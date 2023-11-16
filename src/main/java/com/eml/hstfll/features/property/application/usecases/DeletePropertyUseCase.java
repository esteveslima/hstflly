package com.eml.hstfll.features.property.application.usecases;

import com.eml.hstfll.features.property.application.interfaces.gateways.database.PropertyDAO;
import com.eml.hstfll.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.dtos.DeletePropertyUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("deletePropertyUseCase")
public class DeletePropertyUseCase implements UseCase<DeletePropertyUseCaseDTO.Params, DeletePropertyUseCaseDTO.Result> {

    private PropertyDAO propertyDAO;

    @Autowired
    public DeletePropertyUseCase(@Qualifier("propertyJpaDAO") PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Transactional
    public DeletePropertyUseCaseDTO.Result execute(DeletePropertyUseCaseDTO.Params params) {
        this.propertyDAO.deleteProperty(params.id, params.userId);

        //OBS.: at this point it would be good to notify users about this change
        return new DeletePropertyUseCaseDTO.Result(params.id);
    }

}
