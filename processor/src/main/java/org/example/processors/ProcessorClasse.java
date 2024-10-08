package org.example.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("org.example.processors.GerarClasse")
public class ProcessorClasse extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Iniciando processamento...");
        for (Element element : roundEnv.getElementsAnnotatedWith(GerarClasse.class)) {
            GerarClasse annotation = element.getAnnotation(GerarClasse.class);
            String nomeClasse = annotation.nome();

            // Gera o código da classe
            try {
                String packageName = processingEnv.getElementUtils().getPackageOf(element).toString();
                JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + nomeClasse);
                try (Writer writer = file.openWriter()) {
                    writer.write("package " + packageName + ";\n");
                    writer.write("public class " + nomeClasse + " {\n");
                    writer.write("    // Código gerado automaticamente\n");
                    writer.write("}\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
