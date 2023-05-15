package ma.emsi.JDBC;

import ma.emsi.JDBC.service.VoitureService;
import ma.emsi.classe.Voiture;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        VoitureService v = new VoitureService();
        v.exporterVersTxt("src/main/resources/voiture.txt");
    }
}
