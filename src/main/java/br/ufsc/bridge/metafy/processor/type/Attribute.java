package br.ufsc.bridge.metafy.processor.type;

import java.io.PrintWriter;

import javax.annotation.processing.ProcessingEnvironment;

import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;

public interface Attribute {

	void initialize(ProcessingEnvironment processingEnv);

	void importTypes(MetafyClass metaClass);

	void writeAttribute(PrintWriter pw);

	void writeConstantMethod(PrintWriter pw);
}
