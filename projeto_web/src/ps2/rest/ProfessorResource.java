package ps2.rest;

import ps2.dao.DaoException;
import ps2.dao.ProfessoresDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.entidade.Professor;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {    

    private ProfessoresDao dao;
    private long proximoId;
    
    public ProfessorResource(ProfessoresDao dao) {
        this.dao = dao;
    }

    @POST
    public Professor create(Professor p) {
        Professor resp;
        try {
            long id = dao.create(p);
            p.setId(id);
            resp = p;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }
    
    @GET
    public List<Professor> read() {
        List<Professor> professores;
        try { professores = dao.read(); }
        catch(DaoException ex) { 
            ex.printStackTrace();
            professores = null; 
        }
        return professores;
    }

    @PUT
    @Path("{id}")
    public Professor update(@PathParam("id") LongParam id, Professor p) {
        Professor resp;
        try {
            p.setId(id.get());
            dao.update(p);
            resp = p;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return p;
    }    

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Professor p;
        try {
            p = dao.readById(id.get());
        } catch(DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar professor com id="
                                                + id.get(), 500);  
        }
        if (p != null) { 
            try{ 
                dao.delete(id.get()); 
            } catch(DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar professor com id="
                                                  + id.get(), 500);                
            }
        }
        else {
            throw new WebApplicationException("Professor com id=" + id.get() 
                                              + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}