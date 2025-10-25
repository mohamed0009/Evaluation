package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {
    @Override
    public boolean create(Employe o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Employe o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Employe o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Employe findById(int id) {
        Employe employe = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            employe = session.get(Employe.class, id);
            tx.commit();
            return employe;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Employe> findAll() {
        List<Employe> employes = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            employes = session.createQuery("from Employe", Employe.class).list();
            tx.commit();
            return employes;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Méthodes spécifiques
    public void displayEmployeeTasks(int employeeId) {
        Employe employe = findById(employeeId);
        if (employe != null) {
            System.out.println("Tâches réalisées par " + employe.getNom() + " " + employe.getPrenom() + ":");
            System.out.println("ID  Nom            Projet          Date Début     Date Fin");
            for (EmployeTache et : employe.getEmployeTaches()) {
                Tache tache = et.getTache();
                System.out.printf("%-3d %-14s %-14s %-14s %-14s%n",
                        tache.getId(),
                        tache.getNom(),
                        tache.getProjet().getNom(),
                        et.getDateDebutReelle(),
                        et.getDateFinReelle());
            }
        }
    }

    public void displayManagedProjects(int employeeId) {
        Employe employe = findById(employeeId);
        if (employe != null) {
            System.out.println("Projets gérés par " + employe.getNom() + " " + employe.getPrenom() + ":");
            System.out.println("ID  Nom            Date Début     Date Fin");
            for (Projet projet : employe.getProjetsGeres()) {
                System.out.printf("%-3d %-14s %-14s %-14s%n",
                        projet.getId(),
                        projet.getNom(),
                        projet.getDateDebut(),
                        projet.getDateFin());
            }
        }
    }
}