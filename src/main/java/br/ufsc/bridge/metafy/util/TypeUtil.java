package br.ufsc.bridge.metafy.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class TypeUtil {

	public static TypeElement resolveVariableType(ProcessingEnvironment processingEnv, VariableElement e) {
		TypeMirror typeMirror = e.asType();
		if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
			String type = typeMirror.toString();
			return processingEnv.getElementUtils().getTypeElement(type.substring(0, type.length() - 2));
		} else {
			return (TypeElement) processingEnv.getTypeUtils().asElement(e.asType());
		}
	}

}
