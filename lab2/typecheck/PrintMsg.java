package lab2.typecheck;

import lab2.symboltable.MClass;
import lab2.symboltable.MClassList;
import lab2.symboltable.MMethod;

import java.util.Vector;

/**
 *
 */
public class PrintMsg {
    public static Vector<String> errors = new Vector<String>();

    public static int PRINT_LEVEL = 0; // 0:提交 1:Debug

    public static void print(int line, int column, String error_msg) {
        String msg = "At Line " + line + " Column " + column + ": " + error_msg;
        errors.addElement(msg);
    }

    public static void printAll() {
        int sz = errors.size();
        if (sz == 0) {
            System.out.println("Program type checked successfully");
        }
        for (int i = 0; i < sz; i++) {
            System.out.println(errors.elementAt(i));
        }
    }

    //打印符号表
    public static void printSymbolTable(MClassList root) {
        for (int i = 0; i < root.class_list.size(); i++) {
            MClass m_class = root.class_list.elementAt(i);
            System.out.print("Class: " + m_class.getName());
            if (!m_class.parent_name.equals("")) {
                System.out.print(" extends Class: " + m_class.parent_name);
            }
            System.out.println();
            for (int j = 0; j < m_class.var_list.size(); j++) {
                System.out.println("\tVariable: " + m_class.var_list.elementAt(j).getName() + " (" + m_class.var_list.elementAt(j).type + ")");
            }
            for (int j = 0; j < m_class.method_list.size(); j++) {
                MMethod m_method = m_class.method_list.elementAt(j);
                System.out.println("\tMethod: " + m_method.getName());
                System.out.println("\t\tReturn Type: " + m_method.type);
                if (m_method.par_list.size() == 0)
                    System.out.println("\t\tPar: void");
                for (int k = 0; k < m_method.par_list.size(); k++) {
                    System.out.println("\t\tPar: " + m_method.par_list.elementAt(k).getName() + "(" + m_method.par_list.elementAt(k).type + ")");
                }
                for (int k = 0; k < m_method.var_list.size(); k++) {
                    System.out.println("\t\tVariable: " + m_method.var_list.elementAt(k).getName() + " (" + m_method.var_list.elementAt(k).type + ")");
                }
            }
        }
        System.out.println();
    }
}

