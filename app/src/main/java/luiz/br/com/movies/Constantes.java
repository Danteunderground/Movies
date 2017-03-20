package luiz.br.com.movies;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Luiz on 14/03/2017.
 */

public class Constantes {
    public static final String TAG_USUARIO = "usuario";
    public static final String TAG_MOVIE = "movie_c";
    public static final String SHARED_PREFERENCES = "movie_x";
    public static final String PREF_LOGADO = "manter_logado";

    public static String getVersao(Context contexto) {
        PackageInfo info = null;
        String retorno = "1.0.0";
        try {
            info = contexto.getPackageManager().getPackageInfo(contexto.getPackageName(), 0);
            retorno = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return retorno;
    }

}
