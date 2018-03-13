package br.ufsc.bridge.metafy.processor.type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import br.ufsc.bridge.metafy.processor.MetafyProcessorConstants;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassContext;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassFactory;
import br.ufsc.bridge.metafy.processor.clazz.MetafyInnerClassSerializer;
import br.ufsc.bridge.metafy.processor.exception.UnexpectedException;

public class MetaReferenceAttribute implements Attribute {

	private String attributeName;
	private String typeName;
	private String importString;
	private VariableElement element;
	private DeclaredType type;
	private MetafyClass innerClass;


	public MetaReferenceAttribute(DeclaredType type, VariableElement element) {
		super();
		this.type = type;
		this.element = element;
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnv) {
		TypeElement typeElement = (TypeElement) this.type.asElement();
		this.attributeName = this.element.getSimpleName().toString();
		String simpleName = typeElement.getSimpleName().toString();
		if (typeElement.getModifiers().contains(Modifier.STATIC)) {
			TypeElement parentTypeElement = (TypeElement) typeElement.getEnclosingElement();
			this.typeName = MetafyProcessorConstants.PREFIX + parentTypeElement.getSimpleName() + "_" + simpleName;
			this.importString = parentTypeElement.getQualifiedName().toString().replace(parentTypeElement.getSimpleName(), this.typeName);
		} else {
			this.typeName = MetafyProcessorConstants.PREFIX + simpleName;
			this.importString = typeElement.getQualifiedName().toString().replace(simpleName, this.typeName);
		}
		if (!this.type.getTypeArguments().isEmpty()) {
			MetafyClassContext context = new MetafyClassContext();

			for(int i=0; i < this.type.getTypeArguments().size(); i++) {
				context.addTypeMapping(typeElement.getTypeParameters().get(i), this.type.getTypeArguments().get(i));
			}

			this.innerClass = MetafyClassFactory.create(typeElement, context);

			this.innerClass.getAttributes().stream().forEach(attribute -> attribute.initialize(processingEnv));

			this.importString = null;
		}
	}

	@Override
	public void importTypes(MetafyClass metaClass) {
		if (this.importString != null) {
			metaClass.importType(this.importString);
		} else if (this.innerClass != null) {
			this.innerClass.getImports().stream().forEach(metaClass::importType);
			this.innerClass.getAttributes().stream().forEach(attribute -> attribute.importTypes(metaClass));
		}
	}

	@Override
	public void writeAttribute(PrintWriter pw) {
		pw.println(String.format("\tprivate %s %s;", this.typeName, this.attributeName));
	}

	@Override
	public void writeConstantMethod(PrintWriter pw) {
		pw.println(String.format("\tpublic %s %s(){", this.typeName, this.attributeName));
		pw.println(String.format("\t\tif(%s == null){", this.attributeName));
		pw.println(String.format("\t\t\t%s = new %s(this, \"%s\");", this.attributeName, this.typeName, this.attributeName));
		pw.println("\t\t}");
		pw.println(String.format("\t\treturn %s;", this.attributeName));
		pw.println("\t}");
		pw.println();

		if (this.innerClass != null) {
			StringWriter out = new StringWriter();
			PrintWriter innerPw = new PrintWriter(new BufferedWriter(out));
			new MetafyInnerClassSerializer().serialize(this.innerClass, innerPw);
			innerPw.flush();
			innerPw.close();
			BufferedReader reader = new BufferedReader(new StringReader(out.toString()));
			String sCurrentLine;
			try {
				while ((sCurrentLine = reader.readLine()) != null) {
					pw.println("\t"+sCurrentLine);
				}
				reader.close();
			} catch (IOException e) {
				throw new UnexpectedException(e);
			}
		}
	}
}
