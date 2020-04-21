//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lab2.symboltable;

public class MTypename extends MType {
    public String type; //类型
    public String val; //表达式值，从PrimaryExpression读

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
