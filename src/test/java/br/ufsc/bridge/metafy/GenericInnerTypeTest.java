package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class GenericInnerTypeTest {

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
					"import java.util.Date;",
					"import javax.annotation.Generated;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto extends MetaBean<TestDto> {",
					"",
					"	public static final MTestDto meta = new MTestDto();",
					"",
					"	private MTestDto_TestGenericDto generic;",
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
					"	public MTestDto_TestGenericDto generic(){",
					"		if(generic == null){",
					"			generic = new MTestDto_TestGenericDto(this, \"generic\");",
					"		}",
					"		return generic;",
					"	}",
					"",
					"	private static class MTestDto_TestGenericDto extends MetaBean<TestGenericDto> {",
					"",
					"		public static final MTestDto_TestGenericDto meta = new MTestDto_TestGenericDto();",
					"",
					"		public final MetaField<Long> value = createField(Long.class, \"value\");",
					"		public final MetaField<Date> date = createField(Date.class, \"date\");",
					"",
					"		public MTestDto_TestGenericDto() {",
					"			super(TestGenericDto.class);",
					"		}",
					"",
					"		public MTestDto_TestGenericDto(MetaBean<?> parent) {",
					"			super(parent, TestGenericDto.class);",
					"		}",
					"",
					"		public MTestDto_TestGenericDto(MetaBean<?> parent, String alias) {",
					"			super(parent, TestGenericDto.class, alias);",
					"		}",
					"",
					"	}",
					"}"
					));
	}

}
