package edu.cs.uga.roommatesshopping.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.cs.uga.roommatesshopping.R;
import edu.cs.uga.roommatesshopping.adapter.ShoppingListAdapter;
import edu.cs.uga.roommatesshopping.databinding.FragmentHomeBinding;


/**
 * Displays the current shopping lists
 */
public class HomeFragment extends Fragment implements ShoppingListAdapter.OnListListener, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private ArrayList<String> shoppingList = new ArrayList<>();
    private ShoppingListAdapter adapter = new ShoppingListAdapter(generateFakeValues(), this);
    ;
    private NavController navController = null;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.recyclerviewShoppingLists.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewShoppingLists.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerviewShoppingLists);
        binding.recyclerviewShoppingLists.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // holds reference to navigation graph
        // TODO: set listeners
        navController = Navigation.findNavController(view);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // TODO
        if (item.getItemId() == R.id.new_shopping_list) {
            navController.navigate(R.id.action_homeFragment_to_itemEntryFragment);
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_home, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<String> generateFakeValues() {
        shoppingList.add("Shopping list 1");
        shoppingList.add("Shopping list 2");
        shoppingList.add("Shopping list 3");
        shoppingList.add("Shopping list 4");
        shoppingList.add("Shopping list 5");
        shoppingList.add("Shopping list 6");
        shoppingList.add("Shopping list 7");
        shoppingList.add("Shopping list 8");
        shoppingList.add("Shopping list 9");
        return shoppingList;
    }

    @Override
    public void onListClick(int position) {
        Log.d(TAG, "onListClick: clicked: " + position);

        // Navigate to ListDetailFragment
        navController.navigate(R.id.action_homeFragment_to_listDetailFragment);
    }

    /**
     * Deletes items from the recyclerview and database with swiping to the left or right
     */
    private ItemTouchHelper.SimpleCallback itemTouchHelper =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // TODO: Remove shopping list from database
                    shoppingList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            };

    @Override
    public void onClick(View v) {

    }
}
