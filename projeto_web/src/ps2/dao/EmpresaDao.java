package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Empresa;
import ps2.entidade.Empregado;

public class EmpresaDao {

    private final static String sqlC = "INSERT INTO empresas (nome) VALUES (?)";
    private final static String sqlR = "SELECT * FROM empresas";
    private final static String sqlI = "SELECT E.NOME_EMPREGADO FROM EMPREGADOS E, EMPRESAS EMP WHERE 1=1 AND E.ID_EMPREGADO = EMP.ID_EMP AND E.ID_EMP = ?;";
    private final static String sqlU = "UPDATE empresas SET nome=? WHERE id_emp=?";
    private final static String sqlD = "DELETE FROM empresas WHERE id_emp=?";
    private final String sqlRById = "SELECT * FROM empresas WHERE id_emp=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmI;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public EmpresaDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
        try {
            Connection con = conexao.getConnection();
            stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
            stmR = con.prepareStatement(sqlR);
            stmI = con.prepareStatement(sqlI);
            stmU = con.prepareStatement(sqlU);
            stmD = con.prepareStatement(sqlD);
            stmRById = con.prepareStatement(sqlRById);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao preparar o statement: " + ex.getMessage());
        }
    }

    public long create(Empresa t) throws DaoException {
        long id = 0;
        try {
            stmC.setString(1, t.getNome());
            int r = stmC.executeUpdate();
            ResultSet rs = stmC.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao criar registro: " + ex.getMessage());
        }
        return id;
    }

    public List<Empresa> read() throws DaoException {
        List<Empresa> empresas = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id_emp");
                String nome = rs.getString("nome");
                Empresa t = new Empresa(id, nome);
                empresas.add(t);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return empresas;
    }

    public void update(Empresa t) throws DaoException {
        try {
            stmU.setString(1, t.getNome());
            stmU.setLong(2, t.getId());
            int r = stmU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
        }
    }

    public void delete(long id) throws DaoException {
        try {
            stmD.setLong(1, id);
            int r = stmD.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Falha ao apagar registro: " + ex.getMessage());
        }
    }

    public void close() throws DaoException {
        try {
            stmC.close();
            stmR.close();
            stmU.close();
            stmD.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
        }
    }

    public Empresa readById(long id) throws DaoException {
        Empresa t = null;

        try {
            stmRById.setLong(1, id);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                t = new Empresa(id, nome);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return t;
    }
    
        public List<Empregado> readEmpregados(long id_emp) throws DaoException {
        List<Empregado> empregados = new ArrayList<>();
        try {
            stmR.setLong(1, id_emp);
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                Empregado t = new Empregado(nome);
                empregados.add(t);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return empregados;
    }
}
