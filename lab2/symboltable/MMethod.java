/*
    方法
 */

package lab2.symboltable;

import java.util.Vector;

public class MMethod extends MIdentifier {
    public MClass owner; // 方法属于哪个类 / 语法树的父亲
    public String type; // 返回类型
    public Vector<MVar> var_list = new Vector(); // 局部变量列表
    public Vector<MVar> par_list = new Vector(); // 参数列表

    public MMethod() {
        this.owner = null;
    }

    public MMethod(MClass _owner) {
        this.owner = _owner;
    }

    public MMethod(MClass _owner, int _line, int _column, String _type) {
        super(_line, _column);
        this.owner = _owner;
        this.type = _type;
    }

    public MMethod(MClass _owner, int _line, int _column, String _name, String _type) {
        super(_line, _column, _name);
        this.owner = _owner;
        type = _type;
    }

    public String InsertPar(MVar _var) {
        if (this.IsRepeated(_var.name)) {
            return "Repeat define";
        } else {
            this.par_list.addElement(_var);
            return null;
        }
    }

    public int FindPar(MVar _var) {
        int sz = par_list.size();

        for (int i = 0; i < sz; i++) {
            if (((MVar) par_list.elementAt(i)).name.equals(_var.name)) {
                return i;
            }
        }

        return -1;
    }

    public int FindVar(MVar _var) {
        int sz = var_list.size();
        for (int i = 0; i < sz; i++) {
            if (((MVar) var_list.elementAt(i)).name.equals(_var.name)) {
                return i;
            }
        }

        return -1;
    }

    public int FindVarIndexByName(String _var_name) {
        int sz = this.var_list.size();

        for (int i = 0; i < sz; ++i) {
            String i_name = ((MVar) this.var_list.elementAt(i)).getName();
            if (i_name.equals(_var_name)) {
                return i;
            }
        }

        return -1;
    }

    public int FindParIndexByName(String _par_name) {
        int sz = this.par_list.size();

        for (int i = 0; i < sz; ++i) {
            String i_name = ((MVar) this.par_list.elementAt(i)).getName();
            if (i_name.equals(_par_name)) {
                return i;
            }
        }

        return -1;
    }

/*  Unused
    public MVar FindParByName(String var1) {
        int var2 = 0;

        for(int var3 = this.par_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.par_list.elementAt(var2)).name.equals(var1)) {
                return (MVar)this.par_list.elementAt(var2);
            }
        }

        return null;
    }
*/
    public int getVarnum(){
        return var_list.size();
    }

    public int getParnum(){
        return par_list.size();
    }

    public boolean haspar(String _name){
        for(int i = 0;i < par_list.size();i ++){
            if (par_list.elementAt(i).name.equals(_name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasvar(String _name){
        for(int i =0;i < var_list.size();i ++){
            if (var_list.elementAt(i).name.equals(_name)){
                return true;
            }
        }
        return false;
    }

    public String InsertVar(MVar _var) {
        if (this.IsRepeated(_var.name)) {
            return "repeat define";
        } else {
            this.var_list.addElement(_var);
            return null;
        }
    }

    /*
        也要在父类里面找
     */
    public MVar getVar(String var_name) {
        int sz = var_list.size();
        for (int i = 0; i < sz; i++) {
            String i_name = ((MVar) var_list.elementAt(i)).getName();
            if (i_name.equals(var_name)) {
                return var_list.elementAt(i);
            }
        }

        int sz_p = par_list.size();
        for (int i = 0; i < sz_p; i++) {
            String i_name = ((MVar) par_list.elementAt(i)).getName();
            if (i_name.equals((var_name))) {
                return par_list.elementAt(i);
            }
        }

        MClass o = owner;
        while (o != null && !o.getName().equals("")) {
            int sz_o = o.var_list.size();
            for (int i = 0; i < sz_o; i++) {
                String i_name = ((MVar) o.var_list.elementAt(i)).getName();
                if (i_name.equals(var_name)) {
                    return o.var_list.elementAt(i);
                }
            }
            o = o.classes.FindClassByName(o.parent_name);
        }
        return null;
    }

    private boolean IsRepeated(String var1) {
        int var2 = 0;

        int var3;
        for (var3 = this.par_list.size(); var2 < var3; ++var2) {
            if (((MVar) this.par_list.elementAt(var2)).name.equals(var1)) {
                return true;
            }
        }

        var2 = 0;

        for (var3 = this.var_list.size(); var2 < var3; ++var2) {
            if (((MVar) this.var_list.elementAt(var2)).name.equals(var1)) {
                return true;
            }
        }

        return false;
    }
}
