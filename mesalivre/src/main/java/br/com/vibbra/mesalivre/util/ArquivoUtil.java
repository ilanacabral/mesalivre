package br.com.vibbra.mesalivre.util;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoUtil {

	public static String lerArquivo(String caminho) throws Exception {
		Path path = Paths.get(caminho);
		return "";

		//return Files.lines(path, StandardCharsets.UTF_8)
		//		.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

}
