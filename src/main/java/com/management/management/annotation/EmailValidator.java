package com.management.management.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("EmailValidate")
public class EmailValidator extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {



        for (Element element : roundEnv.getElementsAnnotatedWith(EmailValidate.class)) {

            if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;


                if (!variableElement.asType().toString().equals("java.lang.String")) {
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "Fields annotated with @EmailValidate must be of type String",
                            element
                    );
                }
            }
        }
        return true;
    }

}

