package ps2.rest;

import ps2.dao.DaoException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.dao.EmpresaDao;
import ps2.entidade.Empregado;
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
    public Empresa create(Empresa c) {
        Empresa resp;
        try {
            long id = dao.create(c);
            c.setId(id);
            resp = c;
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
    
    @GET
    @Path("{id}")
    public List<Empregado> readEmpregado(@PathParam("id") long id_emp) {
        List<Empregado> empregados;
        try {
            empregados = dao.readEmpregados(id_emp);
        } catch (DaoException ex) {
            ex.printStackTrace();
            empregados = null;
        }
        return empregados;
    }

    @PUT
    @Path("{id}")
    public Empresa update(@PathParam("id") LongParam id, Empresa c) {
        Empresa resp;
        try {
            c.setId(id.get());
            dao.update(c);
            resp = c;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return c;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Empresa c;
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
                throw new WebApplicationException("Erro ao tentar apagar empresa com id="
                        + id.get(), 500);
            }
        } else {
            throw new WebApplicationException("Empresa com id=" + id.get()
                    + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}