//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

import java.util.Vector;

public class MMethod extends MIdentifier {
    public MClass owner;
    public String type;
    public Vector<MVar> var_list = new Vector();
    public Vector<MVar> par_list = new Vector();

    public MMethod() {
        this.owner = null;
    }

    public MMethod(MClass var1) {
        this.owner = var1;
    }

    public MMethod(MClass var1, int var2, int var3, String var4) {
        super(var2, var3);
        this.owner = var1;
        this.type = var4;
    }

    public MMethod(MClass var1, int var2, int var3, String var4, String var5) {
        super(var2, var3, var4);
        this.owner = var1;
    }

    public String InsertPar(MVar var1) {
        if (this.IsRepeated(var1.name)) {
            return "Repeat define";
        } else {
            this.par_list.addElement(var1);
            return null;
        }
    }

    public int FindPar(MVar var1) {
        int var2 = 0;

        for(int var3 = this.par_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.par_list.elementAt(var2)).name.equals(var1.name)) {
                return var2;
            }
        }

        return -1;
    }

    public int FindVar(MVar var1) {
        int var2 = 0;

        for(int var3 = this.var_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.var_list.elementAt(var2)).name.equals(var1.name)) {
                return var2;
            }
        }

        return -1;
    }

    public int getVarIndex(String var1) {
        int var2 = this.var_list.size();

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MVar)this.var_list.elementAt(var3)).getName();
            if (var4.equals(var1)) {
                return var3;
            }
        }

        return -1;
    }

    public MVar FindParByName(String var1) {
        int var2 = 0;

        for(int var3 = this.par_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.par_list.elementAt(var2)).name.equals(var1)) {
                return (MVar)this.par_list.elementAt(var2);
            }
        }

        return null;
    }

    public String InsertVar(MVar var1) {
        if (this.IsRepeated(var1.name)) {
            return "repeat define";
        } else {
            this.var_list.addElement(var1);
            return null;
        }
    }

    public MVar getVar(String var1) {
        int var2 = this.var_list.size();

        int var3;
        for(var3 = 0; var3 < var2; ++var3) {
            String var4 = ((MVar)this.var_list.elementAt(var3)).getName();
            if (var4.equals(var1)) {
                return (MVar)this.var_list.elementAt(var3);
            }
        }

        var3 = this.par_list.size();

        String var5;
        for(int var9 = 0; var9 < var3; ++var9) {
            var5 = ((MVar)this.par_list.elementAt(var9)).getName();
            if (var5.equals(var1)) {
                return (MVar)this.par_list.elementAt(var9);
            }
        }

        MClass var10 = this.owner;

        for(var5 = var10.getName(); var10 != null && !var5.equals("") && !var5.equals("Object"); var5 = var10.getName()) {
            int var6 = var10.var_list.size();

            for(int var7 = 0; var7 < var6; ++var7) {
                String var8 = ((MVar)var10.var_list.elementAt(var7)).getName();
                if (var8.equals(var1)) {
                    return (MVar)var10.var_list.elementAt(var7);
                }
            }

            var10 = var10.classes.FindClassByName(var10.parent_name);
        }

        return null;
    }

    private boolean IsRepeated(String var1) {
        int var2 = 0;

        int var3;
        for(var3 = this.par_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.par_list.elementAt(var2)).name.equals(var1)) {
                return true;
            }
        }

        var2 = 0;

        for(var3 = this.var_list.size(); var2 < var3; ++var2) {
            if (((MVar)this.var_list.elementAt(var2)).name.equals(var1)) {
                return true;
            }
        }

        return false;
    }
}
