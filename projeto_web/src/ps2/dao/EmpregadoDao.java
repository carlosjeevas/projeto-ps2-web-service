package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Empregado;

public class EmpregadoDao {

    private final static String sqlC = "INSERT INTO empregados (id_emp, nome_empregado) VALUES (?,?)";
    private final static String sqlR = "SELECT * FROM empregados";
    private final static String sqlU = "UPDATE empregados SET id_emp=?, nome_empregado=? WHERE id_empregado=?";
    private final static String sqlD = "DELETE FROM empregados WHERE id_empregado=?";
    private final static String sqlRById = "SELECT * FROM empregados WHERE id_empregado=?"; 
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public EmpregadoDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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

    public long create(Empregado e) throws DaoException {
        long id = 0;
        try {
            stmC.setLong(1, e.getId_emp());
            stmC.setString(2, e.getNome());
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

    public List<Empregado> read() throws DaoException {
        List<Empregado> empregados = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id_empregado = rs.getLong("id_empregado");
                long id_emp = rs.getLong("id_emp");
                String nome_empregado = rs.getString("nome_empregado");

                Empregado e = new Empregado(id_empregado, id_emp, nome_empregado);
                empregados.add(e);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return empregados;
    }

    public void update(Empregado e) throws DaoException {
        try {
            stmU.setString(1, e.getNome());
            stmU.setLong(2, e.getId_emp());
            stmU.setLong(3, e.getId());
            int r = stmU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
        }
    }

    public void delete(long id_empregado) throws DaoException {
        try {
            stmD.setLong(1, id_empregado);
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
    
    public Empregado readById(long id) throws DaoException {
        Empregado e = null;

        try {
            stmRById.setLong(1, id);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                long id_emp = rs.getLong("id_emp");
                String nome = rs.getString("nome");
                e = new Empregado(id, id_emp, nome);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return e;
    }
}
