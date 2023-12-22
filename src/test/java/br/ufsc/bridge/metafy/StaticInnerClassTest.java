package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class StaticInnerClassTest {

	@Test
	public void staticInnerTypes() {
		Compilation compilation = Compiler.javac()
			.withProcessors(new MetafyProcessor())
			.compile(JavaFileObjects.forSourceLines("TestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import java.util.Date;",
					"import br.ufsc.bridge.metafy.Metafy;",
					"",
					"@Metafy",
					"public class TestDto {",
					"",
					"	private TestInnerDto dto;",
					"",
					"	@Metafy",
					"	public static class TestInnerDto {",
					"		private Long id;",
					"		private String name;",
					"		private Date date;",
					"		private Boolean check;",
					"	}",
					"",
					"}"
					));

		CompilationSubject.assertThat(compilation).succeeded();
		CompilationSubject.assertThat(compilation).generatedSourceFile("br.ufsc.bridge.metafy.test.MTestDto")
		.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTestDto",
				"package br.ufsc.bridge.metafy.test;",
				"",
				"import br.ufsc.bridge.metafy.MetaBean;",
				"import br.ufsc.bridge.metafy.test.MTestDto_TestInnerDto;",
				"import br.ufsc.bridge.metafy.test.TestDto;",
				"import jakarta.annotation.Generated;",
				"",
				"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
				"public class MTestDto extends MetaBean<TestDto> {",
				"",
				"	public static final MTestDto meta = new MTestDto();",
				"",
				"	private MTestDto_TestInnerDto dto;",
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
				"	public MTestDto_TestInnerDto dto() {",
				"		if(dto == null){",
				"			dto = new MTestDto_TestInnerDto(this, \"dto\");",
				"		}",
				"		return dto;",
				"	}",
				"",
				"}"
				));
		CompilationSubject.assertThat(compilation).generatedSourceFile("br.ufsc.bridge.metafy.test.MTestDto_TestInnerDto")
			.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTestDto_TestInnerDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.MetaBean;",
					"import br.ufsc.bridge.metafy.MetaField;",
					"import br.ufsc.bridge.metafy.test.TestDto.TestInnerDto;",
					"import jakarta.annotation.Generated;",
					"import java.util.Date;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto_TestInnerDto extends MetaBean<TestInnerDto> {",
					"",
					"	public static final MTestDto_TestInnerDto meta = new MTestDto_TestInnerDto();",
					"",
					"	public final MetaField<Long> id = createField(Long.class, \"id\");",
					"	public final MetaField<String> name = createField(String.class, \"name\");",
					"	public final MetaField<Date> date = createField(Date.class, \"date\");",
					"	public final MetaField<Boolean> check = createField(Boolean.class, \"check\");",
					"",
					"",
					"	public MTestDto_TestInnerDto() {",
					"		super(TestInnerDto.class);",
					"	}",
					"",
					"	public MTestDto_TestInnerDto(MetaBean<?> parent) {",
					"		super(parent, TestInnerDto.class);",
					"	}",
					"",
					"	public MTestDto_TestInnerDto(MetaBean<?> parent, String alias) {",
					"		super(parent, TestInnerDto.class, alias);",
					"	}",
					"",
					"",
					"}"
					));
	}

}
