package com.example.roman.booksexplorer.ui.search;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.example.roman.booksexplorer.data.model.BooksList;
import com.example.roman.booksexplorer.ui.BooksAdapter;
import com.example.roman.booksexplorer.ui.details.DetailsActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity
        implements SearchContract.View, BooksAdapter.BooksAdapterOnItemClickHandler {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final String LAYOUT_STATE_KEY =
            "com.example.roman.booksexplorer.ui.search.layout_state";
    private static final String QUERY_KEY =
            "com.example.roman.booksexplorer.ui.search.query";

    @Inject
    Picasso mPicasso;

    @Inject
    SearchPresenter mSearchPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.results_recycler_view)
    RecyclerView mResultsRecyclerView;

    @BindView(R.id.loading_progress_bar)
    ProgressBar mLoadingProgressBar;

    private SearchView mSearchView;
    private BooksAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mLayoutState;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupViews();
        setupPresenter();
    }

    private void setupViews() {

        setSupportActionBar(mToolbar);
        mLayoutManager = new LinearLayoutManager(this);
        mResultsRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BooksAdapter(this, this, mPicasso);
        mResultsRecyclerView.setAdapter(mAdapter);

    }

    private void setupPresenter() {
        mSearchPresenter.setView(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();

        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

//        mSearchView.setOnCloseListener(() -> {
//            mSearchPresenter.unsubscribe();
//            return false;
//        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(mQuery)) {
                    mQuery = query;
                }
                mSearchView.clearFocus();
                mSearchPresenter.getResultsBasedOnQuery(mQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        if (!TextUtils.isEmpty(mQuery)) {
            searchMenuItem.expandActionView();
            mSearchView.setIconified(false);
            mSearchView.setQuery(mQuery, true);
        }

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SearchActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayResult(BooksList booksList) {
        mAdapter.swapBooks(booksList.getBooks());
        mLayoutManager.onRestoreInstanceState(mLayoutState);
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void setProgressBar(Boolean isVisible) {
        if (isVisible) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            mLoadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Book book) {
        Intent startDetailsActivityIntent = new Intent(this, DetailsActivity.class);
        startDetailsActivityIntent.putExtra("book", book);
         startActivity(startDetailsActivityIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLayoutState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LAYOUT_STATE_KEY, mLayoutState);
        outState.putString(QUERY_KEY, mQuery);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mQuery = savedInstanceState.getString(QUERY_KEY);
        mLayoutState = savedInstanceState.getParcelable(LAYOUT_STATE_KEY);
        }

    @Override
    protected void onStop() {
        super.onStop();
        mSearchPresenter.unsubscribe();
    }
}
