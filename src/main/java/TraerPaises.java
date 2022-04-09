
 import com.fasterxml.jackson.databind.ObjectMapper;
 import countryApi.Country;
 import models.Pais;
 import org.hibernate.NonUniqueObjectException;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.boot.Metadata;
 import org.hibernate.boot.MetadataSources;
 import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

 import java.io.BufferedReader;
 import java.io.Console;
 import java.io.InputStreamReader;
 import java.lang.reflect.Array;
 import java.net.ConnectException;
 import java.net.HttpURLConnection;
 import java.net.URL;


 public class TraerPaises {
    public static void main(String[] args) {
        SessionFactory sessionFactory;
        Configuration configuration = new Configuration();
        configuration.configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure( "hibernate.cfg.xml" )
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry)
                .buildMetadata();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Country country;
        String data;
        session.beginTransaction();
        try {
            for (int code = 0; code < 300; code++) {
                try {
                    data = peticionHttpGet("https://restcountries.com/v2/callingcode/" + code);
                    country = new ObjectMapper().readValue(data, Country[].class)[0];
                    Pais pais = CountryToPais(country);
                    pais.setCodigo(Integer.toUnsignedLong(code));
                    if(session.find(Pais.class, Integer.toUnsignedLong(code)) != null)
                        session.update(pais);
                    else
                        session.save(pais);
                } catch(Exception e) {
                    if (!(e instanceof ConnectException)) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
            session.getTransaction().commit();
        }catch (Exception e){
            if(session.getTransaction() != null)
                session.getTransaction().rollback();
        }
    }

     private static Pais CountryToPais(Country country) {
         Pais pais = new Pais();
         pais.setCodigo(Long.parseLong(country.getCallingCodes()[0]));
         pais.setNombre(country.getName());
         pais.setCapital(country.getCapital() == null? "":country.getCapital());
         pais.setRegion(country.getRegion());
         pais.setPoblacion(country.getPopulation());
         pais.setLatitud(country.getLatlng()[0]);
         pais.setLongitud(country.getLatlng()[1]);
         return pais;
     }

     public static String peticionHttpGet(String urlParaVisitar) throws Exception {
         // Esto es lo que vamos a devolver
         StringBuilder resultado = new StringBuilder();
         // Crear un objeto de tipo URL
         URL url = new URL(urlParaVisitar);

         // Abrir la conexión e indicar que será de tipo GET
         HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
         conexion.setRequestMethod("GET");
         // Búferes para leer
         if(conexion.getResponseCode()==404)
             throw new ConnectException("Not found");
         BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
         String linea;
         // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
         while ((linea = rd.readLine()) != null) {
             resultado.append(linea);
         }
         // Cerrar el BufferedReader
         rd.close();
         // Regresar resultado, pero como cadena, no como StringBuilder
         return resultado.toString();
     }
}
