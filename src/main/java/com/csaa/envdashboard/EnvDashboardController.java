package com.csaa.envdashboard;

import com.csaa.envdashboard.envconfig.EnvConfigService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;

import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@CrossOrigin
@Controller
public class EnvDashboardController {

    @Autowired
    Tracer tracer;

    @Autowired
    EnvConfigService envConfigService;

    @Value("${spring.application.name}")
    private String applicationName;

    private final RestTemplate restTemplate;

    EnvDashboardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public ResponseEntity< String > index(Model model) throws URISyntaxException, IOException {
        this.restTemplate.getForObject("http://localhost:8090/env-dashboard/triggerBuild", String.class);
        model.addAttribute("environments", envConfigService.getEnvNames());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        responseHeaders.set("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Api-Key,traceparent");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");
        return ResponseEntity.ok().headers(responseHeaders).contentType(MediaType.APPLICATION_JSON).body("{\"msg\":\"success\"}");

    }

    @GetMapping("/triggerBuild")
    public ResponseEntity< String > triggerBuild(Model model) {
        triggerJenkinsJob();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        responseHeaders.set("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Api-Key,traceparent");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");
        return ResponseEntity.ok().headers(responseHeaders).contentType(MediaType.APPLICATION_JSON).body("{\"msg\":\"success\"}");
    }

    public void triggerJenkinsJob() {
        try {
            String url = "http://localhost:8080/job/Test-Job/build";
            String jenkinsAuthStr = "admin" + ":" + "admin";
            String auth = Base64.getEncoder().encodeToString(jenkinsAuthStr.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/json");
            headers.add("Authorization", "Basic "+ auth);

            String json = "{}";
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("json", json);
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            String response = this.restTemplate.postForObject(url, entity, String.class);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/buildInfo")
    public String buildInfo(@RequestParam String environment,
        @RequestParam(required = false) String appGroup,
        Model model){
        model.addAttribute("environment", environment);
        model.addAttribute("currentAppGroup", Objects.isNull(appGroup) ? "PAS" : appGroup);
        model.addAttribute("appGroups", envConfigService.getAppGroupNames());
        model.addAttribute("buildInfo",envConfigService.getBuildInfo(environment,
            appGroup));
        return "buildInfo";
    }
}
