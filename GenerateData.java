import java.util.ArrayList;
import java.util.Random;

public class GenerateData {

    private static final String[] NOMBRES = {
            "Minecraft", "Roblox", "CounterStrike", "Valorant", "LeagueOfLegends",
            "Fortnite", "Balatro", "ApexLegends", "GTA5", "AmongUs", "Hades", "EldenRing"
    };

    private static final String[] CATEGORIAS = {
            "Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación", "Shooter", "Battle Royale"
    };

    public static ArrayList<Game> generarJuegos(int cantidad) {
        ArrayList<Game> juegos = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < cantidad; i++) {
            String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
            String categoria = CATEGORIAS[rand.nextInt(CATEGORIAS.length)];
            int precio = rand.nextInt(70001);
            int calidad = rand.nextInt(101);

            juegos.add(new Game(nombre, categoria, precio, calidad));
        }

        return juegos;
    }


    public static void main(String[] args) {
        ArrayList<Game> lista = generarJuegos(10);
        for (Game g : lista) {
            System.out.println(g);
        }
    }
}
