package com.example.lab7_galeriedestars;





import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.lab7_galeriedestars.adapter.StarAdapter;
import com.example.lab7_galeriedestars.databinding.ActivityMainBinding;
import com.example.lab7_galeriedestars.service.StarService;
import java.util.List;
import com.example.lab7_galeriedestars.model.Star;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StarAdapter adapter;
    private StarService service;
    private List<Star> starList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setSupportActionBar(binding.toolbar);

        // Service + donnees
        service  = new StarService();
        starList = service.findAll();

        // RecyclerView
        adapter = new StarAdapter(starList, this::showRatingDialog);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    // ─── Menu Toolbar (Search + Share) ───────────────────────────────────────

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Rechercher...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String q) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                adapter.filter(newText, starList);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ─── Partager l'application ──────────────────────────────────────────────

    private void shareApp() {
        String texte =
                "Decouvrez Stars App !\n\n" +
                        "Une galerie des plus grandes stars avec notes et photos.\n\n" +
                        "Telecharger : https://github.com/TON_USERNAME/stars-app";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, texte);
        startActivity(Intent.createChooser(shareIntent, "Partager l'app via..."));
    }

    // ─── Popup modifier la note ──────────────────────────────────────────────

    private void showRatingDialog(Star star, int position) {
        // Gonfler le layout du dialog
        android.view.View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_rating, null);

        TextView tvName   = dialogView.findViewById(R.id.tvDialogName);
        RatingBar rbPopup = dialogView.findViewById(R.id.rbDialog);

        tvName.setText(star.getNom());
        rbPopup.setRating(star.getRating());

        new AlertDialog.Builder(this)
                .setTitle("Modifier la note")
                .setView(dialogView)
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    // Mettre a jour la note
                    float newRating = rbPopup.getRating();
                    star.setRating(newRating);
                    service.update(star);

                    // Mise a jour dynamique sans recharger toute la liste
                    adapter.notifyItemChanged(position);

                    Toast.makeText(this,
                            star.getNom() + " : " + newRating + " ★",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}