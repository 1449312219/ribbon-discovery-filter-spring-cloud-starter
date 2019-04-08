package io.jmnarloch.spring.cloud.ribbon.gray;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RegionController {

    private static final String URL_FORMATE = "%sapps/%s/%s/metadata?" + Constant.REGION + "=%s";

    @Autowired
    private EurekaInstanceConfigBean instanceConfig;

    @Autowired
    private EurekaClientConfigBean clientConfig;

    @Autowired
    @Qualifier("metadataUpdateRestTemplate")
    private RestTemplate rest;

    @RequestMapping(path = "/region", method = RequestMethod.PUT)
    public void setRegion(@RequestBody String region) {
        List<String> serverUrls = clientConfig.getEurekaServerServiceUrls(clientConfig.getAvailabilityZones(clientConfig.getRegion())[0]);
        for (String url : serverUrls) {
            try {
                rest.put(String.format(URL_FORMATE, url, instanceConfig.getAppname(), instanceConfig.getInstanceId(), region), null);
                break;
            } catch (Exception e) {
            }
        }
    }

}