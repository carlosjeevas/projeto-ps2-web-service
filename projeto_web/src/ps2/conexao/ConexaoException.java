package ps2.conexao;

public class ConexaoException extends Exception {
	private String msg;
	public ConexaoException(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}