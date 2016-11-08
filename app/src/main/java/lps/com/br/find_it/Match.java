package lps.com.br.find_it;

import android.annotation.SuppressLint;
import android.location.Location;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Match {

    @SuppressLint("StaticFieldLeak")
    static Item melhorResultado;

    static boolean match(Item item) throws InterruptedException, ExecutionException {
        melhorResultado = localizarPossivelItem(recuperarItens(item), item);
        return melhorResultado != null;
    }

    private static ArrayList<Item> recuperarItens(Item item) throws InterruptedException, ExecutionException {
        PossibleMatchListItem task = new PossibleMatchListItem(item.getCategoria(), item.getNomeItem(), item.getStatus(), item.getCodigoUsuario());
        return task.execute((Void) null).get();
    }

    private static Item localizarPossivelItem(ArrayList<Item> list, Item item){

        double menor_distancia = item.getRaio();
        Item resp = null;

        Location loc1 = new Location("Item 1");
        loc1.setLatitude(item.getLatitude());
        loc1.setLongitude(item.getLongitude());

        for(Item x : list) {
            Location loc2 = new Location("Item 2");
            loc2.setLatitude(x.getLatitude());
            loc2.setLongitude(x.getLongitude());

            double distancia = loc1.distanceTo(loc2) ;

            if(distancia <= menor_distancia){
                menor_distancia = distancia;
                resp = x;
            }
        }
        return resp;
    }
}
