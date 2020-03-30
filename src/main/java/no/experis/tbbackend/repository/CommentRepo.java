package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.Comment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The type Comment repo.
 */
public class CommentRepo implements MainRepository<Comment> {

    @Override
    public void save(Comment comment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(comment);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Comment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Comment", Comment.class).list();
        }
    }

    @Override
    public Comment findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public void update(Comment comment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Comment comment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Delete comment.
     *
     * @param r_id the vacation request id
     * @param c_id the comment id
     * @param u_id the user id
     */
    public void deleteComment(long r_id, long c_id, long u_id) {
        System.out.println("COMMENTID " + c_id);
        System.out.println("USERID " + u_id);
        System.out.println("REQUESTID " + r_id);
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            System.out.println("------------------------------------------------------------");
            System.out.println("DELETE FROM comment_user where "+c_id+" and user_d = "+u_id);
            System.out.println("DELETE FROM comments where comment_id ="+c_id);
            System.out.println("------------------------------------------------------------");
            session.createSQLQuery("DELETE FROM comment_user where comment_id = ?1 AND user_id = ?2").setParameter(1, c_id).setParameter(2, u_id).executeUpdate();
            session.createSQLQuery("DELETE FROM comments where comment_id = ?1").setParameter(1, c_id).executeUpdate();
            //session.createSQLQuery("DELETE FROM request_user where request_id = ?1 and user_id = ?2").setParameter(1, r_id).setParameter(2, c_id).executeUpdate();
            //session.createSQLQuery("DELETE FROM vacation_requests where request_id = ?1").setParameter(1, r_id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
