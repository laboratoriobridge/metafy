package br.ufsc.bridge.metafy.processor.type;

import java.io.PrintWriter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import br.ufsc.bridge.metafy.processor.MetafyProcessorConstants;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassContext;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassFactory;

public class MetaReferenceAttribute implements Attribute {

	private String attributeName;
	private String typeName;
	private String importString;
	private VariableElement element;
	private DeclaredType type;
	private MetafyClassContext context;


	public MetaReferenceAttribute(DeclaredType type, MetafyClassContext context, VariableElement element) {
		super();
		this.type = type;
		this.context = context;
		this.element = element;
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnv) {
		TypeElement typeElement = (TypeElement) this.type.asElement();
		this.attributeName = this.element.getSimpleName().toString();
		String simpleName = typeElement.getSimpleName().toString();
		if (!this.type.getTypeArguments().isEmpty()) {
			MetafyClassContext innerContext = new MetafyClassContext();

			StringBuilder innerClassNameBuilder = new StringBuilder();
			innerClassNameBuilder.append(typeElement.getQualifiedName().toString());

			for(int i=0; i < this.type.getTypeArguments().size(); i++) {
				innerClassNameBuilder.append(((DeclaredType) this.type.getTypeArguments().get(i)).asElement().getSimpleName());

				innerContext.addTypeMapping(typeElement.getTypeParameters().get(i), this.type.getTypeArguments().get(i));
			}

			MetafyClass innerClass;
			String innerClassName = innerClassNameBuilder.toString();
			if (!this.context.getClazz().hasInnerClassFor(innerClassName)) {
				innerClass = MetafyClassFactory.create(innerClassName, typeElement, innerContext);
				innerClass.getAttributes().stream().forEach(attribute -> attribute.initialize(processingEnv));

				this.context.getClazz().addInnerClass(innerClassName, innerClass);
			} else {
				innerClass = this.context.getClazz().getInnerClasses().get(innerClassName);
			}

			this.typeName = innerClass.getSimpleName();
			this.importString = null;
		} else if (typeElement.getModifiers().contains(Modifier.STATIC)) {
			TypeElement parentTypeElement = (TypeElement) typeElement.getEnclosingElement();
			this.typeName = MetafyProcessorConstants.PREFIX + parentTypeElement.getSimpleName() + "_" + simpleName;
			this.importString = parentTypeElement.getQualifiedName().toString().replace(parentTypeElement.getSimpleName(), this.typeName);
		} else {
			this.typeName = MetafyProcessorConstants.PREFIX + simpleName;
			this.importString = typeElement.getQualifiedName().toString().replace(simpleName, this.typeName);
		}
	}

	@Override
	public void importTypes(MetafyClass metaClass) {
		if (this.importString != null) {
			metaClass.importType(this.importString);
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
	}
}
