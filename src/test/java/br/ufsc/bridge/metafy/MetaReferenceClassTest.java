package br.ufsc.bridge.metafy;

import org.junit.Test;

import br.ufsc.bridge.metafy.processor.MetafyProcessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class MetaReferenceClassTest {

	@Test
	public void metaReferenceTypes() {
		Compilation compilation = Compiler.javac()
			.withProcessors(new MetafyProcessor())
			.compile(JavaFileObjects.forSourceLines("Test1Dto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import java.util.Date;",
					"import br.ufsc.bridge.metafy.Metafy;",
					"",
					"@Metafy",
					"public class Test1Dto {",
					"",
					"	private Test2Dto dto;",
					"",
					"}"
					),
					JavaFileObjects.forSourceLines("Test2Dto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.Metafy;",
					"",
					"@Metafy",
					"public class Test2Dto {",
					"",
					"	private Long id;",
					"	private String name;",
					"",
					"}"
					));

		CompilationSubject.assertThat(compilation).succeeded();
		CompilationSubject.assertThat(compilation).generatedSourceFile("br.ufsc.bridge.metafy.test.MTest1Dto")
			.hasSourceEquivalentTo(JavaFileObjects.forSourceLines("MTest1Dto",
					"package br.ufsc.bridge.metafy.test;",
					"",
					"import br.ufsc.bridge.metafy.MetaBean;",
					"import br.ufsc.bridge.metafy.test.MTest2Dto;",
					"import br.ufsc.bridge.metafy.test.Test1Dto;",
					"import jakarta.annotation.Generated;",
					"",
					"@Generated(\"br.ufsc.bridge.metafy.processor.MetafyProcessor\")",
					"public class MTest1Dto extends MetaBean<Test1Dto> {",
					"",
					"	public static final MTest1Dto meta = new MTest1Dto();",
					"",
					"	private MTest2Dto dto;",
					"",
					"	public MTest1Dto() {",
					"		super(Test1Dto.class);",
					"	}",
					"",
					"	public MTest1Dto(MetaBean<?> parent) {",
					"		super(parent, Test1Dto.class);",
					"	}",
					"",
					"	public MTest1Dto(MetaBean<?> parent, String alias) {",
					"		super(parent, Test1Dto.class, alias);",
					"	}",
					"",
					"	public MTest2Dto dto(){",
					"		if(dto == null){",
					"			dto = new MTest2Dto(this, \"dto\");",
					"		}",
					"		return dto;",
					"	}",
					"",
					"}"
					));
	}

}
