package com;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

//http://localhost:8080/TFM/rest1/hello/hola
@Path("/hello")
public class Hello {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		
		String output = "Jersey say : " + msg;
		int i= 5;
		if(i==5){
			output += i;
		}
		return Response.status(200).entity(output).build();

	}

}