package br.ufsc.bridge.metafy.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

public class TypeUtils {

	public static final String LIST_QUALIFIED_NAME = "java.util.List";

	public static final String SET_QUALIFIED_NAME = "java.util.Set";

	private ProcessingEnvironment pEnv;

	private TypeMirror listType;

	private TypeMirror setType;

	public TypeUtils(ProcessingEnvironment processingEnv) {
		this.pEnv = processingEnv;

		this.listType = this.pEnv.getElementUtils().getTypeElement(LIST_QUALIFIED_NAME).asType();

		this.setType = this.pEnv.getElementUtils().getTypeElement(SET_QUALIFIED_NAME).asType();
	}

	public boolean isAssignableToList(TypeMirror typeMirror) {
		return this.pEnv.getTypeUtils().isAssignable(typeMirror, this.listType);
	}

	public boolean isAssignableToSet(TypeMirror typeMirror) {
		return this.pEnv.getTypeUtils().isAssignable(typeMirror, this.setType);
	}

}
