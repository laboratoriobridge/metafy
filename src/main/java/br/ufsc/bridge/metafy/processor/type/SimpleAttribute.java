package br.ufsc.bridge.metafy.processor.type;

import java.io.PrintWriter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import br.ufsc.bridge.metafy.MetaField;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;

public class SimpleAttribute implements Attribute {

	private String attributeName;
	private String typeName;
	private String importString;
	private VariableElement element;
	private DeclaredType type;


	public SimpleAttribute(DeclaredType type, VariableElement element) {
		super();
		this.type = type;
		this.element = element;
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnv) {
		this.attributeName = this.element.getSimpleName().toString();
		TypeElement typeElement = (TypeElement) this.type.asElement();
		this.typeName = typeElement.getSimpleName().toString();
		this.importString = typeElement.getQualifiedName().toString();
	}

	@Override
	public void importTypes(MetafyClass metaClass) {
		metaClass.importType(MetaField.class.getName());
		if (this.importString != null) {
			metaClass.importType(this.importString);
		}
	}

	@Override
	public void writeAttribute(PrintWriter pw) {
		pw.println(String.format("\tpublic final MetaField<%s> %s = createField(%s.class, \"%s\");", this.typeName, this.attributeName, this.typeName,
				this.attributeName));
	}

	@Override
	public void writeConstantMethod(PrintWriter pw) {
		// not used
	}
}
