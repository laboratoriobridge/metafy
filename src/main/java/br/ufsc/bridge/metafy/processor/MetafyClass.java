package br.ufsc.bridge.metafy.processor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;

import br.ufsc.bridge.metafy.FakeTypeElement;
import br.ufsc.bridge.metafy.MetafyConstants;

public class MetafyClass {

	protected String packageName;
	protected String simpleName;
	protected String simpleReferenceName;
	protected String completeName;
	protected List<String> imports;
	protected List<VariableElement> childForms;
	protected List<VariableElement> attributes;

	public MetafyClass(String completeName) {
		this.packageName = completeName.substring(0, completeName.lastIndexOf("."));
		this.simpleReferenceName = completeName.substring(completeName.lastIndexOf(".") + 1, completeName.length());
		this.simpleName = MetafyConstants.PREFIX + this.simpleReferenceName;
		this.completeName = this.packageName + "." + this.simpleName;
		this.imports = new ArrayList<String>();
		this.childForms = new ArrayList<VariableElement>();
		this.attributes = new ArrayList<VariableElement>();
	}

	public void importType(String element) {
		if (!this.imports.contains(element)) {
			this.imports.add(element);
		}
	}

	public void importType(FakeTypeElement element) {
		if (!this.imports.contains(element.getQualifiedName().toString())) {
			this.imports.add(element.getQualifiedName().toString());
		}
	}

	public void addChildForm(VariableElement element) {
		if (!this.childForms.contains(element)) {
			this.childForms.add(element);
		}
	}

	public void addAttribute(VariableElement element) {
		if (!this.attributes.contains(element)) {
			this.attributes.add(element);
		}
	}

	public List<String> getImports() {
		return this.imports;
	}

	public List<VariableElement> getChildForms() {
		return this.childForms;
	}

	public List<VariableElement> getAttributes() {
		return this.attributes;
	}

	public String getSimpleName() {
		return this.simpleName;
	}

	public String getSimpleReferenceName() {
		return this.simpleReferenceName;
	}

	public String getCompleteName() {
		return this.completeName;
	}

	public String getPackageName() {
		return this.packageName;
	}
}
