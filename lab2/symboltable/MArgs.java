/*
    �����б�
    ������鴫��Ĳ����б��뷽����ĵĲ���Vector�Ƿ�һ��
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
