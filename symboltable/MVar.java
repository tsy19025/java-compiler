package lab2.symboltable;

public class MVar extends MIdentifier {
	String type;
	int begin, end; // ������
	MType owner; // ���ĸ��ࣨ���߷���������

	public MVar() {
		super();
		type = null;
		owner = null;
	}
	public MVar(String _type, MType _owner) {
		super();
		type = _type;
		owner = _owner;
	}
	public MVar(String _type, MType _owner, int _line, int _column) {
		super(_line, _column);
		type = _type;
		owner = _owner;
	}
	public MVar(String _type, MType _owner, int _line, int _column, String _name, String _file) {
		super(_line, _column, _name, _file);
		type = _type;
		owner = _owner;
	}
}
