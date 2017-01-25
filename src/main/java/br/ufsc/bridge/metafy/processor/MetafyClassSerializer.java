package br.ufsc.bridge.metafy.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

import br.ufsc.bridge.metafy.FakeTypeElement;

public class MetafyClassSerializer {

	public void serialize(ProcessingEnvironment processingEnvironment, MetafyClass data) throws IOException {
		JavaFileObject javaFile = processingEnvironment.getFiler().createSourceFile(data.getCompleteName());

		String constantsClassName = data.getSimpleName();
		String constantsReferenceName = data.getSimpleReferenceName();

		OutputStream os = javaFile.openOutputStream();
		PrintWriter pw = new PrintWriter(os);

		pw.println(String.format("package %s;", data.getPackageName()));
		pw.println();

		Set<String> imports = new TreeSet<>();
		for (String importString : data.getImports()) {
			imports.add(String.format("import %s;", importString));
		}
		for (FakeTypeElement e : data.getFakeTypes()) {
			for (String value : e.getImport()) {
				imports.add(value);
			}
		}
		for (String value : imports) {
			pw.println(value);
		}

		pw.println();

		pw.println(String.format("public class %s extends MetaBean<%s> {", constantsClassName, constantsReferenceName));
		pw.println();

		pw.println(String.format("\tpublic static final %s meta = new %s();", constantsClassName, constantsClassName));
		pw.println();

		for (FakeTypeElement e : data.getFakeTypes()) {
			e.writeAttribute(pw);
		}

		pw.println();

		for (FakeTypeElement e : data.getFakeTypes()) {
			e.writeConstantMethod(pw);
		}

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

		pw.println("}");

		pw.flush();

		pw.close();

	}
}
