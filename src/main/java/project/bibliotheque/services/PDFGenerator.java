package project.bibliotheque.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import project.bibliotheque.models.*;


public class PDFGenerator {
  public static String main(Membre membre, Livre book, LocalDateTime date) {
    try {
      // Création du document PDF
      Document document = new Document(PageSize.A4);
      PdfWriter.getInstance(document, new FileOutputStream("recu - "+date.format(DateTimeFormatter.ISO_DATE_TIME)+".pdf"));
      document.open();

      // Police et style
      Font fontTitre = new Font(Font.HELVETICA, 18, Font.BOLD);
      Font fontSousTitre = new Font(Font.HELVETICA, 14, Font.BOLD);
      Font fontNormal = new Font(Font.HELVETICA, 12);
      Font fontGras = new Font(Font.HELVETICA, 12, Font.BOLD);

      // Titre principal
      Paragraph titre = new Paragraph("REÇU DE PRÊT DE LIVRE", fontTitre);
      titre.setAlignment(Element.ALIGN_CENTER);
      document.add(titre);

      // Sous-titre Bibliothèque
      Paragraph bibliotheque = new Paragraph("Bibliothèque", fontSousTitre);
      bibliotheque.setAlignment(Element.ALIGN_CENTER);
      document.add(bibliotheque);

      // Ligne séparatrice
      document.add(new Paragraph(" "));
      document.add(new Chunk(new LineSeparator()));
      document.add(new Paragraph(" "));

      // Section Informations de l'emprunteur
      Paragraph infoEmprunteur = new Paragraph("Informations de l'emprunteur :", fontGras);
      document.add(infoEmprunteur);

      // Exemple de données (à remplacer par vos variables)
      String nom = membre.getNom();
      String sexe = membre.getSexe() == Gender.man? "Masculin": "Feminin";
      int age = membre.getAge();
      String email = membre.getContact();

      document.add(new Paragraph("Nom : " + nom, fontNormal));
      document.add(new Paragraph("Sexe : " + sexe + "    Âge : " + age + " ans", fontNormal));
      document.add(new Paragraph("Email : " + email, fontNormal));

      document.add(new Paragraph(" "));

      // Section Informations sur l'emprunt
      Paragraph infoEmprunt = new Paragraph("Informations sur l'emprunt :", fontGras);
      document.add(infoEmprunt);

      LocalDate dateEmprunt = date.toLocalDate();
      LocalDate dateRetour = dateEmprunt.plusDays(30);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      document.add(new Paragraph("Date d'emprunt : " + dateEmprunt.format(formatter), fontNormal));
      document.add(new Paragraph("Date de retour prévue : " + dateRetour.format(formatter), fontNormal));

      document.add(new Paragraph(" "));

      // Section Livre emprunté
      Paragraph livreEmprunte = new Paragraph("Livre emprunté :", fontGras);
      document.add(livreEmprunte);

      String titreLivre = book.getTitre();
      String auteur = book.getAuteur();

      document.add(new Paragraph("Titre : " + titreLivre, fontNormal));
      document.add(new Paragraph("Auteur : " + auteur, fontNormal));

      document.add(new Paragraph(" "));

      // Section Conditions de prêt
      Paragraph conditions = new Paragraph("Conditions de prêt :", fontGras);
      document.add(conditions);

      List listeConditions = new List(List.UNORDERED);
      listeConditions.add(new ListItem("Durée maximale du prêt : 30 jours", fontNormal));
      listeConditions.add(new ListItem("Le livre doit être rendu en bon état", fontNormal));
      document.add(listeConditions);

      document.add(new Paragraph(" "));

      // Pied de page
      document.add(new Paragraph(" "));
      document.add(new Chunk(new LineSeparator()));
      Paragraph fin = new Paragraph("Merci pour votre visite ! Bonne lecture !", fontSousTitre);
      fin.setAlignment(Element.ALIGN_CENTER);
      document.add(fin);

      document.close();
      System.out.println("PDF généré avec succès !");

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "recu - "+date.format(DateTimeFormatter.ISO_DATE_TIME)+".pdf";
  }
}
