package ru.golovina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.golovina.MySecondTestAppSpringBoot.model.Response;
@Service
public interface ModifyResponceService {
    Response modify(Response responce);
}
