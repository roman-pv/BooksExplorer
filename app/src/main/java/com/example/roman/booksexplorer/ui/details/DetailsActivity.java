package com.example.roman.booksexplorer.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.example.roman.booksexplorer.ui.search.SearchActivity;
import com.example.roman.booksexplorer.utils.BooksUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Activity that displays detailed info about the selected book.
 */
public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cover_image_view)
    ImageView mCoverImageView;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.subtitle_text_view)
    TextView mSubitleTextView;
    @BindView(R.id.authors_text_view)
    TextView mAuthorsTextView;
    @BindView(R.id.description_text_view)
    TextView mDescriptionTextView;
    @BindView(R.id.scroll_view)
    View mParentView;

    @Inject
    Picasso mPicasso;

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        // Sets up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Loads the book from the parcel
        mBook = getIntent().getParcelableExtra(SearchActivity.BOOK_KEY);
        // Displays the book info
        showBookDetails(mBook);

    }

    @Override
    public void showBookDetails(Book book) {
        String title = book.getVolumeInfo().getTitle();
        String subtitle = book.getVolumeInfo().getSubtitle();
        List<String> authorsList = book.getVolumeInfo().getAuthors();
        String description = book.getVolumeInfo().getDescription();
        String coverImage = book.getVolumeInfo().getImageLinks().getThumbnail();

        if (!TextUtils.isEmpty(title)) {
            mTitleTextView.setText(title);
        }

        if (!TextUtils.isEmpty(subtitle)) {
            mSubitleTextView.setText(subtitle);
        } else {
            mSubitleTextView.setVisibility(View.GONE);
        }

        if (authorsList != null && authorsList.size() > 0) {
            String authors = BooksUtils.formatStringList(authorsList, false);
            mAuthorsTextView.setText(authors);

        }

        if (!TextUtils.isEmpty(description)) {
            mDescriptionTextView.setText(description);
        }

        if (!TextUtils.isEmpty(coverImage)) {
            mPicasso.load(book.getVolumeInfo().getImageLinks().getThumbnail())
                    .into(mCoverImageView);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_open_url:
                openBookInfoInBrowser(mBook);
                return true;

            case R.id.menu_item_share:
                shareBookDetails(mBook);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Menu action, opens the link to the google books or google play page of the book
     */
    @Override
    public void openBookInfoInBrowser(Book book) {
        String bookUrl = book.getVolumeInfo().getInfoLink();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
        // Checks if there's an app that can open this intent
        // (which must be the case, so it's unlikely to be false)
        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            displayError(R.string.no_browser_error);
        }
    }

    @Override
    public void displayError(int messageResourceId) {
        showSnackbar(getString(messageResourceId));
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Opens 'Share' pop-up, allowing to share the book or copy its basic info.
     */
    private void shareBookDetails(Book book) {

        String textToShare = BooksUtils.getTextToShare(this, book);

        //Specifies the type of content that is to be shared
        String mimeType = getString(R.string.mime_type_text);

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(getString(R.string.share_dialog_title))
                .setText(textToShare)
                .startChooser();
    }
}
