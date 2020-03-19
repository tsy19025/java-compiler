//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

import java.util.Vector;

public class MArgs extends MType {
    public Vector<MType> args_list = new Vector();
    public MType argu;

    public MArgs(MType _parent) {
        this.argu = _parent;
    }

    public MArgs() {
        this.argu = null;
    }
}
