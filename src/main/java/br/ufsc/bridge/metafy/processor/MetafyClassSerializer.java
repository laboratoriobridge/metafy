package br.ufsc.bridge.metafy.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import br.ufsc.bridge.metafy.MetafyConstants;
import br.ufsc.bridge.metafy.util.TypeUtil;

public class MetafyClassSerializer {

	public void serialize(ProcessingEnvironment processingEnvironment, MetafyClass data) throws IOException {
		JavaFileObject javaFile = processingEnvironment.getFiler().createSourceFile(data.getCompleteName());

		String constantsClassName = data.getSimpleName();
		String constantsReferenceName = data.getSimpleReferenceName();

		OutputStream os = javaFile.openOutputStream();
		PrintWriter pw = new PrintWriter(os);

		pw.println(String.format("package %s;", data.getPackageName()));
		pw.println();

		for (String importString : data.getImports()) {
			pw.println(String.format("import %s;", importString));
		}

		pw.println();

		for (VariableElement e : data.getChildForms()) {
			TypeElement typeElement = TypeUtil.resolveVariableType(processingEnvironment, e);
			pw.println(String.format("import %s;",
					typeElement.getQualifiedName().toString().replace(typeElement.getSimpleName(), MetafyConstants.PREFIX + typeElement.getSimpleName())));
		}

		pw.println();

		pw.println(String.format("public class %s extends MetaBean<%s> {", constantsClassName, constantsReferenceName));
		pw.println();

		pw.println(String.format("\tpublic static %s meta = new %s();", constantsClassName, constantsClassName));
		pw.println();

		// Construtor padrão
		pw.println(String.format("\tpublic %s() {", constantsClassName));
		pw.println(String.format("\t\tsuper(%s.class);", constantsReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		// Construtor com parent
		pw.println(String.format("\tpublic %s(MetaBean<?> parent) {", constantsClassName));
		pw.println(String.format("\t\tsuper(parent, %s.class);", constantsReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		// Construtor com parent e alias
		pw.println(String.format("\tpublic %s(MetaBean<?> parent, String alias) {", constantsClassName));
		pw.println(String.format("\t\tsuper(parent, %s.class, alias);", constantsReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		for (VariableElement e : data.getChildForms()) {
			TypeElement typeElement = TypeUtil.resolveVariableType(processingEnvironment, e);
			pw.println(String.format("\tprivate %s%s %s;", MetafyConstants.PREFIX, typeElement.getSimpleName().toString(), e.getSimpleName()));
		}

		pw.println();

		for (VariableElement e : data.getAttributes()) {
			this.generateConstantAttribute(processingEnvironment, pw, e);
		}

		pw.println();

		for (VariableElement e : data.getChildForms()) {
			this.generateConstantMethod(processingEnvironment, pw, e);
			pw.println();
		}

		pw.println("}");

		pw.flush();

		pw.close();

	}

	private void generateConstantMethod(ProcessingEnvironment processingEnvironment, PrintWriter pw, VariableElement e) {
		TypeElement typeElement = TypeUtil.resolveVariableType(processingEnvironment, e);
		pw.println(String.format("\tpublic %s%s %s(){", MetafyConstants.PREFIX, typeElement.getSimpleName(), e.getSimpleName()));
		pw.println(String.format("\t\tif(%s == null){", e.getSimpleName()));
		pw.println(String.format("\t\t\t%s = new %s%s(this, \"%s\");", e.getSimpleName(), MetafyConstants.PREFIX, typeElement.getSimpleName().toString(), e.getSimpleName()));
		pw.println("\t\t}");
		pw.println(String.format("\t\treturn %s;", e.getSimpleName()));
		pw.println("\t}");
	}

	private void generateConstantAttribute(ProcessingEnvironment processingEnvironment, PrintWriter pw, VariableElement e) {
		String fieldTypeName = e.asType().toString();
		String simpleFieldTypeName = TypeUtil.resolveVariableType(processingEnvironment, e).getSimpleName().toString();
		String attrName = e.getSimpleName().toString();

		if (this.isSet(fieldTypeName)) {
			pw.println("\t@SuppressWarnings(\"unchecked\")");
			pw.println(String.format("\tpublic final MetaSet<%s> %s = createSet(\"%s\");", simpleFieldTypeName, attrName, attrName));
		} else if (this.isList(fieldTypeName)) {
			pw.println("\t@SuppressWarnings(\"unchecked\")");
			pw.println(String.format("\tpublic final <MetaList<%s> %s = createList(\"%s\");", simpleFieldTypeName, attrName, attrName));
		} else if (this.isMap(fieldTypeName)) {
			// TODO
			pw.println(String.format("\t// attributo %s do tipo Map não suportado", attrName));
		} else {
			pw.println(String.format("\tpublic final MetaField<%s> %s = createField(%s.class, \"%s\");", simpleFieldTypeName, attrName,
					simpleFieldTypeName,
					attrName));
		}
	}


	private boolean isSet(String qualifiedName) {
		return qualifiedName.startsWith("java.util.Set");
	}

	private boolean isList(String qualifiedName) {
		return qualifiedName.startsWith("java.util.List");
	}

	private boolean isMap(String qualifiedName) {
		return qualifiedName.startsWith("java.util.Map");
	}

}
