package lab2;

import lab2.symboltable.*;
import lab2.syntaxtree.*;
import lab2.visitor.*;

import java.io.*;

public class Main {
	public static void main(String args[]) {
		try {
			InputStream in = new FileInputStream(args[0]);
			Node root = new MiniJavaParser(in).Goal();
			MType allClassList = new MClassList();
	        root.accept(new BuildSymbolTableVisitor(), allClassList);
	        root.accept(new TypeCheckVisitor(), allClassList);
	        if (ErrorPrinter.getSize() == 0) {
	        	System.out.println("Program type checked successfully");
            } else {
            	System.out.println("Type error");
            }
	        ErrorPrinter.printAll();
		}
	}
}
