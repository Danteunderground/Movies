package luiz.br.com.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnCreateContextMenuListener, View.OnClickListener,
        MenuItem.OnMenuItemClickListener {
    private Preferencias prefs;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private TextView listaVazia;
    private List<Movies> movies;
    private Movies movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        iniciaElementos();
        trataToolbar();
        validaNavegacao();
        trataFAB();
        trataNavigationDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaTela();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_adiciona_movie:
                vaiParaFormulario();
                break;

            case R.id.nav_sobre:
                Intent sobre = new Intent(this, AboutActivity.class);
                startActivity(sobre);
                break;

            case R.id.nav_sair:
                prefs.logout();
                validaNavegacao();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void validaNavegacao() {
        if (!prefs.estaLogado()) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    }

    private void iniciaElementos() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        prefs = new Preferencias(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        databaseHelper = new DatabaseHelper(this);
        listaVazia = (TextView) findViewById(R.id.mensagem_movies_vazio);
    }

    private void trataToolbar() {
        setSupportActionBar(toolbar);
    }

    private void trataFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormulario();
            }
        });
    }

    private void trataNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void populaTela() {
        movies = databaseHelper.getMovies();

        exibeRecyclerView(movies.size());

        MoviesAdapter adapter = new MoviesAdapter(this, movies);
        recyclerView.setAdapter(adapter);
    }

    private void exibeRecyclerView(int tamanhoLista) {
        if (tamanhoLista > 0) {
            listaVazia.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);
        } else {
            listaVazia.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void vaiParaFormulario() {
        Intent form = new Intent(this, MovieForm.class);
        startActivity(form);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int position = recyclerView.getChildLayoutPosition(v);
        movie = movies.get(position);

        menu.setHeaderTitle(movie.getName());
        MenuItem editar = menu.add(getString(R.string.menu_editar));
        MenuItem deletar = menu.add(getString(R.string.menu_deletar));

        editar.setOnMenuItemClickListener(this);
        deletar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getTitle().equals(getString(R.string.menu_editar))) {
            editaRestaurante();
        } else if (menuItem.getTitle().equals(getString(R.string.menu_deletar))) {
            deletaRestaurante();
        }

        return false;
    }

    @Override
    public void onClick(View view) {
    }

    private void editaRestaurante() {
        Intent form = new Intent(this, MovieForm.class);
        form.putExtra(Constantes.TAG_MOVIE, movie);
        startActivity(form);
    }

    private void deletaRestaurante() {
        databaseHelper.deletaMovies(movie);
        Toast.makeText(this, R.string.sucesso_deleta, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }
}