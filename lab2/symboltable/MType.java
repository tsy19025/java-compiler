package lab2.symboltable;

public class MType {
	public String name; // ���ŵ�����
	public int line, column;
	// -1��ʾû�ж�λ���������

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
