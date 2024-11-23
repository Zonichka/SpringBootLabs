package ru.golovina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.golovina.MySecondTestAppSpringBoot.model.Response;

import java.util.UUID;

@Service
@Qualifier("ModifyOperationUidResponceService")
public class ModifyOperationUidResponceService
        implements ModifyResponceService{
    @Override
    public Response modify(Response response) {
        UUID uuid = UUID.randomUUID();
        response.setOperationUid(uuid.toString());

        return response;
    }
}
