//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

public class MVar extends MIdentifier {
    public String type;
    public int begin;
    public int end;
    public MType owner;
    public boolean isInited;
    public boolean isUsed;

    public MVar() {
        this.type = null;
        this.owner = null;
    }

    public MVar(String _type, MType _owner) {
        this.type = _type;
        this.owner = _owner;
    }

    public MVar(MType _owner, int _line, int _column, String _type) {
        super(_line, _column);
        this.type = _type;
        this.owner = _owner;
    }

    public MVar(MType _owner, int _line, int _column, String _name, String _type) {
        super(_line, _column, _name);
        this.type = _type;
        this.owner = _owner;
    }
}
