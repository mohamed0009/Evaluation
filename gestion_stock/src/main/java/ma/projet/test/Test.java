package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        Categorie cat1 = new Categorie("PC", "Ordinateurs");
        Categorie cat2 = new Categorie("PER", "Périphériques");
        categorieService.create(cat1);
        categorieService.create(cat2);

        Produit p1 = new Produit("ES12", 120);
        p1.setCategorie(cat1);
        Produit p2 = new Produit("ZR85", 100);
        p2.setCategorie(cat2);
        Produit p3 = new Produit("EE85", 200);
        p3.setCategorie(cat1);
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        Commande cmd = new Commande(new Date());
        commandeService.create(cmd);

        LigneCommandeProduit lc1 = new LigneCommandeProduit(7);
        lc1.setProduit(p1);
        lc1.setCommande(cmd);
        cmd.getLigneCommandes().add(lc1);

        LigneCommandeProduit lc2 = new LigneCommandeProduit(14);
        lc2.setProduit(p2);
        lc2.setCommande(cmd);
        cmd.getLigneCommandes().add(lc2);

        LigneCommandeProduit lc3 = new LigneCommandeProduit(5);
        lc3.setProduit(p3);
        lc3.setCommande(cmd);
        cmd.getLigneCommandes().add(lc3);

        ligneCommandeService.create(lc1);
        ligneCommandeService.create(lc2);
        ligneCommandeService.create(lc3);
        // Display products by category
        System.out.println("Produits dans la catégorie: " + cat1.getLibelle());
        List<Produit> produitsByCategory = produitService.findByCategorie(cat1);
        for (Produit p : produitsByCategory) {
            System.out.printf("%-10s %-3d DH%n", p.getReference(), (int) p.getPrix());
        }

        // Display command details and its products
        System.out.printf("Commande : %-4d Date : 14 Mars 2013%n", cmd.getId());
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");
        for (LigneCommandeProduit lc : cmd.getLigneCommandes()) {
            System.out.printf("%-10s %-3d DH  %d%n",
                    lc.getProduit().getReference(),
                    (int) lc.getProduit().getPrix(),
                    lc.getQuantite());

        }

        // Display expensive products (price > 100 DH)
        System.out.println("\nProduits avec prix > 100 DH:");
        java.util.Set<Produit> expensiveProducts = new java.util.LinkedHashSet<>(
                produitService.findByPrixSuperieur100());
        for (Produit p : expensiveProducts) {
            System.out.printf("%-10s %-3d DH%n", p.getReference(), (int) p.getPrix());
        }
    }
}