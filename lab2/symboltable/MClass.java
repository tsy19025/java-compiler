package lab2.symboltable;
import java.util.*;

public class MClass extends MType {
	public MClassList classes;
	public String parent_name; //is a string!
	public Vector<MVar> var_list = new Vector<MVar>();
	public Vector<MMethod> method_list = new Vector<MMethod>();

	public MClass() {
		super();
		classes = null;
	}
	public MClass(MClassList _classes) {
		super();
		classes = _classes;
	}
	public MClass(MClassList _classes, int _line, int _column) {
		super(_line, _column);
		classes = _classes;
	}
	public MClass(MClassList _classes, int _line, int _column, String _name, String _file) {
		super(_line, _column, _name, _file);
		classes = _classes;
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

	public boolean Repeated_var(String _var_name){
		int len = var_list.size();
		for(int i = 0;i < len;i ++){
			String i_name = ((MVar)var_list.elementAt(i)).getName();
			if (_var_name.equals(i_name)){
				return true;
			}
		}
		return false;
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

	public boolean Repeated_method(String _method_name){
		int len = method_list.size();
		for(int i = 0;i < len;i ++){
			String i_name = ((MMethod)method_list.elementAt(i)).getName();
			if (_method_name.equals(i_name)){
				return true;
			}
		}
		return false;
	}

	public int getVarIndex(String _var_name){ //getVarIndex
		int len = var_list.size();
		for(int i = 0;i < len;i ++){
			String i_name =((MVar)var_list.elementAt(i)).getName();
			if (_var_name.equals(i_name)){
				return i;
			}
		}
		return -1;
	}

	public int getMethodIndex(String _method_name) {
		int len = method_list.size();
		for(int i = 0;i < len;i ++){
			String i_name = ((MMethod)method_list.elementAt(i)).getName();
			if (_method_name.equals(i_name)){
				return i;
			}
		}
		return -1;
	}

	public MVar getVar(String _var_name){
		int len = var_list.size();
		for(int i = 0;i < len;i ++){
			String i_name = ((MVar)var_list.elementAt(i)).getName();
			if (i_name.equals(_var_name)){
				return var_list.elementAt(i);
			}
		}
		String f = parent_name;
		while (!f.equals("") && !f.equals("Object")){
			int lenf = classes.FindClassByName(f).var_list.size();
			for(int i = 0;i < lenf;i ++){
				String i_name = ((MVar)classes.FindClassByName(f).var_list.elementAt(i)).getName();
				if (i_name.equals(_var_name)){
					return classes.FindClassByName(f).var_list.elementAt(i);
				}
			}
			f = classes.FindClassByName(f).parent_name;
		}
		return null;
	}

	public MMethod getMethod(String _method_name){
		int len = method_list.size();
		for(int i = 0;i < len;i ++){
			String i_name = ((MMethod)method_list.elementAt(i)).getName();
			if (i_name.equals(_method_name)){
				return method_list.elementAt(i);
			}
		}
		String f = parent_name;
		while (!f.equals("") && !f.equals("Object")){
			int lenf = classes.FindClassByName(f).method_list.size();
			for(int i = 0;i < lenf;i ++){
				String i_name = ((MMethod)classes.FindClassByName(f).method_list.elementAt(i)).getName();
				if (i_name.equals(_method_name)){
					return classes.FindClassByName(f).method_list.elementAt(i);
				}
			}
			f = classes.FindClassByName(f).parent_name;
		}
		return null;
	}
}
