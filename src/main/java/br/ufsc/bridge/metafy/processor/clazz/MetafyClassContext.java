package br.ufsc.bridge.metafy.processor.clazz;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class MetafyClassContext {

	private Map<TypeParameterElement, TypeMirror> typeMap = new HashMap<>();
	private MetafyClass clazz;
	private VariableElement actualElement;

	public MetafyClass getClazz() {
		return this.clazz;
	}

	public void setClazz(MetafyClass clazz) {
		this.clazz = clazz;
	}

	public TypeMirror getTypeFor(TypeParameterElement element) {
		return this.typeMap.get(element);
	}

	public void addTypeMapping(TypeParameterElement element, TypeMirror type) {
		this.typeMap.put(element, type);
	}

	public VariableElement getActualElement() {
		return this.actualElement;
	}

	public void setActualElement(VariableElement actualElement) {
		this.actualElement = actualElement;
	}

}
