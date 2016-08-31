package br.ufsc.bridge.metafy;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class FakeTypeElement {

	private boolean arrayMode = false;
	private boolean primitive = false;
	private boolean internalMetafy = false;
	private String qualifiedName;
	private String simpleName;
	private String attributeName;

	private boolean list = false;

	private boolean set = false;

	private String genericQualifiedName = null;

	public FakeTypeElement(ProcessingEnvironment processingEnv, VariableElement variable) {
		this.attributeName = variable.getSimpleName().toString();

		TypeMirror typeMirror = variable.asType();
		String typeToString = typeMirror.toString();

		if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
			this.arrayMode = true;

			String type = typeToString.substring(0, typeToString.length() - 2);
			TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(type);
			if (typeElement == null) {
				this.mountPrimitiveType(type);
			} else {
				this.mountObjectType(processingEnv, typeElement, typeMirror);
			}
		} else {
			if (typeMirror.getKind().isPrimitive()) {
				this.mountPrimitiveType(typeToString);
			} else {
				this.mountObjectType(processingEnv, (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror), typeMirror);
			}
		}
	}

	private void mountPrimitiveType(String type) {
		this.primitive = true;
		this.simpleName = type;
	}

	private void mountObjectType(ProcessingEnvironment processingEnv, TypeElement typeElement, TypeMirror typeMirror) {
		if (typeElement.getAnnotation(Metafy.class) != null) {
			this.internalMetafy = true;
		}
		this.simpleName = typeElement.getSimpleName().toString();
		this.qualifiedName = typeElement.getQualifiedName().toString();

		this.list = this.isList(this.qualifiedName);
		this.set = this.isSet(this.qualifiedName);

		if (this.list || this.set) {
			List<? extends TypeMirror> typeArguments = ((DeclaredType) typeMirror).getTypeArguments();
			if (!typeArguments.isEmpty()) {
				TypeMirror genericElement = typeArguments.get(0);
				TypeElement asElement = (TypeElement) processingEnv.getTypeUtils().asElement(genericElement);
				if (asElement != null) {
					this.simpleName = asElement.getSimpleName().toString();
					this.genericQualifiedName = asElement.getQualifiedName().toString();
				}
			}
		}
	}

	public List<String> getImport() {
		List<String> imports = new ArrayList<>();
		if (this.internalMetafy) {
			imports.add(String.format("import %s;", this.qualifiedName.replace(this.simpleName, MetafyConstants.PREFIX + this.simpleName)));

		} else if (!this.primitive) {

			if (!this.qualifiedName.startsWith("java.lang")) {
				imports.add(String.format("import %s;", this.qualifiedName));
			}
			if (this.genericQualifiedName != null) {
				imports.add(String.format("import %s;", this.genericQualifiedName));
			}
			if (this.list) {
				imports.add(String.format("import %s;", MetaList.class.getName()));
			} else if (this.set) {
				imports.add(String.format("import %s;", MetaSet.class.getName()));
			} else {
				imports.add(String.format("import %s;", MetaField.class.getName()));
			}
		}
		return imports;
	}

	public void writeAttribute(PrintWriter pw) {
		if (this.internalMetafy) {
			pw.println(String.format("\tprivate %s%s %s;", MetafyConstants.PREFIX, this.simpleName, this.attributeName));
		} else {
			this.generateConstantAttribute(pw);
		}
	}

	private void generateConstantAttribute(PrintWriter pw) {
		if (!this.primitive && this.set) {
			pw.println(String.format("\tpublic final MetaSet<%s> %s = createSet(\"%s\");", this.simpleName, this.attributeName, this.attributeName));
		} else if (!this.primitive && this.list) {
			pw.println(String.format("\tpublic final MetaList<%s> %s = createList(\"%s\");", this.simpleName, this.attributeName, this.attributeName));
		} else if (!this.primitive && this.isMap(this.qualifiedName)) {
			// TODO
			pw.println(String.format("\t// attributo %s do tipo Map não suportado", this.attributeName));
		} else if (this.arrayMode) {
			pw.println(String.format("\tpublic final MetaField<%s[]> %s = createField(%s[].class, \"%s\");", this.simpleName, this.attributeName, this.simpleName,
					this.attributeName));
		} else {
			pw.println(String.format("\tpublic final MetaField<%s> %s = createField(%s.class, \"%s\");", this.simpleName, this.attributeName, this.simpleName, this.attributeName));
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

	public void writeConstantMethod(PrintWriter pw) {
		if (this.internalMetafy) {
			pw.println(String.format("\tpublic %s%s %s(){", MetafyConstants.PREFIX, this.simpleName, this.attributeName));
			pw.println(String.format("\t\tif(%s == null){", this.attributeName));
			pw.println(String.format("\t\t\t%s = new %s%s(this, \"%s\");", this.attributeName, MetafyConstants.PREFIX, this.simpleName, this.attributeName));
			pw.println("\t\t}");
			pw.println(String.format("\t\treturn %s;", this.attributeName));
			pw.println("\t}");
			pw.println();
		}
	}

}
