//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

import java.util.Vector;

public class MClassList extends MType {
    public Vector<MClass> class_list = new Vector();

    public MClassList() {
    }

    public MClassList(int var1, int var2) {
        super(var1, var2);
    }

    public MClassList(int var1, int var2, String var3) {
        super(var1, var2, var3);
    }

    public String InsertClass(MClass var1) {
        int var2 = 0;

        for(int var3 = this.class_list.size(); var2 < var3; ++var2) {
            if (((MClass)this.class_list.elementAt(var2)).name.equals(var1.name)) {
                return "repeat define class";
            }
        }

        this.class_list.addElement(var1);
        return null;
    }

    public int FindClass(MClass var1) {
        int var2 = 0;

        for(int var3 = this.class_list.size(); var2 < var3; ++var2) {
            if (((MClass)this.class_list.elementAt(var2)).name.equals(var1.name)) {
                return var2;
            }
        }

        return -1;
    }

    public int FindIndexByName(String var1) {
        int var2 = 0;

        for(int var3 = this.class_list.size(); var2 < var3; ++var2) {
            String var4 = ((MClass)this.class_list.elementAt(var2)).getName();
            if (var4.equals(var1)) {
                return var2;
            }
        }

        return -1;
    }

    public MClass FindClassByName(String var1) {
        int var2 = 0;

        for(int var3 = this.class_list.size(); var2 < var3; ++var2) {
            if (((MClass)this.class_list.elementAt(var2)).name.equals(var1)) {
                return (MClass)this.class_list.elementAt(var2);
            }
        }

        return null;
    }
}
