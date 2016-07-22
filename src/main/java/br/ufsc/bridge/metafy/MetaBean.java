package br.ufsc.bridge.metafy;

/**
 * MetaBean provides base class for BeanMeta
 *
 * @param <T> bean type
 */
public class MetaBean<T> extends MetaField<T> {

	public MetaBean(Class<T> type) {
		this(null, type);
	}

	public MetaBean(MetaBean<?> parent, Class<T> type) {
		this(parent, type, null);
	}

	public MetaBean(MetaBean<?> parent, Class<T> type, String alias) {
		super(parent, type, alias);
	}

	protected <TYPE> MetaField<TYPE> createField(Class<TYPE> fieldType, String fieldName) {
		return new MetaField<>(this, fieldType, fieldName);
	}

	protected <TYPE> MetaList<TYPE> createList(String fieldName) {
		return new MetaList<>(this, fieldName);
	}

	protected <TYPE> MetaSet<TYPE> createSet(String fieldName) {
		return new MetaSet<>(this, fieldName);
	}

}
