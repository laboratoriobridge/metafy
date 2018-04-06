[![Build Status](https://travis-ci.org/laboratoriobridge/metafy.svg?branch=master)](https://travis-ci.org/laboratoriobridge/metafy)
[![codecov](https://codecov.io/gh/laboratoriobridge/metafy/branch/master/graph/badge.svg)](https://codecov.io/gh/laboratoriobridge/metafy)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.ufsc.bridge/metafy/badge.svg)](https://maven-badges.herokuapp.com/maven-central/br.ufsc.bridge/metafy)


# Metafy

Generate meta information for Java objects.

## Usage

Mark your class with `@Metafy`:

```java
import br.ufsc.bridge.metafy.Metafy;

@Metafy
class AuthorDto {
	private Long id;
	private String name;
}
```

This will generate:

```java
import br.ufsc.bridge.metafy.MetaBean;
import br.ufsc.bridge.metafy.MetaField;

public class MAuthorDto extends MetaBean<AuthorDto> {

	public static final MAuthorDto meta = new MAuthorDto();

	public final MetaField<Long> id = createField(Long.class, "id");
	public final MetaField<String> name = createField(String.class, "name");


	public MAuthorDto() {
		super(AuthorDto.class);
	}

	public MAuthorDto(MetaBean<?> parent) {
		super(parent, AuthorDto.class);
	}

	public MAuthorDto(MetaBean<?> parent, String alias) {
		super(parent, AuthorDto.class, alias);
	}

}
```

To be used as:


```java
MAuthorDto meta = MAuthorDto.meta;

meta.id; // meta information about id attribute
meta.name; // meta information about name attribute
```
