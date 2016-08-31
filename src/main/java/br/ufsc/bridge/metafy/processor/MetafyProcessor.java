package br.ufsc.bridge.metafy.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import br.ufsc.bridge.metafy.FakeTypeElement;
import br.ufsc.bridge.metafy.MetaBean;
import br.ufsc.bridge.metafy.Metafy;

@SupportedAnnotationTypes("br.ufsc.bridge.metafy.Metafy")
public class MetafyProcessor extends AbstractProcessor {

	public MetafyProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver() || annotations.size() == 0) {
			return false;
		}

		for (Element elem : roundEnv.getElementsAnnotatedWith(Metafy.class)) {
			if (elem.getKind() == ElementKind.CLASS) {
				TypeElement typeElement = (TypeElement) elem;

				try {
					MetafyClass data;

					if (elem.getModifiers().contains(Modifier.STATIC)) {
						data = new MetafyStaticClass(typeElement.getQualifiedName().toString());
					} else {
						data = new MetafyClass(typeElement.getQualifiedName().toString());
					}

					data.importType(MetaBean.class.getName());
					data.importType(typeElement.getQualifiedName().toString());
					for (VariableElement e : ElementFilter.fieldsIn(typeElement.getEnclosedElements())) {
						if (!e.getModifiers().contains(Modifier.STATIC)) {
							data.addFakeType(new FakeTypeElement(this.processingEnv, e));
						}
					}

					new MetafyClassSerializer().serialize(this.processingEnv, data);

				} catch (Exception e) {
					this.processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
					e.printStackTrace();
				}
			}
		}

		return false;
	}

}