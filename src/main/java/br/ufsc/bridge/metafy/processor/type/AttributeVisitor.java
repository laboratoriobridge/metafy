package br.ufsc.bridge.metafy.processor.type;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor6;

import br.ufsc.bridge.metafy.Metafy;
import br.ufsc.bridge.metafy.processor.clazz.MetafyClassContext;

public class AttributeVisitor extends SimpleTypeVisitor6<Attribute, MetafyClassContext> {

	@Override
	public Attribute visitPrimitive(PrimitiveType t, MetafyClassContext p) {
		if (!p.getActualElement().getModifiers().contains(Modifier.STATIC)) {
			return new PrimitiveAttribute(p.getActualElement());
		}
		return super.visitPrimitive(t, p);
	}

	@Override
	public Attribute visitArray(ArrayType t, MetafyClassContext p) {
		if (!p.getActualElement().getModifiers().contains(Modifier.STATIC)) {
			return new ArrayAttribute(t, p.getActualElement());
		}
		return super.visitArray(t, p);
	}

	@Override
	public Attribute visitDeclared(DeclaredType t, MetafyClassContext p) {
		if (t.asElement().getAnnotation(Metafy.class) != null) {
			return new MetaReferenceAttribute(t, p, p.getActualElement());
		} else if (!t.asElement().getModifiers().contains(Modifier.STATIC)) {
			if(this.isList(t.toString())) {
				return new ListAttribute(t, p.getActualElement());
			} else if (this.isMap(t.toString())) {
				// not implemented
			} else if (this.isSet(t.toString())) {
				return new SetAttribute(t, p.getActualElement());
			} else {
				return new SimpleAttribute(t, p.getActualElement());
			}
		}
		return super.visitDeclared(t, p);
	}

	private boolean isList(String qualifiedName) {
		return qualifiedName.startsWith("java.util.List");
	}

	private boolean isMap(String qualifiedName) {
		return qualifiedName.startsWith("java.util.Map");
	}

	private boolean isSet(String qualifiedName) {
		return qualifiedName.startsWith("java.util.Set");
	}

	@Override
	public Attribute visitTypeVariable(TypeVariable t, MetafyClassContext p) {
		if (!t.asElement().getModifiers().contains(Modifier.STATIC)) {
			TypeMirror typeMirror = p.getTypeFor((TypeParameterElement) t.asElement());

			if (typeMirror != null) {
				return new SimpleAttribute((DeclaredType) typeMirror, p.getActualElement());
			}
		}
		return super.visitTypeVariable(t, p);
	}

}
