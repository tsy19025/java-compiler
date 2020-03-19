//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

import java.util.Vector;

public class MClass extends MType {
    public MClassList classes;
    public String parent_name;
    public Vector<MVar> var_list = new Vector();
    public Vector<MMethod> method_list = new Vector();

    public MClass() {
        this.classes = null;
    }

    public MClass(MClassList _classes) {
        this.classes = _classes;
    }

    public MClass(MClassList _classes, int _line, int _column) {
        super(_line, _column);
        this.classes = _classes;
    }

    public MClass(MClassList _classes, String _type, int _line, int _column, String _name) {
        super(_line, _column, _name);
        this.classes = _classes;
    }

    public String InsertVar(MVar _var) {
        String var_name = _var.name;

        for(int i = 0, sz = this.var_list.size(); i < sz; i ++) {
            if (((MVar)this.var_list.elementAt(i)).name.equals(var_name)) {
                return "Repeat define";
            }
        }

        this.var_list.addElement(_var);
        return null;
    }

    public boolean Repeated_var(String _name) {
        int sz = this.var_list.size();

        for(int i = 0; i < sz; i ++) {
            String i_name = ((MVar)this.var_list.elementAt(i)).getName();
            if (_name.equals(i_name)) {
                return true;
            }
        }

        return false;
    }

    public String InsertMethod(MMethod _method) {
        String method_name = _method.name;
        int sz = this.method_list.size();

        for(int i = 0; i < sz; ++i) {
            if (((MMethod)this.method_list.elementAt(i)).name.equals(method_name)) {
                return "Repeat define";
            }
        }

        this.method_list.addElement(_method);
        return null;
    }

    public boolean Repeated_method(String var1) {
        int var2 = this.method_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod)this.method_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return true;
            }
        }

        return false;
    }

    public int getVarIndex(String var1) {
        int var2 = this.var_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MVar)this.var_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return var3;
            }
        }

        return -1;
    }

    public int getMethodIndex(String var1) {
        int var2 = this.method_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod)this.method_list.elementAt(var3)).getName();
            if (var1.equals(var4)) {
                return var3;
            }
        }

        return -1;
    }

    public MVar getVar(String var1) {
        int var2 = this.var_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MVar)this.var_list.elementAt(var3)).getName();
            if (var4.equals(var1)) {
                return (MVar)this.var_list.elementAt(var3);
            }
        }

        for(String var7 = this.parent_name; !var7.equals("") && !var7.equals("Object"); var7 = this.classes.FindClassByName(var7).parent_name) {
            int var8 = this.classes.FindClassByName(var7).var_list.size();

            for(int var5 = 0; var5 < var8; ++var5) {
                String var6 = ((MVar)this.classes.FindClassByName(var7).var_list.elementAt(var5)).getName();
                if (var6.equals(var1)) {
                    return (MVar)this.classes.FindClassByName(var7).var_list.elementAt(var5);
                }
            }
        }

        return null;
    }

    public MMethod getMethod(String var1) {
        int var2 = this.method_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MMethod)this.method_list.elementAt(var3)).getName();
            if (var4.equals(var1)) {
                return (MMethod)this.method_list.elementAt(var3);
            }
        }

        for(String var7 = this.parent_name; !var7.equals("") && !var7.equals("Object"); var7 = this.classes.FindClassByName(var7).parent_name) {
            int var8 = this.classes.FindClassByName(var7).method_list.size();

            for(int var5 = 0; var5 < var8; ++var5) {
                String var6 = ((MMethod)this.classes.FindClassByName(var7).method_list.elementAt(var5)).getName();
                if (var6.equals(var1)) {
                    return (MMethod)this.classes.FindClassByName(var7).method_list.elementAt(var5);
                }
            }
        }

        return null;
    }
}
