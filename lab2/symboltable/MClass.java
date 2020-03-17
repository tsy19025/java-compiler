package lab2.symboltable;
import java.util.*;

public class MClass extends MIdentifier {
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
	public MClass(MClassList _classes, MClass _parent, int _line, int _column, String _name) {
		super(_line, _column, _name);
		classes = _classes;
		parent = _parent;
	}
	
	String InsertMethod(MMethod new_method) {
		String name = new_method.name;
		for (int i = 0, sz = method_list.size(); i < sz; ++i) {
			if (method_list.elementAt(i).name.equals(name))
				return "Repeat define";
		}
		method_list.addElement(new_method);
		return null;
	}
	
	String InsertVar(MVar new_var) {
		String name = new_var.name;
		for (int i = 0, sz = var_list.size(); i < sz; ++i) {
			if (var_list.elementAt(i).name.equals(name))
				return "Repeat define";
		}
		var_list.addElement(new_var);
		return null;
	}
}
