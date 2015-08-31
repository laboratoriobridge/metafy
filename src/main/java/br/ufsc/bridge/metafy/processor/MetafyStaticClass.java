package br.ufsc.bridge.metafy.processor;

import br.ufsc.bridge.metafy.MetafyConstants;

public class MetafyStaticClass extends MetafyClass {

	public MetafyStaticClass(String completeName) {
		super(completeName);
		String[] parts = completeName.split("\\.");
		this.packageName = this.packageName.substring(0, this.packageName.lastIndexOf("."));
		this.simpleName = MetafyConstants.PREFIX + parts[parts.length - 2] + "_" + parts[parts.length - 1];
		this.completeName = this.packageName + "." + this.simpleName;
	}
}
