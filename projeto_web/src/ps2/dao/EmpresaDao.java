package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Empresa;

public class EmpresaDao {

    private final static String sqlC = "INSERT INTO empresas (nome) VALUES (?)";
    private final static String sqlR = "SELECT * FROM empresas";
    private final static String sqlU = "UPDATE empresas SET nome=? WHERE id=?";
    private final static String sqlD = "DELETE FROM empresas WHERE id=?";
    private final static String sqlRById = "SELECT * FROM empresas WHERE id=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public EmpresaDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
        try {
            Connection con = conexao.getConnection();
            stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
            stmR = con.prepareStatement(sqlR);
            stmU = con.prepareStatement(sqlU);
            stmD = con.prepareStatement(sqlD);
            stmRById = con.prepareStatement(sqlRById);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao preparar o statement: " + ex.getMessage());
        }
    }

    public long create(Empresa e) throws DaoException {
        long id_emp = 0;
        try {
            stmC.setString(1, e.getNome());
            int r = stmC.executeUpdate();
            ResultSet rs = stmC.getGeneratedKeys();
            if (rs.next()) {
                id_emp = rs.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao criar registro: " + ex.getMessage());
        }
        return id_emp;
    }

    public List<Empresa> read() throws DaoException {
        List<Empresa> empresas = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id_emp = rs.getLong("id");
                String nome = rs.getString("nome");
                Empresa e = new Empresa(id_emp, nome);
                empresas.add(e);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return empresas;
    }

    public void update(Empresa e) throws DaoException {
        try {
            stmU.setString(1, e.getNome());
            stmU.setLong(2, e.getId());
            int r = stmU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
        }
    }

    public void delete(long id_emp) throws DaoException {
        try {
            stmD.setLong(1, id_emp);
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

    public Empresa readById(long id_emp) throws DaoException {
        Empresa e = null;

        try {
            stmRById.setLong(1, id_emp);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                e = new Empresa(id_emp, nome);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return e;
    }
}