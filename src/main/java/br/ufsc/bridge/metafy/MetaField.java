package br.ufsc.bridge.metafy;

/**
 * MetaField represents field meta information
 *
 * @param <T> field type
 */
public class MetaField<T> {

	private final String alias;
	private Class<T> type;
	private MetaField<?> parent;

	public MetaField(Class<T> type, String alias) {
		this(null, type, alias);
	}

	public MetaField(MetaField<?> parent, Class<T> type, String alias) {
		this.alias = alias;
		this.type = type;
		this.parent = parent;
	}

	public String getAlias() {
		return this.alias;
	}

	public Class<T> getType() {
		return this.type;
	}

	public MetaField<?> getParent() {
		return this.parent;
	}

}