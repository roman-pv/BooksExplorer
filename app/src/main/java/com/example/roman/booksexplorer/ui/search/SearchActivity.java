package com.example.roman.booksexplorer.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.example.roman.booksexplorer.data.model.RetrievedBooks;
import com.example.roman.booksexplorer.ui.BooksAdapter;
import com.example.roman.booksexplorer.ui.details.DetailsActivity;
import com.example.roman.booksexplorer.utils.BooksUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity
        implements SearchContract.View, BooksAdapter.BooksAdapterOnItemClickHandler,
        BooksAdapter.OverflowMenuClickHandler {

    public static final String BOOK_KEY =
            "com.example.roman.booksexplorer.book";
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

    @BindView(R.id.toolbar_search)
    Toolbar mSearchToolbar;

    @BindView(R.id.results_recycler_view)
    RecyclerView mResultsRecyclerView;

    @BindView(R.id.loading_progress_bar)
    ProgressBar mLoadingProgressBar;

    @BindView(R.id.empty_view)
    View mEmptyView;

    @BindView(R.id.constraint_layout)
    View mParentView;
    boolean mRestoreState;
    private android.support.v7.widget.SearchView mSearchView;
    private Menu mSearchMenu;
    private MenuItem mSearchItem;
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

        setSupportActionBar(mToolbar);
        // Sets up the search functionality in toolbar
        setSearchToolbar();

        setupRecyclerView();
        setupPresenter();

    }

    private void setupRecyclerView() {

        mLayoutManager = new LinearLayoutManager(this);
        mResultsRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BooksAdapter(this, this, this, mPicasso);
        mResultsRecyclerView.setAdapter(mAdapter);

    }

    private void setupPresenter() {
        mSearchPresenter.setView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!TextUtils.isEmpty(mQuery)) {
            restoreSearchState();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_search:
                mSearchToolbar.setVisibility(View.VISIBLE);
                mSearchItem.expandActionView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchToolbar() {
        if (mSearchToolbar != null) {
            mSearchToolbar.inflateMenu(R.menu.search_menu);
            mSearchMenu = mSearchToolbar.getMenu();

            mSearchToolbar.setNavigationOnClickListener(v -> mSearchToolbar.setVisibility(View.GONE));

            mSearchItem = mSearchMenu.findItem(R.id.action_filter_search);

            mSearchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    mSearchToolbar.setVisibility(View.GONE);
                    clearSearch();
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }
            });

            setupSearchView();
        }
    }

    public void setupSearchView() {
        mSearchView =
                (android.support.v7.widget.SearchView) mSearchMenu.findItem(R.id.action_filter_search).getActionView();

        mSearchView.setSubmitButtonEnabled(false);

        // Sets custom colors for editText field in a searchView
        EditText searchEditText = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint(R.string.search_hint);
        searchEditText.setHintTextColor(getResources().getColor(R.color.textDarkSecondary));
        searchEditText.setTextColor(getResources().getColor(R.color.colorPrimary));


        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            /**
             * Handles search when the query is submitted
             */
            public void search(String query) {

                // Checks if it's a new query. If it is, it shouldn't restore RecycleView position.
                if (TextUtils.isEmpty(mQuery) || !mQuery.equals(query)) {
                    mQuery = query;
                    mRestoreState = false;
                }
                mResultsRecyclerView.setVisibility(View.VISIBLE);
                mSearchPresenter.getResultsBasedOnQuery(query);
            }

        });

    }

    /**
     * Implements onItemClick method of the RecyclerView ViewHolder,
     * opens detailsActivity on click
     */
    @Override
    public void onItemClick(Book book) {
        Intent startDetailsActivityIntent = new Intent(this, DetailsActivity.class);
        startDetailsActivityIntent.putExtra(BOOK_KEY, book);
        startActivity(startDetailsActivityIntent);
    }

    /**
     * Implements overflow menu functionality of the cards from the RecyclerView
     */
    @Override
    public void onOverflowMenuClick(View view, Book book) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.inflate(R.menu.card_overflow_menu);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.overflow_menu_share:
                    shareBookDetails(book);
                    break;
                case R.id.overflow_menu_open_url:
                    openBookInfoInBrowser(book);
                    break;
            }
            return false;
        });
        popup.show();
    }

    /**
     * Allows to save started search and RecyclerView position during rotation.
     */
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
    public void showSnackbar(String message) {
        Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG).show();

    }

    /**
     * Handles retrieved search result, displaying books with the RecyclerView Adapter
     */
    @Override
    public void displayResult(RetrievedBooks retrievedBooks) {
        mAdapter.swapBooks(retrievedBooks.getBooks());
        if (mRestoreState) {
            mLayoutManager.onRestoreInstanceState(mLayoutState);
        } else {
            mLayoutManager.scrollToPosition(0);
        }
    }

    @Override
    public void displayError(int messageResourseId) {
        showSnackbar(getString(messageResourseId));
    }

    /**
     * Toggles visibility of the progress bar (loading indicator).
     */
    @Override
    public void setProgressBar(Boolean isVisible) {
        if (isVisible) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        } else {
            mLoadingProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Toggles visibility of the starting empty view
     */
    @Override
    public void setEmptyView(Boolean isVisible) {
        if (isVisible) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Hides search results and restores the starting empty view
     */
    @Override
    public void clearSearch() {
        mAdapter.swapBooks(null);
        mSearchPresenter.unsubscribe();
        mRestoreState = false;
        mQuery = null;
        setEmptyView(true);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // releases rxjava subscription
        mSearchPresenter.unsubscribe();
    }

    /**
     * Handles screen rotation, restoring started search.
     */
    private void restoreSearchState() {
        mRestoreState = true;
        mSearchToolbar.setVisibility(View.VISIBLE);
        mSearchItem.expandActionView();
        mSearchView.setQuery(mQuery, true);
    }

    /**
     * Opens 'Share' pop-up, allowing to share the book or copy its basic info.
     */
    private void shareBookDetails(Book book) {
        String textToShare = BooksUtils.getTextToShare(this, book);

        String mimeType = getString(R.string.mime_type_text);

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(getString(R.string.share_dialog_title))
                .setText(textToShare)
                .startChooser();
    }

    /**
     * Menu action, opens the link to the google books or google play page of the book
     */
    public void openBookInfoInBrowser(Book book) {
        String bookUrl = book.getVolumeInfo().getInfoLink();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            displayError(R.string.no_browser_error);
        }
    }

    @Override
    public void onBackPressed() {
        // Allows to collapse the search bar and hide search results when back button is pressed
        if (mSearchItem.isActionViewExpanded()) {
            mSearchItem.collapseActionView();
            return;
        } else {
            super.onBackPressed();
        }
    }


}
