package lab2.symboltable;
import java.util.*;

public class MMethod extends MIdentifier {
	String type;
	MType owner; // ���ĸ��ඨ���
	public Vector<MVar> var_list = new Vector<MVar>(); // �������涨��ı���
	public Vector<MVar> par_list = new Vector<MVar>(); // �����Ĳ���

	public MMethod() {
		super();
		owner = null;
	}
	public MMethod(MType _owner) {
		super();
		owner = _owner;
	}
	public MMethod(MType _owner, int _line, int _column) {
		super(_line, _column);
		owner = _owner;
	}
	public MMethod(MType _owner, int _line, int _column, String _name, String _type) {
		super(_line, _column, _name);
		owner = _owner;
		tpye = _type;
	}

	// �Ƿ��������Ĳ���
	String InsertPar(MVar new_par) {
		if (IsRepeated(new_par.name)) return "Repeat define";
		par_list.addElement(new_par);
		return null;
	}

	// ���ز����ڲ����б��е��±ꡣ-1��ʾ������������б���
	int FindPar(MVar new_par) {
		for (int i = 0, sz = par_list.size(); i < sz; i++) {
			if (par_list.elementAt(i).name.equals(new_par.name)) {
				return i;
			}
		}
		return -1;
	}

	// ͨ���������ҵ��������
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
