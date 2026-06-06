package br.com.lsantos.techstock.repository;

import br.com.lsantos.techstock.config.JPAUtil;
import br.com.lsantos.techstock.entity.Equipamento;
import br.com.lsantos.techstock.enums.StatusEquipamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class EquipamentoRepository {

    public void salvar(Equipamento equipamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(equipamento);
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

    public Equipamento buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Equipamento.class, id);
        } finally {
            em.close();
        }
    }

    public Equipamento buscarPorPatrimonio(String patrimonio) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT e FROM Equipamento e WHERE e.patrimonio = :patrimonio",
                            Equipamento.class)
                    .setParameter("patrimonio", patrimonio)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Equipamento buscarPorSerial(String serial) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT e FROM Equipamento e WHERE e.serial = :serial",
                            Equipamento.class)
                    .setParameter("serial", serial)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Equipamento> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT e FROM Equipamento e ORDER BY e.patrimonio",
                            Equipamento.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Equipamento equipamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(equipamento);
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

    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            Equipamento equipamento = em.find(Equipamento.class, id);

            em.getTransaction().begin();

            if (equipamento != null) {
                em.remove(equipamento);
            }

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

    public Long contarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("SELECT COUNT(e) FROM Equipamento e", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Long contarPorStatus(StatusEquipamento status) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT COUNT(e) FROM Equipamento e WHERE e.status = :status",
                            Long.class)
                    .setParameter("status", status)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}