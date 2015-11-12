package br.ufsc.bridge.metafy;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class FakeTypeElement {

	private TypeElement typeElement;
	ProcessingEnvironment processingEnv;
	private boolean arrayMode;

	public FakeTypeElement(ProcessingEnvironment processingEnv, TypeMirror typeMirror) {
		this.processingEnv = processingEnv;
		if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
			String type = typeMirror.toString();
			this.typeElement = processingEnv.getElementUtils().getTypeElement(type.substring(0, type.length() - 2));
			this.arrayMode = true;
		} else {
			this.typeElement = (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror);
			this.arrayMode = false;
		}
	}

	public FakeTypeElement(TypeElement typeElement) {
		this.typeElement = typeElement;
		this.arrayMode = false;
	}

	public String getQualifiedName() {
		return this.typeElement.getQualifiedName().toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean hasAnnotation(Class annotationType) {
		return this.typeElement.getAnnotation(annotationType) != null;
	}

	public String getSimpleName() {
		return this.typeElement.getSimpleName().toString();
	}

	public boolean isArray() {
		return this.arrayMode;
	}

}
