package ps2.rest;

import ps2.dao.DaoException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;
import ps2.dao.BairroDao;
import ps2.entidade.Bairro;

@Path("/bairros")
@Produces(MediaType.APPLICATION_JSON)

public class BairroResource {

    private BairroDao dao;
    private long proximoId;

    public BairroResource(BairroDao dao) {
        this.dao = dao;
    }

    @POST
    public Bairro create(Bairro b) {
        Bairro resp;
        try {
            long id = dao.create(b);
            b.setId(id);
            resp = b;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    @GET
    public List<Bairro> read() {
        List<Bairro> bairros;
        try {
            bairros = dao.read();
        } catch (DaoException ex) {
            ex.printStackTrace();
            bairros = null;
        }
        return bairros;
    }

    @PUT
    @Path("{id}")
    public Bairro update(@PathParam("id") LongParam id, Bairro b) {
        Bairro resp;
        try {
            b.setId(id.get());
            dao.update(b);
            resp = b;
        } catch (DaoException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return b;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") LongParam id) {
        Bairro b;
        try {
            b = dao.readById(id.get());
        } catch (DaoException ex) {
            ex.printStackTrace();
            throw new WebApplicationException("Erro ao buscar bairro com id="
                    + id.get(), 500);
        }
        if (b != null) {
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
