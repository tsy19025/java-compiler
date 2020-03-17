package lab2.symboltable;

public class MVar extends MIdentifier {
	int begin, end; // ������
	MType owner; // ���ĸ��ࣨ���߷���������

	public MVar() {
		super();
		owner = null;
	}
	public MVar(MType _owner) {
		super();
		owner = _owner;
	}
	public MVar(MType _owner, int _line, int _column) {
		super(_line, _column);
		owner = _owner;
	}
	public MVar(MType _owner, int _line, int _column, String _name, String _type) {
		super(_line, _column, _name, _type);
		owner = _owner;
	}
}
