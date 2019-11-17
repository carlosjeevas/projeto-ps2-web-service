/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps2.rest;



import ps2.dao.DaoException;
import ps2.dao.TimeDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import ps2.entidade.Time;
/**
 *
 * @author likin
 */

@Path("/times")
@Produces(MediaType.APPLICATION_JSON)

public class TimeResource {
    
    private TimeDao dao;
    private long proximoId;
    
    public TimeResource(TimeDao dao) {
        this.dao = dao;
    }

    @POST
    public Time create(Time t) {
        Time resp;
        try {
            long id = dao.create(t);
            t.setId(id);
            resp = t;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }
    
    @GET
    public List<Time> read() {
        List<Time> times;
        try { times = dao.read(); }
        catch(DaoException ex) { 
            ex.printStackTrace();
            times = null; 
        }
        return times;
    }

    @PUT
    @Path("{id}")
    public Time update(@PathParam("id") LongParam id, Time t) {
        Time resp;
        try {
            t.setId(id.get());
            dao.update(t);
            resp = t;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return t;
    }    

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Time t;
        try {
            t = dao.readById(id.get());
        } catch(DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar time com id="
                                                + id.get(), 500);  
        }
        if (t != null) { 
            try{ 
                dao.delete(id.get()); 
            } catch(DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar time com id="
                                                  + id.get(), 500);                
            }
        }
        else {
            throw new WebApplicationException("Time com id=" + id.get() 
                                              + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}

