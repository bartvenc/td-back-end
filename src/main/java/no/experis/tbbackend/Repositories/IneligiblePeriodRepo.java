package no.experis.tbbackend.Repositories;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.Models.IneligiblePeriod;
import no.experis.tbbackend.Models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IneligiblePeriodRepo implements MainRepository<IneligiblePeriod> {

    @Override
    public void save(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(ineligiblePeriod);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<IneligiblePeriod> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from IneligiblePeriod", IneligiblePeriod.class).list();
        }
    }

    @Override
    public IneligiblePeriod findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(IneligiblePeriod.class, id);
        }
    }

    @Override
    public void update(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(ineligiblePeriod);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(ineligiblePeriod);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
