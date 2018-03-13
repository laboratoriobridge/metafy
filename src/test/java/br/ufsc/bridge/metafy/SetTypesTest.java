package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class SetTypesTest {

	@Test
	public void setTypes() {
		Compilation compilation = Compiler.javac()
			.withProcessors(new MetafyProcessor())
			.compile(JavaFileObjects.forSourceLines("TestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import java.util.Date;",
					"import java.util.Set;",
					"import br.ufsc.bridge.metafy.Metafy;",
					"",
					"@Metafy",
					"public class TestDto {",
					"",
					"	private Set<Long> idArray;",
					"	private Set<String> nameArray;",
					"	private Set<Date> dateArray;",
					"",
					"}"
					));

		CompilationSubject.assertThat(compilation).succeeded();
		CompilationSubject.assertThat(compilation).generatedSourceFile("br.ufsc.bridge.metafy.test.MTestDto")
			.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTestDto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.MetaBean;",
					"import br.ufsc.bridge.metafy.MetaSet;",
					"import br.ufsc.bridge.metafy.test.TestDto;",
					"import java.util.Date;",
					"import java.util.Set;",
					"import javax.annotation.Generated;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTestDto extends MetaBean<TestDto> {",
					"",
					"	public static final MTestDto meta = new MTestDto();",
					"",
					"	public final MetaSet<Long> idArray = createSet(\"idArray\");",
					"	public final MetaSet<String> nameArray = createSet(\"nameArray\");",
					"	public final MetaSet<Date> dateArray = createSet(\"dateArray\");",
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
