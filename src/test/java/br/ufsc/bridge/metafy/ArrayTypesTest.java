package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class ArrayTypesTest {

	@Test
	public void arrayTypes() {
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
					"	private long[] idArray;",
					"	private String[] nameArray;",
					"	private Date[] dateArray;",
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
					"	public final MetaField<long[]> idArray = createField(long[].class, \"idArray\");",
					"	public final MetaField<String[]> nameArray = createField(String[].class, \"nameArray\");",
					"	public final MetaField<Date[]> dateArray = createField(Date[].class, \"dateArray\");",
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
