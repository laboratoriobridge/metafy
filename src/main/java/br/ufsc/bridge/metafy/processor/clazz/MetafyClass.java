package br.ufsc.bridge.metafy.processor.clazz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.ufsc.bridge.metafy.processor.MetafyProcessorConstants;
import br.ufsc.bridge.metafy.processor.exception.DefaultPackageException;
import br.ufsc.bridge.metafy.processor.type.Attribute;

public class MetafyClass {

	protected static final String PACKAGE_SEPARATOR = ".";

	protected String packageName;
	protected String simpleName;
	protected String simpleReferenceName;
	protected String completeName;
	protected Set<String> imports;
	protected List<Attribute> attributes;

	public MetafyClass(String completeName) {
		if (completeName.lastIndexOf(PACKAGE_SEPARATOR) > 0) {
			this.packageName = completeName.substring(0, completeName.lastIndexOf(PACKAGE_SEPARATOR));
			this.simpleReferenceName = completeName.substring(completeName.lastIndexOf(PACKAGE_SEPARATOR) + 1, completeName.length());
			this.simpleName = MetafyProcessorConstants.PREFIX + this.simpleReferenceName;
			this.completeName = this.packageName + PACKAGE_SEPARATOR + this.simpleName;
		} else {
			throw new DefaultPackageException("default package not suported by Metafy.");
		}

		this.imports = new TreeSet<>();
		this.attributes = new ArrayList<>();
	}

	public void importType(String element) {
		if (!this.imports.contains(element)) {
			this.imports.add(element);
		}
	}

	public void addAttribute(Attribute attribute) {
		if (!this.attributes.contains(attribute)) {
			this.attributes.add(attribute);
		}
	}

	public Set<String> getImports() {
		return this.imports;
	}

	public List<Attribute> getAttributes() {
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
