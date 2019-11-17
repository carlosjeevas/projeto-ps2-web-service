package ps2.dao;

import java.sql.*;
import java.util.*;
import ps2.conexao.ConexaoException;
import ps2.conexao.ConexaoJavaDb;
import ps2.entidade.Professor;

public class ProfessoresDao {
    private final static String sqlCreateTable = "CREATE TABLE professores " 
        + "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
        + "nome VARCHAR(100) NOT NULL,"
        + "matricula INT NOT NULL,"
        + "PRIMARY KEY (id))";
	private final String sqlC = "INSERT INTO professores (nome, matricula) VALUES (?,?)";
	private final String sqlR = "SELECT * FROM professores";
	private final String sqlU = "UPDATE professores SET nome=?, matricula=? WHERE id=?";
	private final String sqlD = "DELETE FROM professores WHERE id=?";	
	private final String sqlRById = "SELECT * FROM professores WHERE id=?";
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;
	private PreparedStatement stmRById;
	public ProfessoresDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
		try {
			Connection con = conexao.getConnection();
            try {
                Statement stm = con.createStatement();
                stm.execute(sqlCreateTable);
                System.out.println("Tabela criada com sucesso!");
            } catch( SQLException ex ) {
                System.out.println("Tabela já existe!");
            }           
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);
			stmRById = con.prepareStatement(sqlRById);
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
		}
	}
    
	public long create(Professor p) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, p.getNome());
			stmC.setInt(2, p.getMatricula());
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
	public List<Professor> read() throws DaoException {
		List<Professor> professores = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				int matricula = rs.getInt("matricula");
				Professor p = new Professor(id, nome, matricula);
				professores.add(p);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return professores;
	}
	public void update(Professor p) throws DaoException {
		try {
			stmU.setString(1, p.getNome());
			stmU.setInt(2, p.getMatricula());
			stmU.setLong(3, p.getId());
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

	public Professor readById(long id) throws DaoException {
		Professor p = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {
				int matricula = rs.getInt("matricula");
				String nome = rs.getString("nome");
				p = new Professor(id,nome,matricula);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return p;
	}

}