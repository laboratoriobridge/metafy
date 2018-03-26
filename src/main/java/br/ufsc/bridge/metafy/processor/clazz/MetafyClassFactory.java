package br.ufsc.bridge.metafy.processor.clazz;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;

import br.ufsc.bridge.metafy.MetaBean;
import br.ufsc.bridge.metafy.processor.type.Attribute;
import br.ufsc.bridge.metafy.processor.type.AttributeVisitor;

public class MetafyClassFactory {

	private MetafyClassFactory() {
		// utility class
	}

	public static MetafyClass create(TypeElement typeElement) {
		return create(typeElement, new MetafyClassContext());
	}

	public static MetafyClass create(TypeElement typeElement, MetafyClassContext context) {
		return create(typeElement.getQualifiedName().toString(), typeElement, context);
	}

	public static MetafyClass create(String name, TypeElement typeElement, MetafyClassContext context) {
		MetafyClass data;

		if (typeElement.getModifiers().contains(Modifier.STATIC)) {
			data = new MetafyStaticClass(name, typeElement.getQualifiedName().toString());
		} else {
			data = new MetafyClass(name, typeElement.getQualifiedName().toString());
		}

		data.importType(MetaBean.class.getName());
		data.importType(typeElement.getQualifiedName().toString());
		context.setClazz(data);
		visitAttributes(typeElement, data, context);
		return data;
	}

	private static void visitAttributes(TypeElement typeElement, MetafyClass data, MetafyClassContext context) {
		for (VariableElement e : ElementFilter.fieldsIn(typeElement.getEnclosedElements())) {
			context.setActualElement(e);
			Attribute classAttribute = e.asType().accept(new AttributeVisitor(), context);
			if (classAttribute != null) {
				data.addAttribute(classAttribute);
			}
		}
	}

}
