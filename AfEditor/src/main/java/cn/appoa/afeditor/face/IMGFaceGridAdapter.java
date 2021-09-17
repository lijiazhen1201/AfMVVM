package cn.appoa.afeditor.face;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.appoa.afeditor.R;

public class IMGFaceGridAdapter extends BaseQuickAdapter<IMGFace, BaseViewHolder> {

    public IMGFaceGridAdapter(int layoutResId, @Nullable List<IMGFace> data) {
        super(R.layout.image_face_grid_item, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, IMGFace item) {
        holder.setImageBitmap(R.id.iv_icon, item.getIcon());
        holder.setText(R.id.tv_text, item.getText());
    }


//        extends RecyclerView.Adapter<IMGFaceGridAdapter.ViewHolder> {
//
//    private Context context;
//    private List<IMGFace> data;
//
//    public IMGFaceGridAdapter(Context context, List<IMGFace> data) {
//        this.context = context;
//        this.data = data;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(context).inflate(R.layout.image_face_grid_item, parent, false);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        IMGFace item = data.get(position);
//        holder.iv_icon.setImageBitmap(item.getIcon());
//        holder.tv_text.setText(item.getText());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (OnItemClickListener != null) {
//                    OnItemClickListener.onItemClick(v, position);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return data == null ? 0 : data.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView iv_icon;
//        private TextView tv_text;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            iv_icon = itemView.findViewById(R.id.iv_icon);
//            tv_text = itemView.findViewById(R.id.tv_text);
//        }
//    }
//
//    private OnItemClickListener OnItemClickListener;
//
//    public void setOnItemClickListener(IMGFaceGridAdapter.OnItemClickListener onItemClickListener) {
//        OnItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }

}
