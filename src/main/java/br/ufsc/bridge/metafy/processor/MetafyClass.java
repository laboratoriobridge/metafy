package br.ufsc.bridge.metafy.processor;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.bridge.metafy.FakeTypeElement;
import br.ufsc.bridge.metafy.MetafyConstants;

public class MetafyClass {

	protected String packageName;
	protected String simpleName;
	protected String simpleReferenceName;
	protected String completeName;
	protected List<String> imports;
	protected List<FakeTypeElement> fakeTypes;

	public MetafyClass(String completeName) {
		this.packageName = completeName.substring(0, completeName.lastIndexOf("."));
		this.simpleReferenceName = completeName.substring(completeName.lastIndexOf(".") + 1, completeName.length());
		this.simpleName = MetafyConstants.PREFIX + this.simpleReferenceName;
		this.completeName = this.packageName + "." + this.simpleName;
		this.imports = new ArrayList<>();
		this.fakeTypes = new ArrayList<>();
	}

	public void importType(String element) {
		if (!this.imports.contains(element)) {
			this.imports.add(element);
		}
	}

	public void addFakeType(FakeTypeElement fakeElement) {
		if (!this.fakeTypes.contains(fakeElement)) {
			this.fakeTypes.add(fakeElement);
		}
	}

	public List<String> getImports() {
		return this.imports;
	}

	public List<FakeTypeElement> getFakeTypes() {
		return this.fakeTypes;
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
