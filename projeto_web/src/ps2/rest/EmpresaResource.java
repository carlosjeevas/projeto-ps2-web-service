package ps2.rest;

import ps2.dao.DaoException;
import ps2.dao.EmpresaDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
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
    public Empresa create(Empresa e) {
        Empresa resp;
        try {
            long id_emp = dao.create(e);
            e.setId(id_emp);
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
    @Path("{id_emp}")
    public Empresa update(@PathParam("id_emp") LongParam id_emp, Empresa e) {
        Empresa resp;
        try {
            e.setId(id_emp.get());
            dao.update(e);
            resp = e;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return e;
    }

    @DELETE
    @Path("{id_emp}")
    public Response delete(@PathParam("id_emp") LongParam id_emp) {
        Empresa e;
        try {
            e = dao.readById(id_emp.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar campenato com id="
                    + id_emp.get(), 500);
        }
        if (e != null) {
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
