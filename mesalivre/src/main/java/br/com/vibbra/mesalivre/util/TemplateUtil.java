package br.com.vibbra.mesalivre.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class TemplateUtil {


	@Autowired
	private Configuration freemarkerConfiguration;
	

	public String transformaTemplateString(String template, Map<String, Object> mapaPropriedades) throws Exception {
		String retorno = null;
		try {
			Template freeMarkerTemplate = new Template("template", template,
					freemarkerConfiguration);
			Writer out = new StringWriter();
			freeMarkerTemplate.process(mapaPropriedades, out);
			retorno  = out.toString();
			out.close();
			return retorno;
		} catch (IOException e) {
			throw new Exception("Erro ao transformar template String", e);
		} catch (TemplateException e) {
			throw new Exception("Erro ao transformar template String", e);		
		}
	}
	
	
}
