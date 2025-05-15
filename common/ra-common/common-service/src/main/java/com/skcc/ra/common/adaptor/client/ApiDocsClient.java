package com.skcc.ra.common.adaptor.client;

import com.skcc.ra.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "Api-service", url = "url-placeholder", configuration = {FeignConfig.class})
public interface ApiDocsClient {

    @GetMapping
    String getApiDocs(URI uri);

}
