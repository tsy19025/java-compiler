package minijava.visitor;

import minijava.symboltable.*;
import minijava.syntaxtree.*;
import minijava.typecheck.PrintMsg;

import java.util.Enumeration;

/**
 * 类型检查的Visitor
 * 深度优先遍历
 * 检查内容包括：方法的重载错误，类的循环继承，赋值类型不匹配，方法调用参数不匹配，etc
 */
public class CheckVisitor extends GJDepthFirst<MType, MType> {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
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
     * f14 -> PrintStatement()
     * f15 -> "}"
     * f16 -> "}"
     */
    //CHANGED
    public MType visit(MainClass n, MType argu) {
        MType _ret = null;

        n.f0.accept(this, argu);
        // 获得主类的main方法
        String class_name = ((MIdentifier) n.f1.accept(this, argu)).getName(); //Identifier()
        MClass m_class = ((MClassList) argu).FindClassByName(class_name);
        MMethod m_method = m_class.method_list.elementAt(0); //Main Class
        n.f2.accept(this, argu);
        n.f3.accept(this, m_class);
        n.f4.accept(this, m_class);
        n.f5.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        n.f11.accept(this, m_method); //Identifier()
        n.f12.accept(this, argu);
        n.f13.accept(this, argu);
        n.f14.accept(this, m_method); //PrintStatement()
        n.f15.accept(this, argu);
        n.f16.accept(this, argu);
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
    //CHANGED
    public MType visit(ClassDeclaration n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        String class_name = ((MIdentifier) n.f1.accept(this, argu)).getName(); //Identifier()
        MClass m_class = ((MClassList) argu).FindClassByName(class_name);
        n.f2.accept(this, argu);
        n.f3.accept(this, m_class); //VarDeclaration()
        n.f4.accept(this, m_class); //MethodDeclaration()
        n.f5.accept(this, argu);
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
    //CHANGED
    public MType visit(ClassExtendsDeclaration n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        n.f0.accept(this, argu);
        String class_name = ((MIdentifier) n.f1.accept(this, argu)).getName(); //Identifier()
        n.f2.accept(this, argu);

        // Does the father class exist? If not, set to "Object"
        MType t = n.f3.accept(this, argu); //IdentifieR
        String father_class_name = t.getName();
        if (((MClassList) argu).FindClassByName(father_class_name) == null) {
            ((MClassList) argu).FindClassByName(class_name).parent_name = "Object";

            error_msg = "FATHER CLASS [" + father_class_name + "] NOT EXIST!!";
        }

        // check for cyclic inheritance
        MClass m_class = ((MClassList) argu).FindClassByName(class_name);
        MClass c = m_class;
        while (c != null && !c.parent_name.equals("") && !c.parent_name.equals("Object")) { //do not use ==!
            if (c.parent_name.equals(class_name)) {
                c.parent_name = "Object";
                error_msg = "EXTEND CIRCULATION";
                break;
            }
            if (((MClassList) argu).FindClassByName(c.parent_name) == null) {
                error_msg = "FATHER(or grand-...-grand father) CLASS [" + c.parent_name + "] UNDEFINED";
                break;
            }

            c = ((MClassList) argu).FindClassByName(c.parent_name);
        }

        if (error_msg != null) {
            PrintMsg.print(m_class.getLine(), m_class.getColumn(), error_msg);
        }

        n.f4.accept(this, argu);
        n.f5.accept(this, m_class); //VarDeclaration()
        n.f6.accept(this, m_class); //MethodDeclaration()
        n.f7.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
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
    //CHANGED
    public MType visit(MethodDeclaration n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu); //Type()

        // 检查重载错误 check for overload
        // 若与父类中既在方法重名，则返回类型与参数列表均应严格相等
        // 否则出错
        MIdentifier m = (MIdentifier) n.f2.accept(this, argu);
        String method_name = m.getName();
        MMethod m_method = ((MClass) argu).getMethod(method_name);
        MClass m_class = m_method.owner;
        String m_class_fname = m_class.parent_name;
        while (!m_class_fname.equals("") && !m_class_fname.equals("Object")) {
            MClass m_class_f = m_class.classes.FindClassByName(m_class_fname);
            int f_method_index = m_class_f.getMethodIndex(method_name);
            if (f_method_index != -1) {
                MMethod f_method = m_class_f.method_list.elementAt(f_method_index);
                if (!f_method.type.equals(m_method.type)) {    //check return type
                    error_msg = "METHOD [" + method_name + "] OVERLOAD ERROR, SHOULDN'T RETURN [" + m_method.type + "], SHOULD RETURN [" + f_method.type + "]";
                    break;
                } else if (f_method.par_list.size() != m_method.par_list.size()) {    //check number of parameters
                    error_msg = "METHOD [" + method_name + "] OVERLOAD ERROR (DIFFERENT PARA NUMBER)";
                    break;
                }
                int lenfm = f_method.par_list.size();
                for (int i = 0; i < lenfm; i++)    //check each parameter's type
                    if ((f_method.par_list.elementAt(i).getName() != m_method.par_list.elementAt(i).getName()) ||
                            (f_method.par_list.elementAt(i).type != m_method.par_list.elementAt(i).type)) {
                        error_msg = "OVERLOAD ERROR, PARA NO." + (i + 1) + " NOT MATCH";
                        break;
                    }
            }
            ;
            m_class_fname = m_class_f.parent_name;
        }
        if (error_msg != null)
            PrintMsg.print(m.getLine(), m.getColumn(), error_msg);
        error_msg = null;
        n.f3.accept(this, argu);
        n.f4.accept(this, m_method); //FormalParameterList()
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, m_method); // VarDeclaration()
        n.f8.accept(this, m_method); //Statement()
        n.f9.accept(this, argu);

        // 检查返回值与返回类型是否匹配 check Expression() with return type
        // 若返回普通Int,Boolean或Array型，则直接检查是否一致
        // 否则若返回类名且类名不匹配，检查返回类是否为方法返回类型类的子类
        MType m_ret = n.f10.accept(this, m_method); //Expression()
        String ret_type = m_method.type;
        boolean flag = false;
        if (m_ret instanceof MIdentifier) {    //返回符号表中存在的变量
            MVar ret_var = m_method.getVar(m_ret.getName());
            if (ret_var != null && m_method.owner.classes.FindClassByName(ret_var.type) != null) {    //如果返回的是类，检查父类是否匹配
                MClass r_class = m_method.owner.classes.FindClassByName(ret_var.type);
                while (r_class != null && !r_class.parent_name.equals("") && !r_class.parent_name.equals("Object")) {
                    r_class = m_method.owner.classes.FindClassByName(r_class.parent_name);
                    if (r_class != null && r_class.getName().equals(ret_type)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (ret_var != null && !flag && !ret_var.type.equals(ret_type))
                error_msg = "METHOD [" + method_name + "] RETURN TYPE [" + ret_var.type + "] NOT MATCH, SHOULD RETURN [" + ret_type + "]";
        } else if (m_ret instanceof MTypename) {    //返回符号表中不存在的值，形如"1","true","new A()","new A().a()"
            if (m_method.owner.classes.FindClassByName(((MTypename) m_ret).type) != null) {    //如果返回的是类，检查父类是否匹配
                MClass r_class = m_method.owner.classes.FindClassByName(((MTypename) m_ret).type);
                while (r_class != null && !r_class.parent_name.equals("") && !r_class.parent_name.equals("Object")) {
                    r_class = m_method.owner.classes.FindClassByName(r_class.parent_name);
                    if (r_class != null && r_class.getName().equals(ret_type)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag && !((MTypename) m_ret).type.equals(ret_type))
                error_msg = "METHOD [" + method_name + "] RETURN TYPE [" + ((MTypename) m_ret).type + "] NOT MATCH, SHOULD RETURN [" + ret_type + "]";
        } else {    //未知错误
            error_msg = "RETURN TYPE ERROR";
        }
        if (error_msg != null)
            PrintMsg.print(m_ret.getLine(), m_ret.getColumn(), error_msg);
        n.f11.accept(this, argu);
        n.f12.accept(this, argu);

        /* 判断是否有定义了未使用的变量 */
        if (PrintMsg.PRINT_LEVEL == 2) {
            for (int i = 0; i < m_method.var_list.size(); i++) {
                if (m_method.var_list.elementAt(i).isUsed == false) {
                    error_msg = "WARNING: UNUSED VARIABLE [" + m_method.var_list.elementAt(i).getName() + "]";
                    PrintMsg.print(m_method.var_list.elementAt(i).getLine(), m_method.var_list.elementAt(i).getColumn(), error_msg);
                }
            }
        }

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
    //CHANGED
    public MType visit(Type n, MType argu) {
        String error_msg = null;
        // 若从f0中得到的是理应在符号表中存在的变量且不存在，则报错
        MType _ret = n.f0.accept(this, argu); //ArrayType()
		if(_ret instanceof MIdentifier) {
			if(argu instanceof MClass){
				MClassList m_argu = ((MClass)argu).classes;
				if( ((MClassList)m_argu).FindIndexByName(_ret.getName()) == -1 )
					error_msg = "IN CLASS [" + argu.getName() + "] UNDEFINED CLASS TYPE [" + _ret.getName() + "]";
			}
			if(argu instanceof MMethod){
				MClassList m_argu = ((MMethod)argu).owner.classes;
				if( ((MClassList)m_argu).FindIndexByName(_ret.getName()) == -1 )
					error_msg = "IN METHOD [" + argu.getName() + "] UNDEFIEND CLASS TYPE [" + _ret.getName() + "]";
			}
		}
		if (error_msg != null)
			PrintMsg.print(_ret.getLine(), _ret.getColumn(), error_msg);

        return _ret;
    }


    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public MType visit(ArrayType n, MType argu) {
        MTypename _ret = null;
        MType tmp = n.f0.accept(this, argu);
        //return "return type"
        _ret = new MTypename("Array");
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "boolean"
     */
    public MType visit(BooleanType n, MType argu) {
        MTypename _ret = null;
        MType tmp = n.f0.accept(this, argu);
        //return "return type"
        _ret = new MTypename("Boolean");
        return _ret;
    }

    /**
     * f0 -> "int"
     */
    public MType visit(IntegerType n, MType argu) {
        MTypename _ret = null;
        MType tmp = n.f0.accept(this, argu);
        //return "return type"
        _ret = new MTypename("Int");
        return _ret;
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
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    //CHANGED
    public MType visit(AssignmentStatement n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        MIdentifier m_id = (MIdentifier) n.f0.accept(this, argu); //Identifier()
        n.f1.accept(this, argu);
        MType t = n.f2.accept(this, argu); //Expression()
        if (t == null) return _ret;
        String type1 = null, type2 = null;
        boolean flag = false;

        // 检查赋值语句左右类型是否匹配
        // 首先检查左值在符号表中是否存在
        // 其次检查左右类型是否匹配，若不匹配，则检查右边类型是否为左边子类
        if (((MMethod) argu).getVar(m_id.getName()) == null) {
            error_msg = "UNDECLARED VRIARABLE [" + m_id.getName() + "]";
            PrintMsg.print(m_id.getLine(), m_id.getColumn(), error_msg);
        } else {
            MVar m_var = ((MMethod) argu).getVar(m_id.getName());
            if (t instanceof MIdentifier && ((MIdentifier) t).getName().equals(m_id.getName()))
                ;
            else m_var.isInited = true; //左值已初始化
            type1 = m_var.type;

            if (t instanceof MIdentifier) {
                MVar m_var2 = ((MMethod) argu).getVar(t.getName());
                if (m_var2 != null) {
                    type2 = m_var2.type;
                    if (type1.equals(type2))
                        flag = true;
                    else {
                        MClass m_class = ((MMethod) argu).owner.classes.FindClassByName(type2);
                        while (m_class != null) {    //classname
                            m_class = ((MMethod) argu).owner.classes.FindClassByName(m_class.parent_name);
                            if (m_class != null && m_class.getName().equals(type1)) {
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            } else if (t instanceof MTypename) {
                type2 = ((MTypename) t).type;
                if (type1.equals(type2))
                    flag = true;
                else {
                    MClass m_class = ((MMethod) argu).owner.classes.FindClassByName(type2);
                    while (m_class != null) {    //classname
                        m_class = ((MMethod) argu).owner.classes.FindClassByName(m_class.parent_name);
                        if (m_class != null && m_class.getName().equals(type1)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) ((MMethod) argu).getVar(m_id.getName()).type = type2;
                }
            } else {
                error_msg = "ASSIGNMENT ERROR";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }

            if (!flag) {
                error_msg = "ASSIGNMENT TYPE [" + type1 + "](left) and [" + type2 + "](right) NOT MATCH";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
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
    //CHANGED
    public MType visit(ArrayAssignmentStatement n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查数组赋值操作
        // 首先检查数组变量是否类型为数组
        MIdentifier m_id = (MIdentifier) n.f0.accept(this, argu);
        if (((MMethod) argu).getVar(m_id.getName()) == null)
            error_msg = "UNDECLARED VARIABLE [" + m_id.getName() + "]";
        else if (!((MMethod) argu).getVar(m_id.getName()).type.equals("Array"))
            error_msg = "VARIABLE [" + m_id.getName() + "] MUST BE ARRAY";
        if (error_msg != null)
            PrintMsg.print(m_id.getLine(), m_id.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        // 其次检查下标是否为Int
        MType p = n.f2.accept(this, argu);
        if (p instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(p.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "ARRAY INDEX [" + v.getName() + "] MUST BE INT";
        } else if (p instanceof MTypename) {
            if (!((MTypename) p).type.equals("Int"))
                error_msg = "ARRAY INDEX [" + p.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(p.getLine(), p.getColumn(), error_msg);
        error_msg = null;


        n.f3.accept(this, argu);
        n.f4.accept(this, argu);

        // 最后检查右值类型是否为Int
        MType t = n.f5.accept(this, argu);
        if (t instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "VARIABLE [" + v.getName() + "] MUST BE INT";
        } else if (t instanceof MTypename) {
            if (!((MTypename) t).type.equals("Int"))
                error_msg = "VALUE TYPE [" + t.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t.getLine(), t.getColumn(), error_msg);

        n.f6.accept(this, argu);
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
    //UNFINISHED
    public MType visit(IfStatement n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        // 检查if里是否为布尔型
        MType t = n.f2.accept(this, argu);
        if (t != null) {
            if (t instanceof MTypename && ((MTypename) t).type != "Boolean") {
                error_msg = "IF OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
            if (t instanceof MIdentifier && ((MMethod) argu).getVar(t.getName()).type != "Boolean") {
                error_msg = "IF OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
        }
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    //UNFINISHED
    public MType visit(WhileStatement n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);

        // 检查while里是否为布尔型
        MType t = n.f2.accept(this, argu);
        if (t != null) {
            if (t instanceof MTypename && ((MTypename) t).type != "Boolean") {
                error_msg = "WHILE OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
            if (t instanceof MIdentifier && ((MMethod) argu).getVar(t.getName()).type != "Boolean") {
                error_msg = "WHILE OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
        }
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    //UNFINISHED
    public MType visit(PrintStatement n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MType t = n.f2.accept(this, argu);

        // 检查Print的是否为Int型
        if (t instanceof MTypename) {
            if (((MTypename) t).type == null) ((MTypename) t).type = "";
            if (!((MTypename) t).type.equals("Int"))
                error_msg = "PRINT PARA [" + t.getName() + "] NOT INT";
        } else if (t instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t.getName());
            if (v != null) {
                if (!v.type.equals("Int"))
                    error_msg = "PRINT PARA [" + t.getName() + "] NOT INT";
            }
        }
        if (error_msg != null)
            PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
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
    public MType visit(Expression n, MType argu) {
        MType _ret = n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    public MType visit(AndExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        MType t1 = n.f0.accept(this, argu);

        // 检查布尔操作左右是否为布尔型
        if (t1 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t1.getName());
            if (v != null && !v.type.equals("Boolean"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE BOOLEAN";
        } else if (t1 instanceof MTypename) {
            if (!((MTypename) t1).type.equals("Boolean"))
                error_msg = "OPERATOR [" + t1.getName() + "] MUST BE BOOLEAN";
        }
        if (error_msg != null)
            PrintMsg.print(t1.getLine(), t1.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        MType t2 = n.f2.accept(this, argu);
        if (t2 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t2.getName());
            if (v != null && !v.type.equals("Boolean"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE BOOLEAN";
        } else if (t2 instanceof MTypename) {
            if (!((MTypename) t2).type.equals("Boolean"))
                error_msg = "OPERATOR [" + t2.getName() + "] MUST BE BOOLEAN";
        }
        if (error_msg != null)
            PrintMsg.print(t2.getLine(), t2.getColumn(), error_msg);

        _ret = new MTypename("Boolean", "", t1.getLine(), t1.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public MType visit(CompareExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查比较操作左右是否为Int型
        MType t1 = n.f0.accept(this, argu);
        if (t1 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t1.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t1 instanceof MTypename) {
            if (!((MTypename) t1).type.equals("Int"))
                error_msg = "OPERATOR [" + t1.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t1.getLine(), t1.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        MType t2 = n.f2.accept(this, argu);
        if (t2 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t2.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t2 instanceof MTypename) {
            if (!((MTypename) t2).type.equals("Int"))
                error_msg = "OPERATOR [" + t2.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t2.getLine(), t2.getColumn(), error_msg);

        _ret = new MTypename("Boolean", "", t1.getLine(), t1.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public MType visit(PlusExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查加法操作左右是否为Int型
        MType t1 = n.f0.accept(this, argu);
        if (t1 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t1.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t1 instanceof MTypename) {
            if (!((MTypename) t1).type.equals("Int"))
                error_msg = "OPERATOR [" + t1.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t1.getLine(), t1.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        MType t2 = n.f2.accept(this, argu);
        if (t2 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t2.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t2 instanceof MTypename) {
            if (!((MTypename) t2).type.equals("Int"))
                error_msg = "OPERATOR [" + t2.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t2.getLine(), t2.getColumn(), error_msg);

        _ret = new MTypename("Int", "", t1.getLine(), t1.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public MType visit(MinusExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查减法操作左右是否为Int型
        MType t1 = n.f0.accept(this, argu);
        if (t1 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t1.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t1 instanceof MTypename) {
            if (!((MTypename) t1).type.equals("Int"))
                error_msg = "OPERATOR [" + t1.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t1.getLine(), t1.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        MType t2 = n.f2.accept(this, argu);
        if (t2 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t2.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t2 instanceof MTypename) {
            if (!((MTypename) t2).type.equals("Int"))
                error_msg = "OPERATOR [" + t2.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t2.getLine(), t2.getColumn(), error_msg);

        _ret = new MTypename("Int", "", t1.getLine(), t1.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public MType visit(TimesExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查乘法操作左右是否为Int型
        MType t1 = n.f0.accept(this, argu);
        if (t1 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t1.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t1 instanceof MTypename) {
            if (!((MTypename) t1).type.equals("Int"))
                error_msg = "OPERATOR [" + t1.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t1.getLine(), t1.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        MType t2 = n.f2.accept(this, argu);
        if (t2 instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t2.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "OPERATOR [" + v.getName() + "] MUST BE INT";
        } else if (t2 instanceof MTypename) {
            if (!((MTypename) t2).type.equals("Int"))
                error_msg = "OPERATOR [" + t2.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t2.getLine(), t2.getColumn(), error_msg);

        _ret = new MTypename("Int", "", t1.getLine(), t1.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public MType visit(ArrayLookup n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查数组操作
        // 先检查变量是否为数组类型
        MType t = n.f0.accept(this, argu);
        if (t instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t.getName());
            if (v != null && !v.type.equals("Array"))
                error_msg = "VARIABLE [" + v.getName() + "] MUST BE ARRAY";
        } else if (t instanceof MTypename) {
            if (!((MTypename) t).type.equals("Array"))
                error_msg = "VALUE TYPE [" + t.getName() + "] MUST BE ARRAY";
        }
        if (error_msg != null)
            PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
        error_msg = null;

        n.f1.accept(this, argu);

        // 再检查下标是否为Int型
        MType p = n.f2.accept(this, argu);
        if (p instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(p.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "ARRAY INDEX [" + v.getName() + "] MUST BE INT";
        } else if (p instanceof MTypename) {
            if (!((MTypename) p).type.equals("Int"))
                error_msg = "ARRAY INDEX [" + p.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(p.getLine(), p.getColumn(), error_msg);

        n.f3.accept(this, argu);
        _ret = new MTypename("Int", "", t.getLine(), t.getColumn());
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public MType visit(ArrayLength n, MType argu) {
        MType _ret = null;
        String error_msg = null;

        // 检查数组操作
        // 检查变量是否为数组类型
        MType t = n.f0.accept(this, argu);
        if (t instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t.getName());
            if (v != null && !v.type.equals("Array"))
                error_msg = "VARIABLE [" + v.getName() + "] MUST BE ARRAY";
        } else if (t instanceof MTypename) {
            if (!((MTypename) t).type.equals("Array"))
                error_msg = "VARIABLE [" + t.getName() + "] MUST BE ARRAY";
        }
        if (error_msg != null)
            PrintMsg.print(t.getLine(), t.getColumn(), error_msg);

        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        _ret = new MTypename("Int", "", t.getLine(), t.getColumn());
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
    //UNFINISHED
    public MType visit(MessageSend n, MType argu) {
        MType _ret = null;
        String class_name = null;
        String error_msg = null;
        MClassList m_classes = ((MMethod) argu).owner.classes;
        MClass m_class = null;

        // 判断函数操作是否合法
        // 首先判断调用者是否为类
        MType t = n.f0.accept(this, argu);
        if (t instanceof MTypename) {    //eg: new A().fun()
            class_name = ((MTypename) n.f0.accept(this, argu)).getName();
        } else {    //eg: a.fun()
            MVar m_var = ((MMethod) argu).getVar(t.getName());
            if (m_var == null) return _ret;
            if (m_var.type.equals("Int") || m_var.type.equals("Array") || m_var.type.equals("Boolean")) {
                error_msg = "METHOD OWNER [" + t.getName() + "] MUST BE A CLASS";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            } else class_name = m_var.type;
        }

        n.f1.accept(this, argu);

        // 其次判断方法是否在类中存在
        MIdentifier t_method = (MIdentifier) n.f2.accept(this, argu);
        String method_name = t_method.getName();
        if (class_name != null) {
            m_class = m_classes.FindClassByName(class_name);
            if (m_class != null && m_class.getMethod(method_name) == null) {
                error_msg = "METHOD [" + method_name + "] NOT FOUND";
                PrintMsg.print(t_method.getLine(), t_method.getColumn(), error_msg);
            } else if (m_class != null) {
                _ret = new MTypename(m_class.getMethod(method_name).type, "", t.getLine(), t.getColumn());
            } else {
                error_msg = "CLASS NAME ERROR";
                PrintMsg.print(t_method.getLine(), t_method.getColumn(), error_msg);
            }
        }
        error_msg = null;

        n.f3.accept(this, argu);

        // 最后判断参数列表是否匹配
        MArgs m_args = (MArgs) n.f4.accept(this, argu);
        if (m_class != null) {
            MMethod m_method = m_class.getMethod(method_name);
            if (m_method != null) {
                int arg_size;
                if (m_args == null)
                    arg_size = 0;
                else arg_size = m_args.args_list.size() - 1;
                if (m_method.par_list.size() != arg_size) {
                    error_msg = "PARA NUMBER OF [" + method_name + "] NOT MATCH, SHOULD BE [" + m_method.par_list.size() + "] PARA";
                } else {
                    for (int i = 0; i < m_method.par_list.size(); i++) {
                        if (m_args.args_list.elementAt(i) instanceof MIdentifier) {
                            boolean flg = false;
                            if (((MMethod) argu).getVar(m_args.args_list.elementAt(i).getName()) != null &&
                                    ((MMethod) argu).getVar(m_args.args_list.elementAt(i).getName()).type !=
                                            m_method.par_list.elementAt(i).type) {
                                MClass s_class = ((MMethod) argu).owner.classes.FindClassByName(((MMethod) argu).getVar(m_args.args_list.elementAt(i).getName()).type);
                                while (s_class != null && !(s_class.parent_name).equals("") && !(s_class.parent_name).equals("Object")) {
                                    s_class = ((MMethod) argu).owner.classes.FindClassByName(s_class.parent_name);
                                    if (s_class.getName().equals(m_method.par_list.elementAt(i).type)) {
                                        flg = true;
                                        break;
                                    }
                                }
                                if (flg == false) {
                                    error_msg = "PARA No." + (i + 1) + " TYPE NOT MATCH, SHOULD BE [" + m_method.par_list.elementAt(i).type + "]";
                                    break;
                                }
                            }
                        } else if (m_args.args_list.elementAt(i) instanceof MTypename) {
                            if (((MTypename) m_args.args_list.elementAt(i)).type !=
                                    m_method.par_list.elementAt(i).type) {
                                boolean flg = false;
                                MClass s_class = ((MMethod) argu).owner.classes.FindClassByName(((MTypename) m_args.args_list.elementAt(i)).type);
                                while (s_class != null && !s_class.parent_name.equals("") && !s_class.parent_name.equals("Object")) {
                                    s_class = ((MMethod) argu).owner.classes.FindClassByName(s_class.parent_name);
                                    if (s_class.getName().equals(m_method.par_list.elementAt(i).type)) {
                                        flg = true;
                                        break;
                                    }
                                }
                                if (flg == false) {
                                    error_msg = "PARA No." + (i + 1) + " TYPE NOT MATCH, SHOULD BE [" + s_class.getName() + "]";
                                    break;
                                }
                            }
                        } else {
                            error_msg = "PARAs NUMBER OF [" + method_name + "] NOT MATCH";
                        }
                    }
                }
            }
            if (error_msg != null)
                PrintMsg.print(t_method.getLine(), t_method.getColumn(), error_msg);
        }
        n.f5.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    //CHANGED
    public MType visit(ExpressionList n, MType argu) {
        MType _ret = new MArgs(argu);

        // 构造参数列表
        MType arg1 = (MType) n.f0.accept(this, argu);
        ((MArgs) _ret).args_list.addElement(arg1);

        // 将vector传入f1以添加元素
        MType argn = (MType) n.f1.accept(this, _ret);
        ((MArgs) _ret).args_list.addElement(argn);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    //CHANGED
    public MType visit(ExpressionRest n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);

        //接受参数中的vector并添加元素，但注意传给下一层的参数要回复原来上一层的argu，否则结构出错
        MType arg = (MType) n.f1.accept(this, ((MArgs) argu).argu);
        ((MArgs) argu).args_list.addElement(arg);
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
    //UNFINISHED
    public MType visit(PrimaryExpression n, MType argu) {
        MType _ret = null;
        MType tmp = n.f0.accept(this, argu);
        String error_msg = null;

        // 在这里判断大部分的未声明变量
        if (tmp instanceof MIdentifier) {
            String var_name = tmp.getName();
            if (((MMethod) argu).getVar(var_name) == null) {
                error_msg = "UNDECLARED VARIABLE [" + var_name + "]";
                PrintMsg.print(tmp.getLine(), tmp.getColumn(), error_msg);
            } else {
                ((MMethod) argu).getVar(var_name).isUsed = true;
                if (((MMethod) argu).getVarIndex(var_name) != -1 && ((MMethod) argu).getVar(var_name).isInited == false) {
                    error_msg = "UNINITIALIZED [" + var_name + "]";
                    if (PrintMsg.PRINT_LEVEL == 2) PrintMsg.print(tmp.getLine(), tmp.getColumn(), error_msg);
                }
            }
        }
        _ret = tmp;
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public MType visit(IntegerLiteral n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        _ret = new MTypename("Int", n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "true"
     */
    public MType visit(TrueLiteral n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        _ret = new MTypename("Boolean", n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> "false"
     */
    public MType visit(FalseLiteral n, MType argu) {
        MType _ret = null;
        n.f0.accept(this, argu);
        _ret = new MTypename("Boolean", n.f0.toString(), n.f0.beginLine, n.f0.beginColumn);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public MType visit(Identifier n, MType argu) {
        String identifier_name = n.f0.toString();
        MType _ret = null;
        _ret = new MIdentifier(n.f0.beginLine, n.f0.beginColumn, identifier_name);
        return _ret;
    }

    /**
     * f0 -> "this"
     */
    public MType visit(ThisExpression n, MType argu) {
        MType _ret = new MTypename(((MMethod) argu).owner.getName(), "", n.f0.beginLine, n.f0.beginColumn);
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
        String error_msg = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        // 数组操作检查
        // 判断下标是否为Int型
        MType t = n.f3.accept(this, argu);
        if (t instanceof MIdentifier) {
            MVar v = ((MMethod) argu).getVar(t.getName());
            if (v != null && !v.type.equals("Int"))
                error_msg = "ARRAY INDEX [" + v.getName() + "] MUST BE INT";
        } else if (t instanceof MTypename) {
            if (!((MTypename) t).type.equals("Int"))
                error_msg = "ARRAY INDEX [" + t.getName() + "] MUST BE INT";
        }
        if (error_msg != null)
            PrintMsg.print(t.getLine(), t.getColumn(), error_msg);

        n.f4.accept(this, argu);
        _ret = new MTypename("Array", "", t.getLine(), t.getColumn() - 7);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public MType visit(AllocationExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);

        // 判断类操作是否为类名
        // 并构造返回类型为该类
        MIdentifier m_class = (MIdentifier) n.f1.accept(this, argu);
        String class_name = m_class.getName();
        if ((((MMethod) argu).owner.classes).FindIndexByName(class_name) == -1) { //???
            error_msg = "UNDEFINED CLASS [" + class_name + "]";
            PrintMsg.print(m_class.getLine(), m_class.getColumn(), error_msg);
            class_name = "UNKNOWN";
        }
        _ret = new MTypename(class_name, "", m_class.getLine(), m_class.getColumn());
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public MType visit(NotExpression n, MType argu) {
        MType _ret = null;
        String error_msg = null;
        n.f0.accept(this, argu);

        // 判断布尔操作是否为布尔型
        MType t = n.f1.accept(this, argu);
        if (t != null) {
            if (t instanceof MTypename && ((MTypename) t).type != "Boolean") {
                error_msg = "OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
            if (t instanceof MIdentifier && ((MMethod) argu).getVar(t.getName()).type != "Boolean") {
                error_msg = "OPERATOR [" + t.getName() + "] NOT BOOLEAN";
                PrintMsg.print(t.getLine(), t.getColumn(), error_msg);
            }
        }
        _ret = new MTypename("Boolean", "", t.getLine(), t.getColumn() - 1);
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
        n.f2.accept(this, argu);
        return _ret;
    }

}