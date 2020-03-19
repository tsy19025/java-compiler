//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

public class MTypename extends MType {
    public String type;
    public String val;

    public MTypename(String _type, String _val, int _line, int _column) {
        super(_line, _column);
        this.type = _type;
        if (!_val.equals("")) {
            this.name = _val;
        } else {
            this.name = _type;
        }

        this.val = _val;
    }

    public MTypename(String _type) {
        this.name = _type;
        this.type = _type;
    }
}
