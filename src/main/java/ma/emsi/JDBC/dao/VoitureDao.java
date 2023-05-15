package ma.emsi.JDBC.dao;

import ma.emsi.classe.Voiture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface VoitureDao {
    void insert(Voiture voiture);

    void update(Voiture voiture);

    void deleteById(Integer id);

    Voiture findById(Integer id);

    List<Voiture> findAll();

    void importExcel(String s);

    void exportToExcel(String s);

    void importTxt(String s) throws IOException;

    void exportTxt(String s);

}
