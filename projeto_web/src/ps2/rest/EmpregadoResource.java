package ps2.rest;

import ps2.dao.DaoException;
import ps2.dao.EmpregadoDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
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
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    @GET
    public List<Empregado> read() {
        List<Empregado> empregados;
        try {
            empregados = dao.read();
        } catch (DaoException ex) {
            ex.printStackTrace();
            empregados = null;
        }
        return empregados;
    }

    @PUT
    @Path("{id}")
    public Empregado update(@PathParam("id") LongParam id, Empregado e) {
        Empregado resp;
        try {
            e.setId(id.get());
            dao.update(e);
            resp = e;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return e;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Empregado c;
        try {
            c = dao.readById(id.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar campenato com id="
                    + id.get(), 500);
        }
        if (c != null) {
            try {
                dao.delete(id.get());
            } catch (DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar Empregado com id="
                        + id.get(), 500);
            }
        } else {
            throw new WebApplicationException("Empregado com id=" + id.get()
                    + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}
