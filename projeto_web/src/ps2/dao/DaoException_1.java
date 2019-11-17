package ps2.dao;

public class DaoException_1 extends Exception {
	private String msg;
	public DaoException_1(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}