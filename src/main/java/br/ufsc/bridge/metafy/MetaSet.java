package br.ufsc.bridge.metafy;

import java.util.Set;

public class MetaSet<TYPE> extends MetaField<Set<TYPE>> {

	public MetaSet(String alias) {
		this(null, alias);
	}

	@SuppressWarnings("unchecked")
	public MetaSet(MetaField<?> parent, String alias) {
		super((Class<Set<TYPE>>) (Class<?>) java.util.Set.class, alias);
	}

}
