package ps2.rest;

import ps2.dao.DaoException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.dao.EmpresaDao;
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
        List<Empresa> Empresas;
        try {
            Empresas = dao.read();
        } catch (DaoException ex) {
            ex.printStackTrace();
            Empresas = null;
        }
        return Empresas;
    }

    @PUT
    @Path("{id_emp}")
    public Empresa update(@PathParam("id_emp") LongParam id, Empresa t) {
        Empresa resp;
        try {
            t.setId(id.get());
            dao.update(t);
            resp = t;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return t;
    }

    @DELETE
    @Path("{id_emp}")
    public Response delete(@PathParam("id_emp") LongParam id_emp) {
        Empresa t;
        try {
            t = dao.readById(id_emp.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar Empresa com id="
                    + id_emp.get(), 500);
        }
        if (t != null) {
            try {
                dao.delete(id_emp.get());
            } catch (DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar Empresa com id="
                        + id_emp.get(), 500);
            }
        } else {
            throw new WebApplicationException("Empresa com id=" + id_emp.get()
                    + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}