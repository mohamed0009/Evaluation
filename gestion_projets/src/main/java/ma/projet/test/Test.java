package ma.projet.test;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            // Create employees
            Employe chefProjet = new Employe();
            chefProjet.setNom("Dupont");
            chefProjet.setPrenom("Jean");
            chefProjet.setTelephone("0612345678");
            employeService.create(chefProjet);

            Employe employe = new Employe();
            employe.setNom("Martin");
            employe.setPrenom("Pierre");
            employe.setTelephone("0687654321");
            employeService.create(employe);

            // Create project
            Projet projet = new Projet();
            projet.setNom("Gestion de stock");
            projet.setDateDebut(dateFormat.parse("14/01/2013"));
            projet.setDateFin(dateFormat.parse("14/12/2013"));
            projet.setChefProjet(chefProjet);
            projetService.create(projet);

            // Create tasks
            Tache analyse = new Tache();
            analyse.setNom("Analyse");
            analyse.setDateDebut(dateFormat.parse("10/02/2013"));
            analyse.setDateFin(dateFormat.parse("20/02/2013"));
            analyse.setPrix(1200.0);
            analyse.setProjet(projet);
            tacheService.create(analyse);

            Tache conception = new Tache();
            conception.setNom("Conception");
            conception.setDateDebut(dateFormat.parse("10/03/2013"));
            conception.setDateFin(dateFormat.parse("15/03/2013"));
            conception.setPrix(1500.0);
            conception.setProjet(projet);
            tacheService.create(conception);

            Tache developpement = new Tache();
            developpement.setNom("Développement");
            developpement.setDateDebut(dateFormat.parse("10/04/2013"));
            developpement.setDateFin(dateFormat.parse("25/04/2013"));
            developpement.setPrix(2000.0);
            developpement.setProjet(projet);
            tacheService.create(developpement);

            // Assign tasks to employees
            EmployeTache et1 = new EmployeTache();
            et1.setEmploye(employe);
            et1.setTache(analyse);
            et1.setDateDebutReelle(dateFormat.parse("10/02/2013"));
            et1.setDateFinReelle(dateFormat.parse("20/02/2013"));
            employeTacheService.create(et1);

            EmployeTache et2 = new EmployeTache();
            et2.setEmploye(employe);
            et2.setTache(conception);
            et2.setDateDebutReelle(dateFormat.parse("10/03/2013"));
            et2.setDateFinReelle(dateFormat.parse("15/03/2013"));
            employeTacheService.create(et2);

            EmployeTache et3 = new EmployeTache();
            et3.setEmploye(employe);
            et3.setTache(developpement);
            et3.setDateDebutReelle(dateFormat.parse("10/04/2013"));
            et3.setDateFinReelle(dateFormat.parse("25/04/2013"));
            employeTacheService.create(et3);

            // Test display methods
            System.out.println("\n=== Tasks by Employee ===");
            employeService.displayEmployeeTasks(employe.getId());

            System.out.println("\n=== Projects by Manager ===");
            employeService.displayManagedProjects(chefProjet.getId());

            System.out.println("\n=== Project Tasks ===");
            projetService.displayProjectTasks(projet.getId());

            System.out.println("\n=== Tasks with price > 1000 DH ===");
            for (Tache t : tacheService.findByPrixSuperieur(1000.0)) {
                System.out.println(t.getNom() + " - " + t.getPrix() + " DH");
            }

            System.out.println("\n=== Tasks between dates ===");
            Date startDate = dateFormat.parse("01/02/2013");
            Date endDate = dateFormat.parse("30/04/2013");
            for (Tache t : tacheService.findBetweenDates(startDate, endDate)) {
                System.out.println(t.getNom() + " - " +
                        dateFormat.format(t.getDateDebut()) + " to " +
                        dateFormat.format(t.getDateFin()));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}