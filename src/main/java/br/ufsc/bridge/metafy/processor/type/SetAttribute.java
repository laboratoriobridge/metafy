package br.ufsc.bridge.metafy.processor.type;

import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import br.ufsc.bridge.metafy.MetaSet;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;

public class SetAttribute implements Attribute {

	private String attributeName;
	private String genericTypeName;
	private String importString;
	private VariableElement element;
	private DeclaredType type;


	public SetAttribute(DeclaredType type, VariableElement element) {
		super();
		this.type = type;
		this.element = element;
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnv) {
		this.attributeName = this.element.getSimpleName().toString();
		this.genericTypeName = this.type.getTypeArguments().get(0).toString();
		TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(this.genericTypeName);
		if (typeElement != null) {
			this.genericTypeName = typeElement.getSimpleName().toString();
			this.importString = typeElement.getQualifiedName().toString();
		}
	}

	@Override
	public void importTypes(MetafyClass metaClass) {
		metaClass.importType(MetaSet.class.getName());
		metaClass.importType(Set.class.getName());
		if (this.importString != null) {
			metaClass.importType(this.importString);
		}
	}


	@Override
	public void writeAttribute(PrintWriter pw) {
		pw.println(String.format("\tpublic final MetaSet<%s> %s = createSet(\"%s\");", this.genericTypeName, this.attributeName, this.attributeName));
	}

	@Override
	public void writeConstantMethod(PrintWriter pw) {
		// not used
	}
}
