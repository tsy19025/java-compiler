package lab2.symboltable;

public class MVar extends MIdentifier {
	public String type;
	public int begin, end; // 作用域
	public MType owner; // 在哪个类（或者方法）里面
	public boolean isInited, isUsed;
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
