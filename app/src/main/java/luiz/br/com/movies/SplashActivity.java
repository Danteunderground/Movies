package luiz.br.com.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private ImageView logo;
    private final Context context = this;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferencias = new Preferencias(this);
        iniciaAnimacao();
        iniciaBD();
        buscaUsuario();
    }
    private void iniciaAnimacao(){
        logo = (ImageView) findViewById(R.id.logo_splash);

        Animation animacao = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        logo.startAnimation(animacao);
    }

    private void iniciaBD(){
        helper = new DatabaseHelper(context);
    }

    private void buscaUsuario(){
        UsuarioInterface api = UsuarioInterface.retrofit.create(UsuarioInterface.class);
        Call<Usuario> chamada = api.getUsuario();

        chamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if( preferencias.estaLogado() ){
                    Intent dashboard = new Intent(context, DashboardActivity.class);
                    startActivity(dashboard);
                    finish();
                }else{
                    Usuario usuario = response.body();
                    helper.insereUsuario( usuario );

                    Intent login = new Intent(context, LoginActivity.class);
                    login.putExtra(Constantes.TAG_USUARIO, usuario);
                    startActivity(login);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, R.string.erro_api, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
