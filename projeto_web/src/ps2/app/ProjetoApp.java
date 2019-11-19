package ps2.app;

import ps2.rest.CampeonatoResource;
import ps2.rest.TimeResource;
import ps2.dao.CampeonatoDao;
import ps2.dao.TimeDao;
import ps2.dao.EmpregadoDao;
import ps2.dao.EmpresaDao;
import ps2.rest.EmpregadoResource;
import ps2.rest.EmpresaResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.assets.AssetsBundle;
import ps2.conexao.ConexaoJavaDb;
import ps2.dao.BairroDao;
import ps2.dao.CidadeDao;
import ps2.rest.BairroResource;
import ps2.rest.CidadeResource;

public class ProjetoApp extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new ProjetoApp().run(new String[] { "server" });
    }

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/resources/html", "/site", "index.html"));
    }
    
    @Override
    public void run(Configuration configuration, Environment environment) {
        try {
            ConexaoJavaDb conexao;
            conexao = new ConexaoJavaDb("app", "app", "localhost", 1527, "Web");
            CampeonatoDao campeonatoDao = new CampeonatoDao(conexao);
            EmpresaDao empresaDao = new EmpresaDao(conexao);
            CidadeDao cidadeDao = new CidadeDao(conexao);
            EmpregadoDao empregadoDao = new EmpregadoDao(conexao);
            BairroDao bairroDao = new BairroDao(conexao);
            TimeDao timeDao = new TimeDao(conexao);
            environment.jersey().register(new CampeonatoResource(campeonatoDao));
            environment.jersey().register(new TimeResource(timeDao));
            environment.jersey().register(new EmpresaResource(empresaDao));
            environment.jersey().register(new EmpregadoResource(empregadoDao));
            environment.jersey().register(new CidadeResource(cidadeDao));
            environment.jersey().register(new BairroResource(bairroDao));
            environment.jersey().setUrlPattern("/api/*");
        } catch(Exception e) {
            e.printStackTrace();
        }           
    }
}