package com.example.roman.booksexplorer.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roman.booksexplorer.BooksUtils;
import com.example.roman.booksexplorer.R;
import com.example.roman.booksexplorer.data.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private final Context mContext;

    private BooksAdapterOnItemClickHandler mClickHandler;

    private List<Book> mBooks;

    private Picasso mPicasso;

    public BooksAdapter(Context context,
                        BooksAdapterOnItemClickHandler onItemClickHandler, Picasso picasso) {
        this.mContext = context;
        this.mClickHandler = onItemClickHandler;
        this.mPicasso = picasso;
    }

    @Override
    public BooksAdapter.BooksAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.recyclerview_book, viewGroup, false);
        view.setFocusable(true);
        return new BooksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.BooksAdapterViewHolder holder, int position) {

        Book currentBook = mBooks.get(position);

        String title = currentBook.getVolumeInfo().getTitle();
        holder.titleTextView.setText(title);

        List<String> authorsList = currentBook.getVolumeInfo().getAuthors();
        if (authorsList != null && authorsList.size() > 0) {
            String authors = BooksUtils.formatStringList(authorsList, true);
            holder.authorTextView.setText(authors);
        }

        if (currentBook.getVolumeInfo().getImageLinks() != null) {
            String thumbnailUrl = currentBook.getVolumeInfo().getImageLinks().getThumbnail();

            mPicasso.load(thumbnailUrl)
                    .into(holder.thumbnailImageView);
        }



    }

    @Override
    public int getItemCount() {
        if (null == mBooks) return 0;
        else return mBooks.size();
    }

    public void swapBooks(List<Book> newBooks) {
        mBooks = newBooks;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onReviewItemClick messages.
     */
    public interface BooksAdapterOnItemClickHandler {
        void onItemClick(Book book);
    }

    class BooksAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.thumbnail_image_view)
        ImageView thumbnailImageView;

        @BindView(R.id.title_text_view)
        TextView titleTextView;

        @BindView(R.id.author_text_view)
        TextView authorTextView;

        public BooksAdapterViewHolder(View view) {

            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Book book = mBooks.get(adapterPosition);
            mClickHandler.onItemClick(book);
        }
    }
}