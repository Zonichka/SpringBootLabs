package ru.golovina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.golovina.MySecondTestAppSpringBoot.model.Response;
import ru.golovina.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponceService")
public class ModifySystemTimeResponceService
        implements ModifyResponceService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));

        return response;
    }
}
