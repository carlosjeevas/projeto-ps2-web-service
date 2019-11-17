package ps2.rest;

import ps2.dao.DaoException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.dao.EmpregadoDao;
import ps2.entidade.Empregado;

@Path("/empregados")
@Produces(MediaType.APPLICATION_JSON)

public class EmpregadoResource {
    
    private EmpregadoDao dao;
    private long proximoId;
    
    public EmpregadoResource(EmpregadoDao dao) {
        this.dao = dao;
    }

    @POST
    public Empregado create(Empregado e) {
        Empregado resp;
        try {
            long id = dao.create(e);
            e.setId(id);
            resp = e;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }
    
    @GET
    public List<Empregado> read() {
        List<Empregado> empregados;
        try { empregados = dao.read(); }
        catch(DaoException ex) { 
            ex.printStackTrace();
            empregados = null; 
        }
        return empregados;
    }

    @PUT
    @Path("{id_empregado}")
    public Empregado update(@PathParam("id_empregado") LongParam id_empregado, Empregado e) {
        Empregado resp;
        try {
            e.setId(id_empregado.get());
            dao.update(e);
            resp = e;
        } catch(DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return e;
    }    

    @DELETE
    @Path("{id_empregado}")
    public Response delete(@PathParam("id_empregado") LongParam id_empregado) {
        Empregado e;
        try {
            e = dao.readById(id_empregado.get());
        } catch(DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar time com id="
                                                + id_empregado.get(), 500);  
        }
        if (e != null) { 
            try{ 
                dao.delete(id_empregado.get()); 
            } catch(DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar time com id="
                                                  + id_empregado.get(), 500);                
            }
        }
        else {
            throw new WebApplicationException("Empregado com id=" + id_empregado.get() 
                                              + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}