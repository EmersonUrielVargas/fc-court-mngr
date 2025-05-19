package com.foodcourt.court.infrastructure.out.rest.client;

import com.foodcourt.court.infrastructure.configuration.FeignClientConfig;
import com.foodcourt.court.infrastructure.out.rest.dto.response.UserRoleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="users-mngr", url = "${external.api.users-mngr.url}", configuration = FeignClientConfig.class)
public interface IUserRestClient {

    @GetMapping(value = "/role/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    UserRoleResponseDto getUserRole(@PathVariable Long id);

}
