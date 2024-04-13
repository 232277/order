package com.dyc.administrator.toollibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.administrator.toollibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * func:
 * author:丁语成 on 2020/3/8 22:49
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class SortChooseTab extends ConstraintLayout {
	public static int STATE_UNSELECTED = 0;
	public static final Map<String, Set<SortChooseTab>> allInstances = new HashMap<>();
	private String groupName;
	private boolean selected;
	private int id;
	private Object data;
	private String name;
	private ColorStateList lineColor;
	private ColorStateList lineColorUnSelected;
	private ColorStateList nameColor;
	private ColorStateList nameColorUnSelected;
	private List<Integer> imgId = new ArrayList<>();
	private int textSize;
	private int selectedTimes = 0;
	private SelectedListener listener;

	private View line;
	private TextView nameText;
	private ImageView img;
	private ConstraintLayout parentLayout;

	public SortChooseTab(Context context) {
		super(context);

	}

	public SortChooseTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SortChooseTab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs){
		LayoutInflater.from(getContext()).inflate(R.layout.toollibrary_sort_choose_tab_layout, this);
		line = findViewById(R.id.line);
		nameText = findViewById(R.id.name);
		img = findViewById(R.id.img);
		parentLayout = findViewById(R.id.parentLayout);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortChooseTab);
		groupName = typedArray.getString(R.styleable.SortChooseTab_sort_group_name);
		selected = typedArray.getBoolean(R.styleable.SortChooseTab_sort_selected, false);
		id = typedArray.getInt(R.styleable.SortChooseTab_sort_id, -1);
		name = typedArray.getString(R.styleable.SortChooseTab_sort_name);
		lineColor = typedArray.getColorStateList(R.styleable.SortChooseTab_sort_line_color);
		lineColorUnSelected = typedArray.getColorStateList(R.styleable.SortChooseTab_sort_line_color_unselected);
		nameColor = typedArray.getColorStateList(R.styleable.SortChooseTab_sort_name_color);
		nameColorUnSelected = typedArray.getColorStateList(R.styleable.SortChooseTab_sort_name_color_unselected);
		imgId.add(typedArray.getResourceId(R.styleable.SortChooseTab_sort_imgId_unselected, 0));
		imgId.add(typedArray.getResourceId(R.styleable.SortChooseTab_sort_imgId, -1));
		textSize = typedArray.getDimensionPixelSize(R.styleable.SortChooseTab_sort_name_text_size, 12);
		addToGroup();
		this.setOnClickListener(v -> {
			setTabSelected(true);
		});
	}

	private void addToGroup(){
		if (groupName != null){
			if (allInstances.containsKey(groupName) && allInstances.get(groupName) != null){
				allInstances.get(groupName).add(this);
			}else {
				allInstances.put(groupName, new HashSet<SortChooseTab>(){{
					add(SortChooseTab.this);
				}});
			}
		}
	}

	public void addSelectedImg(int imgId){
		if (this.imgId.isEmpty() || this.imgId.get(0) != -1){
			this.imgId.add(imgId);
		}else {
			this.imgId.set(0, imgId);
		}
	}

	public void setTabSelected(boolean selected){
		this.selected = selected;
		int state;
		if (selected){
			selectedTimes %= imgId.size();
			if (selectedTimes == 0) {
				selectedTimes = 1;
			}
			state = selectedTimes;
			line.setBackgroundColor(lineColor.getDefaultColor());
			nameText.setTextColor(nameColor);
			img.setImageResource(imgId.get(selectedTimes++));
			changeGroupSelected();
		}else {
			line.setBackgroundColor(lineColorUnSelected.getDefaultColor());
			nameText.setTextColor(nameColorUnSelected);
			img.setImageResource(imgId.get(0));
			selectedTimes = 0;
			state = selectedTimes;
		}
		if (listener!=null){
			listener.onSelected(state, name, data);
		}
	}

	private void changeGroupSelected(){
		if (groupName != null){
			Set<SortChooseTab> tabs = allInstances.get(groupName);
			for (SortChooseTab tab : tabs){
				//关闭其他tab
				if (tab.id != id){
					tab.setTabSelected(false);
				}
			}
		}
	}

	public void setListener(SelectedListener listener) {
		this.listener = listener;
	}

	private void apply(){
		setTabSelected(selected);
		nameText.setText(name);
		nameText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		apply();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public interface SelectedListener{
		void onSelected(int state, String name, Object data);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public View getLine() {
		return line;
	}

	public void setLine(View line) {
		this.line = line;
	}

	public TextView getNameText() {
		return nameText;
	}

	public void setNameText(TextView nameText) {
		this.nameText = nameText;
	}

	public ImageView getImg() {
		return img;
	}

	public void setImg(ImageView img) {
		this.img = img;
	}

	public ConstraintLayout getParentLayout() {
		return parentLayout;
	}

	public void setParentLayout(ConstraintLayout parentLayout) {
		this.parentLayout = parentLayout;
	}
}
