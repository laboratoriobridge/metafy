package br.ufsc.bridge.metafy.processor.clazz;

import br.ufsc.bridge.metafy.processor.MetafyProcessorConstants;
import br.ufsc.bridge.metafy.processor.exception.DefaultPackageException;

public class MetafyStaticClass extends MetafyClass {

	public MetafyStaticClass(String completeName) {
		super(completeName);
		String[] parts = completeName.split("\\.");
		if (this.packageName.lastIndexOf(PACKAGE_SEPARATOR) > 0) {
			this.packageName = this.packageName.substring(0, this.packageName.lastIndexOf(PACKAGE_SEPARATOR));
			this.simpleName = MetafyProcessorConstants.PREFIX + parts[parts.length - 2] + "_" + parts[parts.length - 1];
			this.completeName = this.packageName + PACKAGE_SEPARATOR + this.simpleName;
		} else {
			throw new DefaultPackageException("default package not suported by Metafy.");
		}
	}
}
