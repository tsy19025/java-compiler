/*

 */

package lab2.symboltable;

import java.util.Vector;

public class MClass extends MIdentifier {
    public MClassList classes; //所有类表；有时argu不是ClassList，跟着类传
    public String parent_name; //父类名
    public Vector<MVar> var_list = new Vector(); //类的属性
    public Vector<MMethod> method_list = new Vector(); //类的方法
    public int alloc_size = -1;

    public MClass() {
        this.classes = null;
        //parent_name = "";
    }

    public MClass(MClassList _classes) {
        this.classes = _classes;
        //parent_name = "";
    }

    public MClass(MClassList _classes, int _line, int _column) {
        super(_line, _column);
        this.classes = _classes;
        //parent_name = "";
    }

    public MClass(MClassList _classes, String _parent_name, int _line, int _column, String _name) {
        super(_line, _column, _name);
        this.classes = _classes;
        this.parent_name = _parent_name;
    }

    public int calalloc(){
        if (alloc_size != -1){
            return alloc_size;
        }
        MClass parent_class = classes.FindClassByName(parent_name);
        if (parent_class == null){
            alloc_size = 4 * (1 + var_list.size());
        }
        else{
            alloc_size = 4 * (1 + var_list.size()) + parent_class.calalloc();
        }
        return alloc_size;
    }

    public String InsertVar(MVar _var) {
        String var_name = _var.name;

        for (int i = 0, sz = this.var_list.size(); i < sz; i++) {
            if (((MVar) this.var_list.elementAt(i)).name.equals(var_name)) {
                return "Repeat define";
            }
        }

        this.var_list.addElement(_var);
        return null;
    }

    public boolean Repeated_var(String _name) {
        int sz = this.var_list.size();

        for (int i = 0; i < sz; i++) {
            String i_name = ((MVar) this.var_list.elementAt(i)).getName();
            if (_name.equals(i_name)) {
                return true;
            }
        }

        return false;
    }

    public String InsertMethod(MMethod _method) {
        String method_name = _method.name;
        int sz = this.method_list.size();

        for (int i = 0; i < sz; ++i) {
            if (((MMethod) this.method_list.elementAt(i)).name.equals(method_name)) {
                return "Repeat define";
            }
        }

        this.method_list.addElement(_method);
        return null;
    }

    public boolean Repeated_method(String var1) {
        int var2 = this.method_list.size();

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod) this.method_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return true;
            }
        }

        return false;
    }

    public int getVarIndex(String var1) {
        int var2 = this.var_list.size();

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MVar) this.var_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return var3;
            }
        }

        return -1;
    }
    /*
    find position of a member variable
     */
    public int getVarPos(String _name){ //used for piglet
        MClass tmpClass = this;
        int sum = 0;
        int tmp;
        while (true){
            tmp = tmpClass.getVarIndex(name);
            if (tmp != -1){
                break;
            }
            sum += 1 + tmpClass.var_list.size();
            //need to continue searching in the parent!!!
            tmpClass = classes.FindClassByName(tmpClass.parent_name);
        }
        return 4 * (1 + sum + tmp);
    }


    public Vector<Object> getMethodVec(String name){
        Vector<Object> ret = new Vector<>();
        MClass tmpClass = this;
        int i = 0;
        while (true){
            if (tmpClass.getMethod(name) != null){
                ret.add(4 * i);
                ret.add(tmpClass);
                return ret;
            }
            i += tmpClass.var_list.size() + 1;
            tmpClass = classes.FindClassByName(tmpClass.parent_name);
        }
    }

    public int getMethodIndex(String var1) {
        int var2 = this.method_list.size();

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod) this.method_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return var3;
            }
        }

        return -1;
    }

    public MVar getVar(String var_name) {
        int sz = var_list.size();
        for (int i = 0; i < sz; i++) {
            String now_name = ((MVar) var_list.elementAt(i)).getName();
            if (now_name.equals(var_name)) {
                return var_list.elementAt(i);
            }
        }
        String f = parent_name;
        while (!f.equals("")) {
            int szf = classes.FindClassByName(f).var_list.size();
            for (int i = 0; i < szf; i++) {
                String now_name = ((MVar) classes.FindClassByName(f).var_list.elementAt(i)).getName();
                if (now_name.equals(var_name)) {
                    return classes.FindClassByName(f).var_list.elementAt(i);
                }
            }
            f = classes.FindClassByName(f).parent_name;
        }

        return null;
    }

    public MMethod getMethod(String var1) {
        int var2 = this.method_list.size();

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod) this.method_list.elementAt(var3)).getName();
            if (var4.equals(var1)) {
                return (MMethod) this.method_list.elementAt(var3);
            }
        }

        for (String var7 = this.parent_name; !var7.equals(""); var7 = this.classes.FindClassByName(var7).parent_name) {
            int var8 = this.classes.FindClassByName(var7).method_list.size();

            for (int var5 = 0; var5 < var8; ++var5) {
                String var6 = ((MMethod) this.classes.FindClassByName(var7).method_list.elementAt(var5)).getName();
                if (var6.equals(var1)) {
                    return (MMethod) this.classes.FindClassByName(var7).method_list.elementAt(var5);
                }
            }
        }

        return null;
    }
}
