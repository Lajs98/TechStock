package br.com.lsantos.techstock.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String criptografar(String senha) {
        return BCrypt.withDefaults()
                .hashToString(12, senha.toCharArray());
    }

    public static boolean verificar(String senhaDigitada, String senhaCriptografada) {
        BCrypt.Result resultado = BCrypt.verifyer()
                .verify(senhaDigitada.toCharArray(), senhaCriptografada);

        return resultado.verified;
    }
}