package lps.com.br.find_it;

import android.location.Location;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Match {

    public static Item melhorResultado;

    public static boolean match(Item item) throws InterruptedException, ExecutionException {
        melhorResultado = localizarPossivelItem(recuperarItens(item), item);

        if(melhorResultado == null)
            return false;
        else
            return true;
    }

    public static ArrayList<Item> recuperarItens(Item item) throws InterruptedException, ExecutionException {
        NoName task = new NoName(item.getCategoria(), item.getNomeItem(), item.getStatus(), item.getCodigoUsuario());
        ArrayList<Item> list = task.execute((Void) null).get();
        return list;
    }

    public static Item localizarPossivelItem(ArrayList<Item> list, Item item){

        double menor_distancia = Integer.MAX_VALUE;
        Item resp = null;

        Location loc1 = new Location("Item 1");
        loc1.setLatitude(item.getLatitude());
        loc1.setLongitude(item.getLongitude());

        for(Item x : list) {
            Location loc2 = new Location("Item 2");
            loc2.setLatitude(x.getLatitude());
            loc2.setLongitude(x.getLongitude());

            double distancia = loc1.distanceTo(loc2) ;

            if(distancia < menor_distancia){
                menor_distancia = distancia;
                resp = x;
            }
        }
        return resp;
    }
}
