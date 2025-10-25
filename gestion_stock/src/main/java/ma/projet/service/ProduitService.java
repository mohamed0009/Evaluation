package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    @Override
    public boolean create(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.remove(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Produit findById(Long id) {
        Session session = null;
        Produit produit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produit = (Produit) session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produit;
    }

    @Override
    public List<Produit> findAll() {
        Session session = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery("from Produit", Produit.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produits;
    }

    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery("from Produit p where p.categorie = :categorie", Produit.class)
                    .setParameter("categorie", categorie)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produits;
    }

    public List<Produit> findByDateCommande(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Produit> produits = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery(
                    "select distinct p from Produit p join p.ligneCommandes lc where lc.commande.date between :dateDebut and :dateFin",
                    Produit.class)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produits;
    }

    public List<Produit> findByCommande(Commande commande) {
        Session session = null;
        List<Produit> produits = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery(
                    "select distinct p from Produit p join p.ligneCommandes lc where lc.commande = :commande",
                    Produit.class)
                    .setParameter("commande", commande)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produits;
    }

    public List<Produit> findByPrixSuperieur100() {
        Session session = null;
        List<Produit> produits = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createNamedQuery("Produit.findByPriceGreaterThan", Produit.class)
                    .setParameter("prix", 100)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return produits;
    }
}