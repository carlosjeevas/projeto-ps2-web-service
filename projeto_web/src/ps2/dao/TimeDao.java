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
import ps2.entidade.Time;

/**
 *
 * @author likin
 */
public class TimeDao {
    private final static String sqlC =  "INSERT INTO times (nome, anodefundacao, cidade, estado) VALUES (?,?,?,?)";
    private final static String sqlR =  "SELECT * FROM times";
    private final static String sqlU =  "UPDATE times SET nome=?, anodefundacao=?, cidade=?, estado=? WHERE id=?";
    private final static String sqlD =  "DELETE FROM times WHERE id=?";
    private final String sqlRById = "SELECT * FROM times WHERE id=?";
    private PreparedStatement stmC;
    private PreparedStatement stmR;
    private PreparedStatement stmU;
    private PreparedStatement stmD;
    private PreparedStatement stmRById;
    public TimeDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
        try {
            Connection con = conexao.getConnection();
            stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);
                        stmRById = con.prepareStatement(sqlRById);
        }
        catch (SQLException ex){
            ex.printStackTrace();
                throw new DaoException ("Falha ao preparar o statement: " + ex.getMessage());        
        }
    }


	public long create(Time t) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, t.getNome());
                        stmC.setInt(2, t.getAnoFundacao());
                        stmC.setString(3, t.getCidade());
                        stmC.setString(4, t.getEstado());
			int r = stmC.executeUpdate();
			ResultSet rs = stmC.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao criar registro: " + ex.getMessage());
		}
		return id;
	}
	public List<Time> read() throws DaoException {
		List<Time> times = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
                                int anodefundacao = rs.getInt("anodefundacao");
                                String cidade = rs.getString("cidade");
                                String estado = rs.getString("estado");
				Time t = new Time(id, nome, anodefundacao, cidade, estado);
				times.add(t);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return times;
	}
	public void update(Time t) throws DaoException {
		try {
			stmU.setString(1, t.getNome());
                        stmU.setInt(2, t.getAnoFundacao());
                        stmU.setString(3, t.getCidade());
                        stmU.setString(4, t.getEstado());
			stmU.setLong(5, t.getId());
			int r = stmU.executeUpdate();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
		}
	}
	public void delete(long id) throws DaoException {
		try {
			stmD.setLong(1, id);
			int r = stmD.executeUpdate();
		} catch(SQLException ex) {
			throw new DaoException("Falha ao apagar registro: " + ex.getMessage());
		}
	}
	public void close() throws DaoException {
		try {
			stmC.close();
			stmR.close();
			stmU.close();
			stmD.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}
         public Time readById(long id) throws DaoException {
		Time t = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {
                                String estado = rs.getString("estado");
                                String cidade = rs.getString("cidade");
				int anodefundacao = rs.getInt("anodefundacao");
				String nome = rs.getString("nome");
				t = new Time(id,nome,anodefundacao, cidade,estado);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return t;
	}
}
