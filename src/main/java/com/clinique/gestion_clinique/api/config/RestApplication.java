package com.clinique.gestion_clinique.api.config;
import org.glassfish.jersey.server.ResourceConfig;
// import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.clinique.gestion_clinique.api.security.BasicAuthFilter;
public class RestApplication extends ResourceConfig {
    
     public RestApplication() {

        packages("com.clinique.gestion_clinique.api.resource");
    }

}
