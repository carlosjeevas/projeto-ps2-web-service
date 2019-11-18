/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Campeonato;

/**
 *
 * @author likin
 */
public class CampeonatoDao {

    private final static String sqlC = "INSERT INTO campeonatos (nome) VALUES (?)";
    private final static String sqlR = "SELECT * FROM campeonatos";
    private final static String sqlU = "UPDATE campeonatos SET nome=? WHERE id=?";
    private final static String sqlD = "DELETE FROM campeonatos WHERE id=?";
    private final static String sqlRById = "SELECT * FROM campeonatos WHERE id=?";

    
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;

    public CampeonatoDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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

    public long create(Campeonato c) throws DaoException {
        long id = 0;
        try {
            stmC.setString(1, c.getNome());
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

    public List<Campeonato> read() throws DaoException {
        List<Campeonato> campeonatos = new ArrayList<>();
        try {
            ResultSet rs = stmR.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                Campeonato c = new Campeonato(id, nome);
                campeonatos.add(c);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao ler registros: " + ex.getMessage());
        }
        return campeonatos;
    }

    public void update(Campeonato c) throws DaoException {
        try {
            stmU.setString(1, c.getNome());
            stmU.setLong(2, c.getId());
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

    public Campeonato readById(long id) throws DaoException {
        Campeonato c = null;

        try {
            stmRById.setLong(1, id);
            ResultSet rs = stmRById.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                c = new Campeonato(id, nome);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
        }
        return c;
    }
}
