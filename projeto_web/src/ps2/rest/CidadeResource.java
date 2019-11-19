package ps2.rest;

import ps2.dao.DaoException;
import ps2.dao.EmpresaDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.dao.CidadeDao;
import ps2.entidade.Cidade;

@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)

public class CidadeResource {

    private CidadeDao dao;
    private long proximoId;

    public CidadeResource(CidadeDao dao) {
        this.dao = dao;
    }

    @POST
    public Cidade create(Cidade ci) {
        Cidade resp;
        try {
            long id = dao.create(ci);
            ci.setId(id);
            resp = ci;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    @GET
    public List<Cidade> read() {
        List<Cidade> cidades;
        try {
            cidades = dao.read();
        } catch (DaoException ex) {
            ex.printStackTrace();
            cidades = null;
        }
        return cidades;
    }

    @PUT
    @Path("{id_cidade}")
    public Cidade update(@PathParam("id_cidade") LongParam id, Cidade ci) {
        Cidade resp;
        try {
            ci.setId(id.get());
            dao.update(ci);
            resp = ci;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return ci;
    }

    @DELETE
    @Path("{id_cidade}")
    public Response delete(@PathParam("id_cidade") LongParam id) {
        Cidade ci;
        try {
            ci = dao.readById(id.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar cidade com id="
                    + id.get(), 500);
        }
        if (ci != null) {
            try {
                dao.delete(id.get());
            } catch (DaoException ex) {
                ex.printStackTrace();
                throw new WebApplicationException("Erro ao tentar apagar Cidade com id="
                        + id.get(), 500);
            }
        } else {
            throw new WebApplicationException("Cidade com id=" + id.get()
                    + " não encontrado!", 404);
        }
        return Response.ok().build();
    }
}