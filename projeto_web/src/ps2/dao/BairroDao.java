package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Bairro;
import ps2.entidade.Empregado;

public class BairroDao {

    private final static String sqlC = "INSERT INTO BAIRRO (ID_CIDADE,NOME_BAIRRO) VALUES (?,?)";
    private final static String sqlR = "SELECT * FROM BAIRRO";
    private final static String sqlU = "UPDATE BAIRRO SET NOME_BAIRRO=? WHERE ID_BAIRRO=?";
    private final static String sqlD = "DELETE FROM BAIRRO WHERE ID_BAIRRO=?";
    private final static String sqlRById = "SELECT * FROM BAIRRO WHERE ID_BAIRRO=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public BairroDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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

    public long create(Bairro b) throws DaoException {
        long id_bairro = 0;
        try {
            stmC.setString(1, b.getNome());
            int r = stmC.executeUpdate();
            ResultSet rs = stmC.getGeneratedKeys();
            if (rs.next()) {
                id_bairro = rs.getLong(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao criar registro: " + ex.getMessage());
        }
        return id_bairro;
    }

    public List<Bairro> read() throws DaoException {
        List<Bairro> bairros = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id_bairro = rs.getLong("ID_BAIRRO");
                long id_cidade = rs.getLong("ID_CIDADE");
                String nome_bairro = rs.getString("NOME_BAIRRO");

                Bairro b = new Bairro(id_bairro, id_cidade, nome_bairro);
                bairros.add(b);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return bairros;
    }

    public void update(Bairro b) throws DaoException {
        try {
            stmU.setString(1, b.getNome());
            stmU.setLong(2, b.getid_cid());
            stmU.setLong(3, b.getId());
            int r = stmU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
        }
    }

    public void delete(long id_bairro) throws DaoException {
        try {
            stmD.setLong(1, id_bairro);
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

    public Bairro readById(long id_bairro) throws DaoException {

        Bairro b = null;

        try {
            stmRById.setLong(1, id_bairro);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                long id_cidade = rs.getLong("id_cidade");
                String nome_bairro = rs.getString("nome_bairro");
                b = new Bairro(id_bairro, id_cidade, nome_bairro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return b;
    }
}