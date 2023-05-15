package ma.emsi.JDBC.dao.impl;

import ma.emsi.JDBC.dao.VoitureDao;
import ma.emsi.classe.Voiture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoitureDaoImp implements VoitureDao {

    private Connection conn = DB.getConnection();

    @Override
    public void insert(Voiture voiture) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO voiture (matricule,marque,couleur,prix,kilometrage,vitesse) VALUES (?,?,?,?,?,?)");

            ps.setString(1, voiture.getMatricule());
            ps.setString(2, voiture.getMarque());
            ps.setString(3, voiture.getCouleur());
            ps.setDouble(4, voiture.getPrix());
            ps.setDouble(5, voiture.getKilometrage());
            ps.setDouble(6, voiture.getVitesse());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("problème d'insertion d'une voiture");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Voiture voiture) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE voiture SET marque = ? WHERE matricule = ?");

            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getMatricule());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'une voiture");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM voiture WHERE matricule = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'une voiture");
            ;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Voiture findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM voiture WHERE matricule = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Voiture voiture = new Voiture();

                voiture.setMarque(rs.getString("Marque"));

                return voiture;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver une voiture");
            ;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Voiture> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM voiture");
            rs = ps.executeQuery();

            List<Voiture> voitures = new ArrayList<>();

            while (rs.next()) {
                Voiture voiture = new Voiture();
                voiture.setmMtricule(rs.getString("matricule"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setPrix(rs.getDouble("prix"));
                voiture.setKilometrage(rs.getDouble("kilometrage"));
                voiture.setVitesse(rs.getDouble("vitesse"));

                voitures.add(voiture);
            }

            return voitures;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner une voiture");
            ;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public void importExcel(String s) {

        List<Voiture> voitureList = new ArrayList<>();
        try {
            // Load the Excel file
            FileInputStream fis = new FileInputStream(new File(s));

            // Create an instance of Workbook based on the Excel file
            Workbook workbook = new XSSFWorkbook(fis);

            // Get the first sheet in the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over each row in the sheet
            for (Row row : sheet) {
                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Extract the cell values and create a Voiture object

                String matricule = row.getCell(0).getStringCellValue();
                String marque = row.getCell(1).getStringCellValue();
                String couleur = row.getCell(2).getStringCellValue();
                double prix = Double.parseDouble(row.getCell(3).getStringCellValue());
                double kilometrage = Double.parseDouble(row.getCell(4).getStringCellValue());
                double vitesse = Double.parseDouble(row.getCell(5).getStringCellValue());
                Voiture voiture = new Voiture(matricule, marque, couleur, prix, kilometrage, vitesse);
                voitureList.add(voiture);
            }

            // Close the workbook and file stream
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Print the imported Voiture objects
        for (Voiture voiture : voitureList) {
            insert(voiture);
        }
    }
    @Override
    public void exportToExcel(String s){
        List<Voiture> lVoiture = findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voiture Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("matricule");
        headerRow.createCell(1).setCellValue("marque");
        headerRow.createCell(2).setCellValue("couleur");
        headerRow.createCell(3).setCellValue("prix");
        headerRow.createCell(4).setCellValue("kilometrage");
        headerRow.createCell(5).setCellValue("vitesse");

        // Populate data rows
        int rowNum = 1;
        for (Voiture voiture : lVoiture) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(voiture.getMarque());
            row.createCell(1).setCellValue(voiture.getMarque());
            row.createCell(2).setCellValue(voiture.getCouleur());
            row.createCell(3).setCellValue(voiture.getPrix());
            row.createCell(4).setCellValue(voiture.getKilometrage());
            row.createCell(5).setCellValue(voiture.getVitesse());
        }

        // Autosize columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook content to a file
        try (FileOutputStream fos = new FileOutputStream(s)) {
            workbook.write(fos);
            System.out.println("Data exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void importTxt(String s) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(s));

        ArrayList<Voiture> lvoiture = new ArrayList<Voiture>();
        Voiture v = null;
        String readLine = br.readLine();

        while(readLine != null){
            String [] voiture  = readLine.split("\\|");
            v = new Voiture();
            v.setmMtricule(voiture[0].trim());
            v.setMarque(voiture[1].trim());
            v.setCouleur(voiture[2].trim());
            v.setPrix(Double.parseDouble(voiture[3].trim()));
            v.setKilometrage(Double.parseDouble(voiture[3].trim()));
            v.setVitesse(Double.parseDouble(voiture[3].trim()));
            lvoiture.add(v);
            readLine = br.readLine();
        }
        for (Voiture voiture : lvoiture) {
            insert(voiture);
        }
    }


    @Override
    public void exportTxt(String s){
        try( FileOutputStream fout = new FileOutputStream(s))
        {
            List<Voiture> lVoiture = findAll();
            for(Voiture vtr : lVoiture){
                fout.write(vtr.toString().getBytes());
                fout.write('\n');
            }
        }
        catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}


