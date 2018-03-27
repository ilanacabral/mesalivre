package br.com.vibbra.mesalivre.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class ValidacaoSenhaUtil {

	private static final int TAMANHO_MINIMO = 6;
	private static final int TAMANHO_MAXIMO = 15;
	private static final int QUANTIDADE_DIGITOS = 1;
	private static final int QUANTIDADE_LETRAS_MAIUSCULAS = 1;
	private static final int QUANTIDADE_CARACTER_ESPECIAL = 1;

	public static List<String> verificarRegrasSenha(String senha) throws Exception {
		if (senha == null || senha.isEmpty()) {
			throw new Exception("Senha deve ser informada");
		}

		// regra de tamanho mínimo/máximo
		LengthRule tamanho = new LengthRule(TAMANHO_MINIMO, TAMANHO_MAXIMO);

		// regra de espaços não permitidos
		WhitespaceRule espaco = new WhitespaceRule();

		// regra de caracter maiúsculo obrigatório
		CharacterRule maiusculo = new CharacterRule(EnglishCharacterData.UpperCase, QUANTIDADE_LETRAS_MAIUSCULAS);

		// regra de dígitos obrigatórios
		CharacterRule digito = new CharacterRule(EnglishCharacterData.Digit, QUANTIDADE_DIGITOS);

		// regra de caracteres especiais obrigatórios
		CharacterRule caracterEspecial = new CharacterRule(EnglishCharacterData.Special, QUANTIDADE_CARACTER_ESPECIAL);

		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(tamanho);
		ruleList.add(espaco);
		ruleList.add(digito);
		ruleList.add(caracterEspecial);
		ruleList.add(maiusculo);

		Properties props = new Properties();
		props.load(new FileInputStream("./src/main/resources/mensagens.properties"));
		MessageResolver resolver = new PropertiesMessageResolver(props);

		PasswordValidator validator = new PasswordValidator(resolver, ruleList);
		PasswordData passwordData = new PasswordData(senha);

		RuleResult result = validator.validate(passwordData);
		if (!result.isValid()) {
			return validator.getMessages(result);
		}

		return null;
	}
}
