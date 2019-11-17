package ps2.rest;

import ps2.dao.DaoException;
import ps2.dao.EmpresaDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.entidade.Empresa;

@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)

public class EmpresaResource {

    private EmpresaDao dao;
    private long proximoId;

    public EmpresaResource(EmpresaDao dao) {
        this.dao = dao;
    }

    @POST
    public Empresa create(Empresa e) {
        Empresa resp;
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
    public List<Empresa> read() {
        List<Empresa> empresas;
        try {
            empresas = dao.read();
        } catch (DaoException ex) {
            ex.printStackTrace();
            empresas = null;
        }
        return empresas;
    }

    @PUT
    @Path("{id}")
    public Empresa update(@PathParam("id") LongParam id, Empresa e) {
        Empresa resp;
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
        Empresa e;
        try {
            e = dao.readById(id.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar campenato com id="
                    + id.get(), 500);
        }
        if (e != null) {
            try {
                dao.delete(id.get());
            } catch (DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar Empresa com id="
                        + id.get(), 500);
            }
        } else {
            throw new WebApplicationException("Empresa com id=" + id.get()
                    + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}