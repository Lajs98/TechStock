package br.com.lsantos.techstock.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class FacesUtil {

    public static void sucesso(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Sucesso",
                        mensagem
                )
        );
    }

    public static void erro(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro",
                        mensagem
                )
        );
    }
}