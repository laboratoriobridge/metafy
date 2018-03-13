package br.ufsc.bridge.metafy.processor.clazz;

import java.io.PrintWriter;

public class MetafyInnerClassSerializer {

	public void serialize(MetafyClass data, PrintWriter pw) {
		String metaClassName = data.getSimpleName();
		String metaReferenceName = data.getSimpleReferenceName();

		pw.println(String.format("private static class %s extends MetaBean<%s> {", metaClassName, metaReferenceName));
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
	}
}
