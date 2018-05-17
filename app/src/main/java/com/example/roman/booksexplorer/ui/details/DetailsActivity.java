package com.example.roman.booksexplorer.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roman.booksexplorer.BooksUtils;
import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

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

    @Inject Picasso mPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Book book = getIntent().getParcelableExtra("book");
        showBookDetails(book);


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
        } else {
            mPicasso.load(R.drawable.ic_favorite).into(mCoverImageView);
        }



    }

    @Override
    public void openBookInfoInBrowser(Book book) {

    }
}
