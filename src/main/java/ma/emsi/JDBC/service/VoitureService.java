package ma.emsi.JDBC.service;
import ma.emsi.JDBC.dao.VoitureDao;
import ma.emsi.JDBC.dao.impl.VoitureDaoImp;
import ma.emsi.classe.Voiture;

import java.io.IOException;
import java.util.List;

public class VoitureService {
    private VoitureDao voitureDao = new VoitureDaoImp();

    public List<Voiture> findAll() { return voitureDao.findAll(); }

    public void save(Voiture voiture) {
        voitureDao.insert(voiture);
    }

    public void update(Voiture voiture) {
        voitureDao.update(voiture);
    }

    public void remove(Voiture voiture) {
        voitureDao.deleteById(Integer.valueOf(voiture.getMarque()));
    }

    public void importerDepuisExcel(String path){
        voitureDao.importExcel(path);
    }
    public void exporterVersExcel(String path){
        voitureDao.exportToExcel(path);
    }

    public void importerDepuisTxt(String path) throws IOException {
        voitureDao.importTxt(path);
    }
    public void exporterVersTxt(String path){
        voitureDao.exportTxt(path);
    }
}


