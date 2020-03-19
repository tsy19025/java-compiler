//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package minijava.symboltable;

public class MType {
    public String name;
    public int line;
    public int column;

    public MType() {
        this.line = -1;
        this.column = -1;
    }

    public MType(int var1, int var2) {
        this.line = var1;
        this.column = var2;
    }

    public MType(int var1, int var2, String var3) {
        this.line = var1;
        this.column = var2;
        this.name = var3;
    }

    public String getName() {
        return this.name;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }
}
