package com.restful.jersey.resource;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/client/*")
public class AirResourceConfig extends ResourceConfig {

    public AirResourceConfig() {
        packages("com.restful.jersey");
    }
}