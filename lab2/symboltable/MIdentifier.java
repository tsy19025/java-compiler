//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lab2.symboltable;

public class MIdentifier extends MType {
    public String type;

    public MIdentifier() {
    }

    public MIdentifier(int _line, int _column) {
        super(_line, _column);
    }

    public MIdentifier(int _line, int _column, String _name) {
        super(_line, _column, _name);
    }

    public MIdentifier(String _type) {
        type = _type;
        name = _type;
    }

    public MIdentifier(String _type, String _name, int _line, int _column) {
        super(_line, _column);
        type = _type;
        if (!_name.equals("")) {
            name = _name;
        } else {
            name = _type;
        }
    }

    public MVar getVar(String _name){// virtual function
        return null;
    }
}
