package com.njq.nongfadai.javassit;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import org.junit.Before;
import org.junit.Test;

public class JavassitTester {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWriteFile() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("com.njq.nongfadai.javassit.Dog");// compile-time
																// class
		cc.setSuperclass(pool.get("com.njq.nongfadai.javassit.Animal"));
		cc.writeFile();
	}

	@Test
	public void testToBytecode() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("com.njq.nongfadai.javassit.Dog");// compile-time
																// class
		cc.setSuperclass(pool.get("com.njq.nongfadai.javassit.Animal"));
		cc.toBytecode();
	}

	@Test
	public void testToClass() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("com.njq.nongfadai.javassit.Dog");// compile-time
																// class
		cc.setSuperclass(pool.get("com.njq.nongfadai.javassit.Animal"));
		cc.toClass();
	}

	@Test
	public void testDefineNewClass() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("com.njq.nongfadai.Man");
		cc.setSuperclass(pool.get("com.njq.nongfadai.javassit.Animal"));
		cc.setInterfaces(new CtClass[] { pool.get("java.io.Serializable") });
		cc.writeFile();
	}

	@Test
	public void testMakeInterface() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeInterface("com.njq.nongfadai.Eattable");
		cc.writeFile();
	}

	@Test
	public void testMethod() throws Exception {
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("com.njq.nongfadai.javassit.Hello");
		CtMethod method = cc.getDeclaredMethod("say");
		method.insertBefore("{ System.out.println(\"what can I do for you.:\"); }");
		method.insertAfter("{System.out.println(\"after say method.\");}");
		Class c = cc.toClass();
		Hello hello = (Hello) c.newInstance();
		hello.say();
	}

	// 创建类、添加属性、添加方法、添加构造函数
	@Test
	public void testCreateClassAndField() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("com.njq.nongfadai.Pig");

		// 创建属性
		CtField f1 = CtField.make("private String descs;", cc);
		CtField f2 = CtField.make("private String name;", cc);
		cc.addField(f1);
		cc.addField(f2);

		// 创建方法
		CtMethod method1 = CtMethod.make("public int getDescs(){return descs;}", cc);
		CtMethod method2 = CtMethod.make("public void setDescs(int descs){this.descs=descs;}", cc);
		cc.addMethod(method1);
		cc.addMethod(method2);

		// 添加构造器
		CtConstructor constructor = new CtConstructor(new CtClass[] { pool.get("java.lang.String"), pool.get("java.lang.String") },
				cc);
		constructor.setBody("{this.descs=descs; this.name=name;}");
		cc.addConstructor(constructor);

		cc.writeFile(); 
	}

}
