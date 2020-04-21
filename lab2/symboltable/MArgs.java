/*
    参数列表
    用来检查传入的参数列表与方法里的的参数Vector是否一致
 */
package lab2.symboltable;

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
