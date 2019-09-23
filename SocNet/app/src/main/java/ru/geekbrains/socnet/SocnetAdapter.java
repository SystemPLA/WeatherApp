package ru.geekbrains.socnet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// адаптер
public class SocnetAdapter extends RecyclerView.Adapter<SocnetAdapter.ViewHolder> {

    private final List<Soc> dataSource;                         // Наш источник данных
    private OnItemClickListener itemClickListener;  // Слушатель, будет устанавливаться извне

    // этот класс хранит связь между данными и элементами View
    // сложные данные (как в этом примере) требуют несколько View на один элемент списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView description;
        private final ImageView picture;
        private final CheckBox like;

        public ViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.description);
            picture = view.findViewById(R.id.picture);
            like = view.findViewById(R.id.like);

            // обработчик нажатий на элемент списка
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                notifyItemChanged(getAdapterPosition());
                CheckBox cb = (CheckBox) view;
                dataSource.get(getAdapterPosition()).setLike(cb.isChecked());
                }
            });
        }
    }

    // интерфейс для обработки нажатий на элемент списка (как в ListView)
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // устанавливаем слушателя нажатий
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Передаем в конструктор источник данных
    // В нашем случае это список, но может быть и запрос к БД
    public SocnetAdapter(List<Soc> dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    @NonNull
    @Override
    public SocnetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса через Inflater
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // Здесь можно установить параметры
        ViewHolder viewHolder = new ViewHolder(view);

        // на каком-то этапе будет переиспользование карточки, и в лог эта строка не попадет
        // а строка onBindViewHolder попадет, это будет означать, что старая карточка
        // переоткрыта с новыми данными
        Log.d("SocnetAdapter", "onCreateViewHolder");
        return viewHolder;
    }

    // Связывание данных (data binding) в пользовательском интерфейсе
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Получить элемент из источника данных (БД, интернет…)
        final int pos = position;
        Soc item = dataSource.get(position);

        // Вывести на экран, используя ViewHolder
        holder.description.setText(item.getDescription());
        holder.picture.setImageResource(item.getPicture());
        holder.like.setChecked(item.getLike());

        // отрабатывает при необходимости нарисовать карточку
        Log.d("SocnetAdapter", "onBindViewHolder");
    }

    // Вернуть размер данных
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
