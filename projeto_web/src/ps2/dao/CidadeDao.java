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
import ps2.entidade.Cidade;

/**
 *
 * @author likin
 */
public class CidadeDao {
    private final static String sqlC =  "INSERT INTO cidade (nome_cidade, estado) VALUES (?,?)";
    private final static String sqlR =  "SELECT * FROM cidade";
    private final static String sqlU =  "UPDATE cidade SET nome_cidade=?, estado=? WHERE id_cidade=?";
    private final static String sqlD =  "DELETE FROM cidade WHERE id_cidade=?";
    private final String sqlRById = "SELECT * FROM cidade WHERE id_cidade=?";
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
        }
        catch (SQLException ex){
            ex.printStackTrace();
                throw new DaoException ("Falha ao preparar o statement: " + ex.getMessage());        
        }
    }


	public long create(Cidade c) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, c.getNome());
                        stmC.setString(2, c.getEstado());
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
	public List<Cidade> read() throws DaoException {
		List<Cidade> cidades = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id_cidade");
				String nome = rs.getString("nome_cidade");
                                String estado = rs.getString("estado");
				Cidade c = new Cidade(id, nome,estado);
				cidades.add(c);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return cidades;
	}
	public void update(Cidade c) throws DaoException {
		try {
			stmU.setString(1, c.getNome());
                        stmU.setString(2, c.getEstado());
			stmU.setLong(3, c.getId());
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
         public Cidade readById(long id) throws DaoException {
		Cidade c = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {
                                String estado = rs.getString("estado");
				String nome = rs.getString("nome_cidade");
				c = new Cidade(id,nome,estado);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return c;
	}
}
