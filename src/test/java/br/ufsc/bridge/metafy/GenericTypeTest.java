package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class GenericTypeTest {

	@Test
	public void genericInerTypes() {
		Compilation compilation = Compiler.javac()
			.withProcessors(new MetafyProcessor())
			.compile(JavaFileObjects.forSourceLines("TestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.Metafy;",
					"import java.util.Date;",
					"",
					"@Metafy",
					"public class TestDto {",
					"",
					"	private TestGenericDto<Long> generic;",
					"	private TestGenericDto<Long> generic2;",
					"	private TestGenericDto<Date> generic3;",
					"",
					"}"
					),
					JavaFileObjects.forSourceLines("TestGenericDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.Metafy;",
					"import java.util.Date;",
					"",
					"@Metafy",
					"public class TestGenericDto<T> {",
					"	private T value;",
					"	private Date date;",
					"}"
					));

		CompilationSubject.assertThat(compilation).succeeded();
		CompilationSubject.assertThat(compilation)
			.generatedSourceFile("br.ufsc.bridge.metafy.test.MTestDto")
			.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.MetaBean;",
					"import br.ufsc.bridge.metafy.MetaField;",
					"import br.ufsc.bridge.metafy.test.TestDto;",
					"import br.ufsc.bridge.metafy.test.TestGenericDto;",
					"import java.util.Date;",
					"import javax.annotation.Generated;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto extends MetaBean<TestDto> {",
					"",
					"	public static final MTestDto meta = new MTestDto();",
					"",
					"	private MTestGenericDtoLong generic;",
					"	private MTestGenericDtoLong generic2;",
					"	private MTestGenericDtoDate generic3;",
					"",
					"",
					"	public MTestDto() {",
					"		super(TestDto.class);",
					"	}",
					"",
					"	public MTestDto(MetaBean<?> parent) {",
					"		super(parent, TestDto.class);",
					"	}",
					"",
					"	public MTestDto(MetaBean<?> parent, String alias) {",
					"		super(parent, TestDto.class, alias);",
					"	}",
					"",
					"	public MTestGenericDtoLong generic(){",
					"		if(generic == null){",
					"			generic = new MTestGenericDtoLong(this, \"generic\");",
					"		}",
					"		return generic;",
					"	}",
					"",
					"	public MTestGenericDtoLong generic2(){",
					"		if(generic2 == null){",
					"			generic2 = new MTestGenericDtoLong(this, \"generic2\");",
					"		}",
					"		return generic2;",
					"	}",
					"",
					"	public MTestGenericDtoDate generic3(){",
					"		if(generic3 == null){",
					"			generic3 = new MTestGenericDtoDate(this, \"generic3\");",
					"		}",
					"		return generic3;",
					"	}",
					"",
					"	public static class MTestGenericDtoLong extends MetaBean<TestGenericDto> {",
					"",
					"		public final MetaField<Long> value = createField(Long.class, \"value\");",
					"		public final MetaField<Date> date = createField(Date.class, \"date\");",
					"",
					"		protected MTestGenericDtoLong(MetaBean<?> parent, String alias) {",
					"			super(parent, TestGenericDto.class, alias);",
					"		}",
					"",
					"	}",
					"",
					"	public static class MTestGenericDtoDate extends MetaBean<TestGenericDto> {",
					"",
					"		public final MetaField<Date> value = createField(Date.class, \"value\");",
					"		public final MetaField<Date> date = createField(Date.class, \"date\");",
					"",
					"		protected MTestGenericDtoDate(MetaBean<?> parent, String alias) {",
					"			super(parent, TestGenericDto.class, alias);",
					"		}",
					"",
					"	}",
					"}"
					));
	}

}
