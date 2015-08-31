package br.ufsc.bridge.metafy;

import java.util.List;

public class MetaList<TYPE> extends MetaField<List<TYPE>> {

	public MetaList(String alias) {
		this(null, alias);
	}

	@SuppressWarnings("unchecked")
	public MetaList(MetaField<?> parent, String alias) {
		super(parent, (Class<List<TYPE>>) (Class<?>) List.class, alias);
	}

}
