package lab2.symboltable;

public class MType {
	public String name; // 符号的名称
	public int line, column;
	// -1表示没有定位到这个符号

	MType() {
		line = -1;
		column = -1;
	}
	MType(int _line, int _column) {
		line = _line;
		column = _column;
	}
	MType(int _line, int _column, String _name) {
		line = _line;
		column = _column;
		name = _name;
	}

	String getName() {return name;}
	String getFile() {return file;}
	int getLine() {return line;}
	int getColumn() {return column;}
}
