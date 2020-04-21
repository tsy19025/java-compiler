package lab2.visitor;

import lab2.symboltable.*;
import lab2.syntaxtree.*;
import lab2.typecheck.PrintPiglet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

public class Trans2Piglet extends GJDepthFirst<MType, MType> {

    public int cur_temp = 20;
    public int cur_label = 0;
    public MClassList class_list = null;
//    public MClass cur_class = null;
//    public MMethod cur_method = null;
//    public Vector<String> code = new Vector<>();
    public HashMap<String, String> temp_list = new HashMap<>();//a temp var always use the same name
    public int paranum = 0;
    public int para;
    public int para_temp;

    public String get_temp_num(MMethod _method, String _name){
        MClass cur_class = _method.owner;
        String temp_name = cur_class.name + "_" + _method.name + "_" + _name;
        if (temp_list.containsKey(temp_name)){
            return temp_list.get(temp_name);
        }
        else{
            temp_list.put(temp_name, "" + cur_temp);
            cur_temp ++;
            return "" + (cur_temp - 1);
        }
    }

    public MType visit(NodeList n, MType argu) {
        MType _ret = null;
        int _count = 0;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this, argu);
            _count++;
        }
        return _ret;
    }

    public MType visit(NodeListOptional n, MType argu) {
        if (n.present()) {
            MType _ret = null;
            int _count = 0;
            for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                e.nextElement().accept(this, argu);
                _count++;
            }
            return _ret;
        } else
            return null;
    }

    public MType visit(NodeOptional n, MType argu) {
        if (n.present())
            return n.node.accept(this, argu);
        else
            return null;
    }

    public MType visit(NodeSequence n, MType argu) {
        MType _ret = null;
        int _count = 0;
        for (Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this, argu);
            _count++;
        }
        return _ret;
    }

    public MType visit(NodeToken n, MType argu) {
        return null;
    }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public MType visit(Goal n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    /*
    MAIN
    BEGIN
    ...
    END
     */
    public MType visit(MainClass n, MType argu) {
        MType _ret = null;
        class_list = (MClassList)argu;
        PrintPiglet.printmain();
        n.f0.accept(this, argu);
        String class_name = n.f1.accept(this, argu).getName();
        MClass main_class = class_list.FindClassByName(class_name);
        n.f2.accept(this, main_class);
        n.f3.accept(this, main_class);
        n.f4.accept(this, main_class);
        n.f5.accept(this, main_class);
        n.f6.accept(this, main_class);
        MMethod main_method = main_class.getMethod("main");
        n.f7.accept(this, main_method);
        n.f8.accept(this, main_method);
        n.f9.accept(this, main_method);
        n.f10.accept(this, main_method);
        n.f11.accept(this, main_method);
        n.f12.accept(this, main_method);
        n.f13.accept(this, main_method);
        n.f14.accept(this, main_method);
        n.f15.accept(this, main_method);
        n.f16.accept(this, main_method);
        n.f17.accept(this, main_method);
        PrintPiglet.printend();
        return _ret;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    public MType visit(TypeDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    /*
      No code here
     */
    public MType visit(ClassDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        String class_name = n.f1.accept(this, argu).getName();
        MClass cur_class = class_list.FindClassByName(class_name);
        n.f2.accept(this, cur_class);
        n.f3.accept(this, cur_class);
        n.f4.accept(this, cur_class);
        n.f5.accept(this, cur_class);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    /*
    No code here
     */
    public MType visit(ClassExtendsDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        String class_name = ((MIdentifier) n.f1.accept(this, argu)).getName();
        MClass cur_class = class_list.FindClassByName(class_name);
        n.f2.accept(this, cur_class);
        n.f3.accept(this, cur_class);
        n.f4.accept(this, cur_class);
        n.f5.accept(this, cur_class);
        n.f6.accept(this, cur_class);
        n.f7.accept(this, cur_class);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    /*
    No code here
     */
    public MType visit(VarDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    /*
        classname_methodname[number of parametres]
        BEGIN
        ...
        RETURN f10
        END
     */
    public MType visit(MethodDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        String method_name = n.f2.accept(this, argu).getName();
        MMethod cur_method = ((MClass) argu).getMethod(method_name);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        int cur_paranum = cur_method.getParnum();
        PrintPiglet.println(argu.name + "_" + method_name + "[" + (paranum + 1) + "]");
        PrintPiglet.printbegin();
        n.f7.accept(this, cur_method);
        n.f8.accept(this, cur_method);
        n.f9.accept(this, cur_method);
        PrintPiglet.print("RETURN ");
        n.f10.accept(this, cur_method);
        n.f11.accept(this, cur_method);
        n.f12.accept(this, cur_method);
        PrintPiglet.println("");
        PrintPiglet.printend();
        return _ret;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    public MType visit(FormalParameterList n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public MType visit(FormalParameter n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public MType visit(FormalParameterRest n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */

    public MType visit(Type n, MType argu){
        n.f0.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public MType visit(ArrayType n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "boolean"
     */
    public MType visit(BooleanType n, MType argu) {
        n.f0.accept(this, argu);
        return null;
    }

    /**
     * f0 -> "int"
     */
    public MType visit(IntegerType n, MType argu) {
        n.f0.accept(this, argu);
        return null;
    }

    /**
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     */
    public MType visit(Statement n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public MType visit(Block n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu); //TODO
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    /*
    f0 is temp:
    f0 is parameter:
    f0 is member:
    MOVE TEMP X EXP
    or
    HLOAD TEMP X TEMP 19 + bias
    HSTORE TEMP 19 + bias TEMP X
     */
    public MType visit(AssignmentStatement n, MType argu) {
        MType _ret = null;
        int para_num = -1;
        int temp_num = -1;
        String id_name = n.f0.accept(this, argu).name;
        MMethod cur_method = (MMethod)argu;
        MClass cur_class = cur_method.owner;
        MVar cur_var = (MVar)cur_method.getVar(id_name);
        if (cur_method.hasvar(id_name)){ //temp
            String tmp_num = get_temp_num(cur_method, id_name);
            PrintPiglet.print("MOVE TEMP " + tmp_num + " ");
        }
        else if (cur_method.haspar(id_name)){ // para
            para_num = cur_method.FindParIndexByName(id_name);
            if (para_num < 18){ //direct para
                PrintPiglet.print("MOVE TEMP " + (para_num + 1) + " ");
            }
            else{ // indirect para, taken from another loaded reg
                temp_num = cur_temp ++;
                PrintPiglet.println("HLOAD TEMP " + temp_num + " TEMP 19 " +
                        4 * (para_num - 18));
            }
        }
        else{ // member, get from the class
            PrintPiglet.print("HSTORE TEMP 0 " + cur_class.getVarPos(id_name) + " ");
        }
        n.f1.accept(this, argu);
        MIdentifier exp = (MIdentifier)n.f2.accept(this, argu);
        if (exp != null){
            if (exp instanceof MClass){ // nothing to do
                cur_var.type = exp.type;
            }
            else{
                if (exp.getLine() == 0 && exp.getColumn() == 0){ //not allocating
                    MVar tvar = (MVar)((MMethod)argu).getVar(exp.type);
                    cur_var.type = tvar.type;
                }
                else{ //allocating
                    cur_var.type = exp.type;
                }
            }
        }
        PrintPiglet.println("");
        if (cur_method.haspar(id_name) && para_num >= 18){
            PrintPiglet.println("HSTORE TEMP 19 " + 4 * (para_num - 18) +
                    " TEMP " + temp_num);
        }
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    /*
    MOVE TEMP i TEMP new_temp
    MOVE TEMP i TEMP 0 + bias
    CJUMP LT TEMP i 1 LABELI
    ERROR
    LABELI MOVE TEMP j f2

    if i > length ERROR
     */
    public MType visit(ArrayAssignmentStatement n, MType argu) {
        MType _ret = null;
        int i = cur_temp ++;
        int exp1 = cur_temp ++;
        int len_num = cur_temp ++;
        int labelt = cur_label ++;
        int labelf = cur_label ++;
        int labeli = cur_label ++;
        String id_name = n.f0.accept(this, argu).name;
        MMethod cur_method = (MMethod)argu;
        MClass cur_class = cur_method.owner;
        if (cur_method.hasvar(id_name)){
            PrintPiglet.println("MOVE TEMP " + i + " TEMP " +
                    get_temp_num(cur_method, id_name));
        }
        else{
            PrintPiglet.println("HLOAD TEMP " + i + " TEMP 0 " +
                    cur_class.getVarPos(id_name) + " ");
        }
        PrintPiglet.println("CJUMP LT TEMP " + i + " 1 L" + labeli);
        PrintPiglet.println("ERROR");//TODO
        PrintPiglet.println("L" + labeli + " MOVE TEMP " + exp1 + " ");
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.println("HLOAD TEMP " + len_num + " TEMP " + i + " 0");
        PrintPiglet.println("CJUMP LT TEMP " + exp1 + " TEMP " + len_num +
                " L" + labelf); //if i > length out of bounds
        PrintPiglet.println("MOVE TEMP " + i + " PLUS TEMP " + i +
                " TIMES 4 PLUS 1 TEMP " + exp1); //a[i] = a + 4 * (i + 1)
        PrintPiglet.print("HSTORE TEMP " + i + " 0 ");
        n.f5.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.println("JUMP L" + labelt);
        PrintPiglet.println("L" + labelf + " ERROR");
        PrintPiglet.println("L" + labelt + " NOOP");
        return _ret;
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    /*
    CJUMP f2 LABEL_FALSE
    f4
    LABELF: NOOP
    f6
    LABELT: NOOP
     */
    public MType visit(IfStatement n, MType argu) {
        MType _ret = null;
        int labelt = cur_label ++;
        int labelf = cur_label ++;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        PrintPiglet.print("CJUMP ");
        n.f2.accept(this, argu);
        PrintPiglet.println("L" + labelf);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        PrintPiglet.println("JUMP L" + labelt);
        n.f5.accept(this, argu);
        PrintPiglet.println("L" + labelf + " NOOP");
        n.f6.accept(this, argu);
        PrintPiglet.println("L" + labelt + " NOOP");//TODO
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    /*
    LABEL_START: CJUMP f2 LABELF
    f4
    LABELS
    LABELF: NOOP
     */
    public MType visit(WhileStatement n, MType argu) {
        MType _ret = null;
        int labels = cur_label ++;
        int labelf = cur_label ++;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        PrintPiglet.print("L" + labels + " CJUMP ");
        n.f2.accept(this, argu);
        PrintPiglet.println("L" + labelf);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        PrintPiglet.println("JUMP L" + labels);
        PrintPiglet.println("L" + labelf + " NOOP");
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    /*
    PRINT f2
     */
    public MType visit(PrintStatement n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("PRINT ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        PrintPiglet.println("");
        return _ret;
    }

    /**
     * f0 -> AndExpression()
     * | CompareExpression()
     * | PlusExpression()
     * | MinusExpression()
     * | TimesExpression()
     * | ArrayLookup()
     * | ArrayLength()
     * | MessageSend()
     * | PrimaryExpression()
     */
    /*
     */
    public MType visit(Expression n, MType argu) { //TODO
        MType _ret = null;
        _ret = n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    /*
    BEGIN
    MOVE TEMP return_num 0 //inintialization
    CJUMP f0 L_ret
    MOVE TEMP return_num f2
    L_ret NOOP //shortcut
    RETURN TEMP return_num
    END1
     */
    public MType visit(AndExpression n, MType argu) {
        MType _ret = null;
        int ret_num = cur_temp ++;
        int ret_label = cur_label ++;
        PrintPiglet.printbegin();
        PrintPiglet.println("MOVE TEMP " + ret_num + " 0");
        PrintPiglet.print("CJUMP ");
        n.f0.accept(this, argu);
        PrintPiglet.println("L" + ret_label);
        n.f1.accept(this, argu);
        PrintPiglet.print(" MOVE TEMP " + ret_num + " ");
        n.f2.accept(this, argu);
        PrintPiglet.println("L" + ret_label + " NOOP");
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + ret_num);
        PrintPiglet.printend();
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    /*
    LT f0 f2
     */
    public MType visit(CompareExpression n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("LT ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    /*
    PLUS f0 f2
     */
    public MType visit(PlusExpression n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("PLUS ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    /*
    MINUS f0 f2
     */
    public MType visit(MinusExpression n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("MINUS ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    /*
    TIMES f0 f2
     */
    public MType visit(TimesExpression n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("TIMES ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    /*
    MOVE TEMP i f0 (array's addr)
    CJUMP LT TEMP i 1 LABEL_init
    ERROR
    LABEL_init MOVE TEMP e1 f2

     */
    public MType visit(ArrayLookup n, MType argu) {
        MType _ret = null;
        int i = cur_temp ++;
        int exp1 = cur_temp ++;
        int retnum = cur_temp ++;
        int lennum = cur_temp ++;
        int labelt = cur_label ++;
        int labelf = cur_label ++;
        int labeli = cur_label ++;
        PrintPiglet.printbegin();
        PrintPiglet.print("MOVE TEMP " + i + " ");
        n.f0.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.println("CJUMP LT TEMP " + i + " 1 L" + labeli);
        PrintPiglet.println("ERROR");
        PrintPiglet.print("L" + labeli + " MOVE TEMP " + exp1 + " ");
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        PrintPiglet.println("");
        //TODO
        PrintPiglet.println("HLOAD TEMP " + lennum + " TEMP " + i + " 0");
        PrintPiglet.println("CJUMP LT TEMP " + exp1 + " TEMP " + lennum
            + " L" + labelf);
        PrintPiglet.println("HLOAD TEMP " + retnum + " PLUS TEMP " +
                i + " TIMES 4 PLUS 1 TEMP" + exp1 + " 0");
        PrintPiglet.println("JUMP L" + labelt);
        PrintPiglet.println("L" + labelf + " ERROR");
        PrintPiglet.println("L" + labelt + " NOOP");
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + retnum);
        PrintPiglet.printend();
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    /*
    -- length in array[0]
    BEGIN
    MOVE TEMP i f0

     */
    public MType visit(ArrayLength n, MType argu) {
        MType _ret = null;
        int i = cur_temp ++;
        int retnum = cur_temp ++;
        int labeli = cur_label ++;
        PrintPiglet.printbegin();
        PrintPiglet.print("MOVE TEMP " + i + " ");
        n.f0.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.println("CJUMP LT TEMP " + i + " 1 L" + labeli);
        PrintPiglet.println("ERROR");
        PrintPiglet.println("L" + labeli + " HLOAD TEMP " + retnum +
                " TEMP " + i + " 0");
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + retnum);
        PrintPiglet.printend();
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    /*
    -- vtable for class
    -- dtable for instance
    CALL
    BEGIN

     */
    public MType visit(MessageSend n, MType argu) {
        MType _ret = null;
        int vtnum = cur_temp ++;
        int dtnum = cur_temp ++;
        int temp_num = cur_temp ++;
        int labeli = cur_label ++;
        PrintPiglet.println("CALL");
        PrintPiglet.printbegin();
        PrintPiglet.print("MOVE TEMP " + vtnum + " ");
        MIdentifier exp1 = (MIdentifier)n.f0.accept(this, argu);
        PrintPiglet.println("");
        MMethod cur_method;
        MClass cur_class;
        if (exp1 instanceof MClass){
            cur_class = (MClass)exp1;
        }else if (exp1.getLine() == -1 && exp1.getColumn() == -1) {
            cur_class = class_list.FindClassByName(exp1.type);
        }
        else{
            cur_method = (MMethod)argu;
            cur_class = class_list.FindClassByName(
                    ((MVar)cur_method.getVar(exp1.name)).type);
        }
        n.f1.accept(this, argu);
        MIdentifier id2 = (MIdentifier)n.f2.accept(this, argu);
        Vector<Object> tmp_var_list = cur_class.getMethodVec(id2.name);
        MClass class_of_method = (MClass)tmp_var_list.get(1);
        MMethod calling_method = class_of_method.getMethod(id2.name); //TODO
        para = calling_method.getParnum();

        PrintPiglet.println("CJUMP LT TEMP " + vtnum + " 1 L" + labeli);
        PrintPiglet.println("ERROR");
        PrintPiglet.println("L" + labeli + " MOVE TEMP " + vtnum + " PLUS " +
                tmp_var_list.get(0).toString() + " TEMP " + vtnum);
        PrintPiglet.println("HLOAD TEMP " + temp_num + " TEMP " + vtnum + " 0");
        PrintPiglet.println("HLOAD TEMP " + dtnum + " TEMP " + temp_num + " " +
                4 * class_of_method.getMethodIndex(id2.name)); // *4! find the position of method
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + dtnum);
        PrintPiglet.printend();
        PrintPiglet.print("(TEMP " + vtnum + " ");
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        PrintPiglet.println(")");
        return class_list.FindClassByName(calling_method.type);
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    /*
    BEGIN
    MOVE TEMP i f0
    RETURN TEMP i
    ...
     */
    public MType visit(ExpressionList n, MType argu) {
        MType _ret = null;
        int temp_num = cur_temp ++;
        PrintPiglet.printbegin();
        PrintPiglet.print("MOVE TEMP " + temp_num + " ");
        n.f0.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + temp_num);
        PrintPiglet.printend();
        paranum = 1; //a new para list
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    /*

     */
    public MType visit(ExpressionRest n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        paranum ++; // count current number of para; if para > 18:
        if (paranum > 18){
            if (paranum == 19){
                int tnum = cur_temp ++;
                PrintPiglet.printbegin();
                PrintPiglet.println("MOVE TEMP " + tnum + "HALLOCATE " +
                        (4 * (para - 18)));
                para_temp = tnum;
            }
            int restnum = paranum - 19;
            PrintPiglet.print("HSTORE TEMP " + para_temp + " " +
                    4 * restnum + " ");
            n.f1.accept(this, argu);
            PrintPiglet.println("");
            if (paranum == para){
                PrintPiglet.print("RETURN ");
                PrintPiglet.println("TEMP " + para_temp);
                PrintPiglet.printend();
            }
        }
        else{
            int temp_cum = cur_temp ++;
            PrintPiglet.printbegin();
            PrintPiglet.print("MOVE TEMP " + temp_cum + " ");
            n.f1.accept(this, argu);
            PrintPiglet.println("");
            PrintPiglet.print("RETURN ");
            PrintPiglet.println("TEMP " + temp_cum);
            PrintPiglet.printend();
        }
        return _ret;
    }

    /**
     * f0 -> IntegerLiteral()
     * | TrueLiteral()
     * | FalseLiteral()
     * | Identifier()
     * | ThisExpression()
     * | ArrayAllocationExpression()
     * | AllocationExpression()
     * | NotExpression()
     * | BracketExpression()
     */
    /*
    f0 is member:
    f0 is para:
    f0 is temp:
     */
    public MType visit(PrimaryExpression n, MType argu) { //return MIdentifier
        MType _ret = null;
        MType f0 = n.f0.accept(this, argu);
        if (f0 instanceof MClass){
            return f0;
        }
        if (f0 instanceof MIdentifier) {
            if (((MIdentifier) f0).getLine() == -1 &&
                    ((MIdentifier) f0).getColumn() == -1) {
                return f0; //f0 is just allocated in temp list
            }
            String id_name = f0.name;
            MMethod cur_method = (MMethod) argu;
            MClass cur_class = cur_method.owner;
            if (cur_method.hasvar(id_name)) {
                String temp_num = get_temp_num(cur_method, id_name);
                PrintPiglet.print("TEMP " + temp_num);
            } else if (cur_method.haspar(id_name)) {
                int i_par = cur_method.FindParIndexByName(id_name);
                if (i_par < 18) {
                    PrintPiglet.print("TEMP " + (i_par + 1) + " ");
                } else {
                    i_par -= 18;
                    int new_temp_num = cur_temp ++;
                    PrintPiglet.printbegin();
                    PrintPiglet.println("HLOAD TEMP " + new_temp_num + " TEMP 19 " + 4 * i_par);
                    PrintPiglet.print("RETURN ");
                    PrintPiglet.println("TEMP " + new_temp_num);
                    PrintPiglet.printend();
                }
            } else {
                int tnum = cur_temp++;
                PrintPiglet.printbegin();
                PrintPiglet.println("HLOAD TEMP " + tnum + " TEMP 0 " +
                        cur_class.getVarPos(id_name));
                PrintPiglet.print("RETURN ");
                PrintPiglet.println("TEMP " + tnum);
                PrintPiglet.printend();
            }
            return f0;
        }
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public MType visit(IntegerLiteral n, MType argu) {
        MType _ret = null;
        PrintPiglet.print(n.f0.toString() + " ");
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "true"
     */
    public MType visit(TrueLiteral n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("1 ");
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "false"
     */
    public MType visit(FalseLiteral n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("0 ");
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */

    public MType visit(Identifier n, MType argu) {
        MType _ret = null;
        String name = n.f0.toString();
        _ret = new MIdentifier(name, name, 0, 0);
        return _ret;
    }

    /**
     * f0 -> "this"
     */
    /*
    TEMP 0
     */
    public MType visit(ThisExpression n, MType argu) {
        MType _ret = null;
        PrintPiglet.print("TEMP 0");
        _ret = ((MMethod)argu).owner;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public MType visit(ArrayAllocationExpression n, MType argu) {
        MType _ret = null;
        int exp = cur_temp ++;
        int i = cur_temp ++;
        int temp_num = cur_temp ++;
        int labels = cur_label ++;
        int labeln = cur_label ++;
        PrintPiglet.printbegin();
        PrintPiglet.print("MOVE TEMP " + exp + " ");
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        PrintPiglet.println("");
        PrintPiglet.println("MOVE TEMP " + i + " HALLOCATE TIMES 4 " +
                "PLUS 1 TEMP " + exp);
        PrintPiglet.println("HSTORE TEMP " + i + " 0 TEMP " + exp);
        // inintializee with 0
        PrintPiglet.println("MOVE TEMP " + temp_num + " 0");
        PrintPiglet.println("L" + labels + "  CJUMP LT TEMP " +
                temp_num + " TEMP " + exp + " L" + labeln);
        PrintPiglet.println("HSTORE PLUS TEMP " + i + " TIMES 4 PLUS 1 TEMP "
                + temp_num + " 0 0 ");
        PrintPiglet.println("MOVE TEMP " + temp_num + " PLUS 1 TEMP " + temp_num);
        PrintPiglet.println("JUMP L" + labels);
        PrintPiglet.println("L" + labeln + " NOOP");

        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + i);
        PrintPiglet.printend();
        n.f4.accept(this, argu);

        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    /*
    Allocate new var in dtable
     */
    public MType visit(AllocationExpression n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        String class_name = n.f1.accept(this, argu).name;
        MClass cur_class = class_list.FindClassByName(class_name);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        int dtnum, v_alloced = 0;
        int vtnum = cur_temp ++;
        PrintPiglet.printbegin();
        PrintPiglet.println("MOVE TEMP " + vtnum + " HALLOCATE " +
                cur_class.alloc_size);
        while (cur_class != null){
            dtnum = cur_temp ++;
            PrintPiglet.println("MOVE TEMP " + dtnum + " HALLOCATE " +
                    (4 * cur_class.method_list.size()));
            for(int i = 0;i < cur_class.method_list.size();i ++){
                PrintPiglet.println("HSTORE TEMP " + dtnum + " " + 4 * i +
                        " " + cur_class.name + "_" +
                        cur_class.method_list.elementAt(i).name);
            }
            PrintPiglet.println("HSTORE TEMP " + vtnum + " " +
                    4 * v_alloced + " TEMP " + dtnum);
            v_alloced ++;
            int len = cur_class.var_list.size();
            for(int i = v_alloced;i < v_alloced + len; i ++){
                PrintPiglet.println("HSTORE TEMP " + vtnum + " " +
                        4 * i + " 0");
            }
            v_alloced += len;
            cur_class = class_list.FindClassByName(cur_class.parent_name);
        }
        PrintPiglet.print("RETURN ");
        PrintPiglet.println("TEMP " + vtnum);
        PrintPiglet.printend();
        _ret = new MIdentifier(class_name, class_name, -1, -1);//new: -1
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public MType visit(NotExpression n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        PrintPiglet.print("MINUS 1 ");
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public MType visit(BracketExpression n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        if (_ret instanceof MIdentifier) {
            ((MIdentifier)_ret).line = -1;
            ((MIdentifier)_ret).line = -1;
        }
        n.f2.accept(this, argu);
        return _ret;
    }

}