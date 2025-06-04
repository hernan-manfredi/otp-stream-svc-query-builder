package com.octotelematics.otp.processor;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/foo")
public class FooResource {

    @GET
    public String getFoo() {
        return "foo";
    }

}
