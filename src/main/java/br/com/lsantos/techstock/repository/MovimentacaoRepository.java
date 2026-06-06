package br.com.lsantos.techstock.repository;

import br.com.lsantos.techstock.config.JPAUtil;
import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.entity.Movimentacao;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MovimentacaoRepository {

    public void salvar(Movimentacao movimentacao) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(movimentacao);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Movimentacao buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Movimentacao.class, id);
        } finally {
            em.close();
        }
    }

    public List<Movimentacao> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movimentacao m ORDER BY m.dataHora DESC", Movimentacao.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Movimentacao> listarPorEquipamento(Equipamento equipamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Movimentacao m WHERE m.equipamento = :equipamento ORDER BY m.dataHora DESC",
                            Movimentacao.class)
                    .setParameter("equipamento", equipamento)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Long contarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("SELECT COUNT(m) FROM Movimentacao m", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}