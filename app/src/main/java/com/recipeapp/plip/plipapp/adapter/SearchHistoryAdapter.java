package com.recipeapp.plip.plipapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.viewholder.RecipeItemViewHolder;
import com.recipeapp.plip.plipapp.adapter.viewholder.SearchHistoryViewHolder;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.SearchHistoryModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Adapter classes are responsible for mapping the data in large sets of objects into the views
 * that will be displaying them.  These will most often be ListViews or RecyclerViews. The RecipeApiAdapter
 * is intended to supply data bound to item_recipe layouts for a RecyclerView in activity_search.
 * Therefore, we need to extend the RecyclerView.Adapter class.
 *
 * This abstract class is templated to require a class deriving from RecyclerView.ViewHolder class. In t
 * this case, the {@link RecipeItemViewHolder} class has been created
 * to derive from the base ViewHolder.  A ViewHolder is intended to contain the data for a single item in
 * a collection to be bound to a an item view in the user interface.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryViewHolder> implements Serializable {

    // A local collection that will eventually be assigned a collection of RecipeItemModels as a
    // part of class instantiation
    private ArrayList<SearchHistoryModel> recipeItemCollection;
    private OnItemSelected onItemSelected;

    // A constructor requiring a collection of RecipeItemModels
    public SearchHistoryAdapter(ArrayList<SearchHistoryModel> recipeItemCollection){
        this.recipeItemCollection = recipeItemCollection;
    }

    // An overridden virtual method that is responsible for creating an instance of a ViewHolder to
    // contain the data for a given object.
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // LayoutInflater is a utility that allows us to assign a view to the local instance of View.
        // We need access to the ViewGroup parent (the base layout in SearchActivty) so that we have
        // the required context to find the item_recipe layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);

        // We return a new instance of RecipeItemViewHolder after passing it a a view to bind data to
        return new SearchHistoryViewHolder(view);
    }

    // An overridden virtual method that binds an item in the collection of RecipesItemModels to the
    // instance of the ViewHolder created in {@see onCreateViewHolder}.
    @Override
    public void onBindViewHolder(SearchHistoryViewHolder holder, int position) {
        // Retrieve a RecipeItemModel from the collection
        SearchHistoryModel item = recipeItemCollection.get(position);

        holder.setOnRecipeItemClicked(new SearchHistoryViewHolder.OnRecipeItemClicked(){
            @Override
            public void onClick(SearchHistoryModel item) {
                if(onItemSelected != null) {
                    onItemSelected.onSelected(item);
                }
            }
        });

        // Bind the ComplexRecipeItemModel data to the view managed by the ViewHolder
        holder.bind(item);
    }

    @Override
    public final void onViewRecycled(final SearchHistoryViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setOnRecipeItemClicked(null);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
          return recipeItemCollection.size();
    }

    // The setter that allows other classes to create a reference to the listener.
    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    // An interface defined internal to the Adapter class.  This interface, in conjunction with the
    // ViewHolder interface, will relay events captured on the ViewHolder (selection events) up to
    // the Adapter, and subsequently, up to the SearchFragment and SearchActivity by means of a chained
    // set of listeners.
    public interface OnItemSelected {
        void onSelected(SearchHistoryModel item);
    }
}