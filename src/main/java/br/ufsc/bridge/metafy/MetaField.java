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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.alias == null ? 0 : this.alias.hashCode());
		result = prime * result + (this.parent == null ? 0 : this.parent.hashCode());
		result = prime * result + (this.type == null ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		MetaField other = (MetaField) obj;
		if (this.alias == null) {
			if (other.alias != null) {
				return false;
			}
		} else if (!this.alias.equals(other.alias)) {
			return false;
		}
		if (this.parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!this.parent.equals(other.parent)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

	public MetaField<?> getParent() {
		return this.parent;
	}

}