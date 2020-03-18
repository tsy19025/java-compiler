package lab2.symboltable;
import java.util.*;

public class MMethod extends MIdentifier {
	MClass owner; // 由哪个类定义的
	String type; // 返回类型: "int", "boolean", "array", class(class name)
	public Vector<MVar> var_list = new Vector<MVar>(); // 方法里面定义的变量
	public Vector<MVar> par_list = new Vector<MVar>(); // 方法的参数

	public MMethod() {
		super();
		owner = null;
	}
	public MMethod(MType _owner) {
		super();
		owner = _owner;
	}
	public MMethod(MType _owner, String _type, int _line, int _column) {
		super(_line, _column);
		owner = _owner;
		type = _type;
	}
	public MMethod(MType _owner, int _line, int _column, String _name, String _file) {
		super(_line, _column, _name, _file);
		owner = _owner;
	}

	// 是否有重名的参数
	String InsertPar(MVar new_par) {
		if (IsRepeated(new_par.name)) return "Repeat define";
		par_list.addElement(new_par);
		return null;
	}

	// 返回参数在参数列表中的下标。-1表示这个参数不在列表中
	int FindPar(MVar new_par) {
		for (int i = 0, sz = par_list.size(); i < sz; i++) {
			if (par_list.elementAt(i).name.equals(new_par.name)) {
				return i;
			}
		}
		return -1;
	}

	int FindVar(MVar new_var){
		for (int i = 0, sz = var_list.size();i < sz; i++) {
			if (var_list.elementAt(i).name.equals(new_var.name)) {
				return i;
			}
		}
		return -1;
	}

	// 通过名字来找到这个参数
	MVar FindParByName(String name) {
		for (int i = 0, sz = par_list.size(); i < sz; i++) {
			if (par_list.elementAt(i).name.equals(name)) {
				return par_list.elementAt(i);
			}
		}
		return null;
	}

	int InsertVar(MVar new_var) {
		if (IsRepeated(new_var.name)) return 0;
		var_list.addElement(new_var);
		return 1;
	}

	MVar getVar(String var_name){
		int lenv = var_list.size();
		for(int i = 0;i < lenv;i ++) {
			String now_name = ((MVar)var_list.elementAt(i)).getName();
			if (now_name.equals(var_name)) {
				return var_list.elementAt(i);
			}
		}
		int lenp = par_list.size();
		for(int i = 0;i < lenp;i ++){
			String now_name = ((MVar)par_list.elementAt(i)).getName();
			if (now_name.equals(var_name)){
				return par_list.elementAt(i);
			}
		}
		MClass o = owner;
		String o_name = o.getName();
		while (o != null && !o_name.equals("") && !o_name.equals("Object")){
			int leno = o.var_list.size();
			for(int i = 0;i < leno;i ++){
				String now_name = ((MVar)o.var_list.elementAt(i)).getName();
				if (now_name.equals(var_name)) {
					return o.var_list.elementAt(i);
				}
			}
			o = o.classes.FindClassByName(o.parent_name);
			o_name = o.getName();
		}
		return null;
	}

	private boolean IsRepeated(String name) {
		for (int i = 0, sz = par_list.size(); i < sz; i++) {
			if (par_list.elementAt(i).name.equals(name)) {
				return true;
			}
		}
		for (int i = 0, sz = var_list.size(); i < sz; i++) {
			if (var_list.elementAt(i).name.equals(name)) {
				return true;
			}
		}
		return false;
	}
}
