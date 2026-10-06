package com.clinique.gestion_clinique.api.resource;

import java.util.Map;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
 @Path("/me")
public class AuthResource {
   
    @Context
    private SecurityContext securityContext;

    @GET
    @RolesAllowed({"GENERALISTE", "SPECIALISTE"})
    public Response me() {

        return Response.ok(
                Map.of(
                        "email",
                        securityContext.getUserPrincipal().getName(),

                        "generaliste",
                        securityContext.isUserInRole("GENERALISTE"),

                        "specialiste",
                        securityContext.isUserInRole("SPECIALISTE")
                )
        ).build();
    }
}
