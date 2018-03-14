package br.ufsc.bridge.metafy.processor.type;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

import br.ufsc.bridge.metafy.processor.clazz.MetafyClass;

public class PrimitiveAttribute implements Attribute {

	/** A map from primitive types to their corresponding wrapper types. */
	private static final Map<String, String> PRIMITIVE_TO_WRAPPER_TYPE;

	static {
		Map<String, String> primToWrap = new HashMap<>(9);

		primToWrap.put("boolean", "Boolean");
	    primToWrap.put("byte", "Byte");
	    primToWrap.put("char", "Character");
	    primToWrap.put("double", "Double");
	    primToWrap.put("float", "Float");
	    primToWrap.put("int", "Integer");
	    primToWrap.put("long", "Long");
	    primToWrap.put("short", "Short");
	    primToWrap.put("void", "Void");

	    PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(primToWrap);
	}

	private String attributeName;
	private String typeName;
	private VariableElement element;


	public PrimitiveAttribute(VariableElement element) {
		super();
		this.element = element;
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnv) {
		this.attributeName = this.element.getSimpleName().toString();
		this.typeName = PRIMITIVE_TO_WRAPPER_TYPE.get(this.element.asType().toString());
	}

	@Override
	public void importTypes(MetafyClass metaClass) {
		// nothing to import
	}


	@Override
	public void writeAttribute(PrintWriter pw) {
		pw.println(String.format("\tpublic final MetaField<%s> %s = createField(%s.class, \"%s\");", this.typeName, this.attributeName, this.typeName, this.attributeName));
	}

	@Override
	public void writeConstantMethod(PrintWriter pw) {
		// not used
	}
}
