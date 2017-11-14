package fr.istic.vv;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.io.IOException;

public class MyTranslator implements Translator {

    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
        System.out.println("Starting");
    }

    public void onLoad(ClassPool classPool, String className) throws NotFoundException, CannotCompileException {
        if(className.contains("Addition")){
            CtClass ctClass = classPool.get(className);
            CtMethod ctMethod = ctClass.getDeclaredMethod("operate");
            ctMethod.setName("operateBis");
            System.out.println(ctMethod.getLongName());
            ctMethod.instrument(new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    f.replace("{ System.out.println(\"operateBis called\"); $_ = $proceed($$); }");
                }
            });
            try {
                System.out.println("try");
                ctClass.writeFile("target/classes");
                System.out.println("written ");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
