package br.ufsc.bridge.metafy.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class TypeUtil {

	public static TypeElement resolveVariableType(ProcessingEnvironment processingEnv, VariableElement e) {
		return (TypeElement) processingEnv.getTypeUtils().asElement(e.asType());
	}

}
