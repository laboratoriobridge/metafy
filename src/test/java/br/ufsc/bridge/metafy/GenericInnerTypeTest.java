package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class GenericInnerTypeTest {

	@Test
	public void genericInnerTypes() {
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
					"	@Metafy",
					"	public static class TestGenericDto<T> {",
					"		private T value;",
					"		private Date date;",
					"	}",
					"",
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
					"import br.ufsc.bridge.metafy.test.TestDto.TestGenericDto;",
					"import jakarta.annotation.Generated;",
					"import java.util.Date;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto extends MetaBean<TestDto> {",
					"",
					"	public static final MTestDto meta = new MTestDto();",
					"",
					"	private MTestDto_TestGenericDtoLong generic;",
					"	private MTestDto_TestGenericDtoLong generic2;",
					"	private MTestDto_TestGenericDtoDate generic3;",
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
					"	public MTestDto_TestGenericDtoLong generic(){",
					"		if(generic == null){",
					"			generic = new MTestDto_TestGenericDtoLong(this, \"generic\");",
					"		}",
					"		return generic;",
					"	}",
					"",
					"	public MTestDto_TestGenericDtoLong generic2(){",
					"		if(generic2 == null){",
					"			generic2 = new MTestDto_TestGenericDtoLong(this, \"generic2\");",
					"		}",
					"		return generic2;",
					"	}",
					"",
					"	public MTestDto_TestGenericDtoDate generic3(){",
					"		if(generic3 == null){",
					"			generic3 = new MTestDto_TestGenericDtoDate(this, \"generic3\");",
					"		}",
					"		return generic3;",
					"	}",
					"",
					"	public static class MTestDto_TestGenericDtoLong extends MetaBean<TestGenericDto> {",
					"",
					"		public final MetaField<Long> value = createField(Long.class, \"value\");",
					"		public final MetaField<Date> date = createField(Date.class, \"date\");",
					"",
					"		protected MTestDto_TestGenericDtoLong(MetaBean<?> parent, String alias) {",
					"			super(parent, TestGenericDto.class, alias);",
					"		}",
					"",
					"	}",
					"",
					"	public static class MTestDto_TestGenericDtoDate extends MetaBean<TestGenericDto> {",
					"",
					"		public final MetaField<Date> value = createField(Date.class, \"value\");",
					"		public final MetaField<Date> date = createField(Date.class, \"date\");",
					"",
					"		protected MTestDto_TestGenericDtoDate(MetaBean<?> parent, String alias) {",
					"			super(parent, TestGenericDto.class, alias);",
					"		}",
					"",
					"	}",
					"}"
					));
	}

}
