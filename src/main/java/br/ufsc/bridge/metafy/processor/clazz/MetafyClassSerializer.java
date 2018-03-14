package br.ufsc.bridge.metafy.processor.clazz;

import java.io.PrintWriter;

import javax.annotation.Generated;
import javax.annotation.processing.ProcessingEnvironment;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

public class MetafyClassSerializer {

	public void serialize(ProcessingEnvironment processingEnvironment, MetafyClass data, PrintWriter pw) {
		String metaClassName = data.getSimpleName();
		String metaReferenceName = data.getSimpleReferenceName();

		if (data.getPackageName() != null) {
			pw.println(String.format("package %s;", data.getPackageName()));
			pw.println();
		}

		data.importType(Generated.class.getName());

		data.getAttributes().stream()
				.forEach(attribute -> {
					attribute.initialize(processingEnvironment);
					attribute.importTypes(data);
				});

		data.getImports().stream()
				.filter(importString -> !importString.startsWith("java.lang"))
				.map(importString -> String.format("import %s;", importString))
				.forEach(pw::println);

		pw.println();

		pw.println(String.format("@Generated(\"%s\")", MetafyProcessor.class.getName()));
		pw.println(String.format("public class %s extends MetaBean<%s> {", metaClassName, metaReferenceName));
		pw.println();

		pw.println(String.format("\tpublic static final %s meta = new %s();", metaClassName, metaClassName));
		pw.println();

		data.getAttributes().stream().forEach(attribute -> attribute.writeAttribute(pw));

		pw.println();

		// Default construtor
		pw.println(String.format("\tpublic %s() {", metaClassName));
		pw.println(String.format("\t\tsuper(%s.class);", metaReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		// Construtor with parent
		pw.println(String.format("\tpublic %s(MetaBean<?> parent) {", metaClassName));
		pw.println(String.format("\t\tsuper(parent, %s.class);", metaReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		// Construtor with parent and alias
		pw.println(String.format("\tpublic %s(MetaBean<?> parent, String alias) {", metaClassName));
		pw.println(String.format("\t\tsuper(parent, %s.class, alias);", metaReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		data.getAttributes().stream().forEach(attribute -> attribute.writeConstantMethod(pw));

		pw.println("}");

		pw.flush();

		pw.close();
	}
}
