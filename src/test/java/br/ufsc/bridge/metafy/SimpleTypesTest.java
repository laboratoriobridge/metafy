package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class SimpleTypesTest {

	@Test
	public void simpleTypes() {
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
					"	private Long id;",
					"	private String name;",
					"	private Date date;",
					"	private boolean check;",
					"",
					"}"
					));

		CompilationSubject.assertThat(compilation).succeeded();
		CompilationSubject.assertThat(compilation).generatedSourceFile("br.ufsc.bridge.metafy.test.MTestDto")
			.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.MetaBean;",
					"import br.ufsc.bridge.metafy.MetaField;",
					"import br.ufsc.bridge.metafy.test.TestDto;",
					"import java.util.Date;",
					"import javax.annotation.Generated;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto extends MetaBean<TestDto> {",
					"",
					"	public static final MTestDto meta = new MTestDto();",
					"",
					"	public final MetaField<Long> id = createField(Long.class, \"id\");",
					"	public final MetaField<String> name = createField(String.class, \"name\");",
					"	public final MetaField<Date> date = createField(Date.class, \"date\");",
					"	public final MetaField<Boolean> check = createField(Boolean.class, \"check\");",
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
					"",
					"}"
					));
	}

}
