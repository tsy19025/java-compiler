package lab2.symboltable;
import java.util.Vector;


public class MClassList extends MType {
	public Vector<MClass> class_list = new Vector<MClass>();

	MClassList() {
		super();
	}
	MClassList(int _line, int _column) {
		super(_line, _column);
	}
	MClassList(int _line, int _column, String _name, String _file) {
		super(_line, _column, _name, _file);
	}

	// 1表示成功	0表示存在重复定义的类
	int InsertClass(MClass new_class) {
		// 是否有重复的class
		for (int i = 0, sz = class_list.size(); i < sz; i++) {
			if (class_list.elementAt(i).name.equals(new_class.name)) {
				return 0;
			}
		}
		class_list.addElement(new_class);
		return 1;
	}

	// 返回类在list中的下标。-1表示这个类不在list中
	int FindClass(MClass new_class) {
		for (int i = 0, sz = class_list.size(); i < sz; i++) {
			if (class_list.elementAt(i).name.equals(new_class.name)) {
				return i;
			}
		}
		return -1;
	}

	// 通过名字来找到这个类
	MClass FindClassByName(String name) {
		for (int i = 0, sz = class_list.size(); i < sz; i++) {
			if (class_list.elementAt(i).name.equals(name)) {
				return class_list.elementAt(i);
			}
		}
		return null;
	}
}
