package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Cidade;
import ps2.entidade.Empresa;
public class CidadeDao {

    private final static String sqlC = "INSERT INTO CIDADE (NOME_CIDADE,ESTADO) VALUES (?,?)";
    private final static String sqlR = "SELECT * FROM CIDADE";
    private final static String sqlU = "UPDATE CIDADE SET NOME_CIDADE=?,ESTADO=? WHERE ID_CIDADE=?";
    private final static String sqlD = "DELETE FROM CIDADE WHERE ID_CIDADE=?";
    private final static String sqlRById = "SELECT * FROM CIDADE WHERE ID_CIDADE=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public CidadeDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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

    public long create(Cidade ci) throws DaoException {
        long id_cidade = 0;
        try {
            stmC.setString(1, ci.getNome());
            int r = stmC.executeUpdate();
            ResultSet rs = stmC.getGeneratedKeys();
            if (rs.next()) {
                id_cidade = rs.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao criar registro: " + ex.getMessage());
        }
        return id_cidade;
    }

    public List<Cidade> read() throws DaoException {
        List<Cidade> cidades = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id_cidade = rs.getLong("ID_CIDADE");
                String nome_cidade = rs.getString("NOME_CIDADE");
                String estado = rs.getString("ESTADO");
                Cidade ci = new Cidade(id_cidade, nome_cidade, estado);
                cidades.add(ci);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return cidades;
    }

    public void update(Cidade ci) throws DaoException {
        try {
            stmU.setString(1, ci.getNome());
            stmU.setLong(2, ci.getId());
            stmU.setString(3, ci.getEstado());
            int r = stmU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
        }
    }

    public void delete(long id_cidade) throws DaoException {
        try {
            stmD.setLong(1, id_cidade);
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

    public Cidade readById(long id_cidade) throws DaoException {
        Cidade ci = null;

        try {
            stmRById.setLong(1, id_cidade);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                String nome_cidade = rs.getString("nome_cidade");
                String estado = rs.getString("estado");
                ci = new Cidade(id_cidade, nome_cidade, estado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return ci;
    }
}