package br.ufsc.bridge.metafy.processor.clazz;

import java.io.PrintWriter;

public class MetafyInnerClassSerializer {

	public void serialize(MetafyClass data, PrintWriter pw) {
		String metaClassName = data.getSimpleName();
		String metaReferenceName = data.getSimpleReferenceName();

		pw.println(String.format("public static class %s extends MetaBean<%s> {", metaClassName, metaReferenceName));
		pw.println();

		data.getAttributes().stream().forEach(attribute -> attribute.writeAttribute(pw));

		pw.println();

		// Construtor with parent and alias
		pw.println(String.format("\tprotected %s(MetaBean<?> parent, String alias) {", metaClassName));
		pw.println(String.format("\t\tsuper(parent, %s.class, alias);", metaReferenceName));
		pw.println(String.format("\t}"));
		pw.println();

		data.getAttributes().stream().forEach(attribute -> attribute.writeConstantMethod(pw));

		pw.println("}");
	}
}
