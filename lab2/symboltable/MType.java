//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
/*
    其他符号的基类
 */
package lab2.symboltable;

public class MType {
    public String name; //符号名
    public int line;    //出现的行
    public int column;  //出现的列

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


