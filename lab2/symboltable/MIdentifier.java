package lab2.symboltable;

public class MIdentifier extends MType {
	public MIdentifier() {
		super();
	}
	public MIdentifier(int _line, int _column) {
		super(_line, _column);
	}
	public MIdentifier(int _line, int _column, String _name) {
		super(_line, _column, _name);
	}
}
