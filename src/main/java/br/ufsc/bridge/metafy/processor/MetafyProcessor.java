package br.ufsc.bridge.metafy.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import br.ufsc.bridge.metafy.Metafy;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassFactory;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassSerializer;
import br.ufsc.bridge.metafy.processor.exception.UnexpectedException;

@SupportedAnnotationTypes("br.ufsc.bridge.metafy.Metafy")
public class MetafyProcessor extends AbstractProcessor {

	public MetafyProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver() || annotations.isEmpty()) {
			return false;
		}

		for (Element elem : roundEnv.getElementsAnnotatedWith(Metafy.class)) {
			if (elem.getKind() == ElementKind.CLASS && ((TypeElement) elem).getTypeParameters().isEmpty()) {
				try {
					MetafyClass data = MetafyClassFactory.create((TypeElement) elem);

					this.writeClass(data);
				} catch (Exception e) {
					this.processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
					throw new UnexpectedException(e);
				}
			}
		}

		return false;
	}

	private void writeClass(MetafyClass data) throws IOException {
		JavaFileObject javaFile = this.processingEnv.getFiler().createSourceFile(data.getCompleteName());
		OutputStream os = javaFile.openOutputStream();
		PrintWriter pw = new PrintWriter(os);
		new MetafyClassSerializer().serialize(this.processingEnv, data, pw);
	}

}