package lab2.symboltable;
import java.util.*;

public class MClass extends MType {
	public MClassList classes;
	public MClass parent;
	public Vector<MVar> var_list = new Vector<MVar>();
	public Vector<MMethod> method_list = new Vector<MMethod>();

	public MClass() {
		super();
		classes = null;
		parent = null;
	}
	public MClass(MClassList _classes, MClass _parent) {
		super();
		classes = _classes;
		parent = _parent;
	}
	public MClass(MClassList _classes, MClass _parent, int _line, int _column) {
		super(_line, _column);
		classes = _classes;
		parent = _parent;
	}
	public MClass(MClassList _classes, MClass _parent, int _line, int _column, String _name, String _file) {
		super(_line, _column, _name, _file);
		classes = _classes;
		parent = _parent;
	}


}
