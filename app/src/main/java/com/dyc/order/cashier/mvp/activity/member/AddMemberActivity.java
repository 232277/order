package com.dyc.order.cashier.mvp.activity.member;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dyc.order.cashier.R;
import com.dyc.order.cashier.base.MyApplication;
import com.dyc.order.cashier.constant.ActivityOperationField;
import com.dyc.order.cashier.contact.MemberContact;
import com.dyc.order.cashier.data.fields.AddOrChangeMemberFields;
import com.dyc.order.cashier.data.local.DataCache;
import com.dyc.order.cashier.mvp.activity.face.FaceCapture2Activity;
import com.dyc.order.cashier.mvp.activity.face.FaceCaptureByUvcActivity;
import com.dyc.order.cashier.mvp.activity.base.BaseCountDownActivity;
import com.dyc.administrator.toollibrary.utils.KeyBoardUtils;
import com.dyc.administrator.toollibrary.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddMemberActivity extends BaseCountDownActivity<
		MemberContact.MemberModel,
		MemberContact.MemberView,
		MemberContact.MemberPresent> implements MemberContact.MemberView, View.OnClickListener {
	private TextView countDown;
	private EditText memberPhone;
	private EditText memberName;
	private TextView memberBirth;
	private TextView faceRecogStr;
	private RoundedImageView facePic;
	private Button recognize;
	private Button finish;
	private Bitmap faceBitmap;

	private AddOrChangeMemberFields fields;
	private boolean birthPick = false;


	@Override
	public int getLayoutId() {
		return R.layout.activity_add_member;
	}

	@Override
	public void onInitView(View view) {
		countDown = findViewById(R.id.countDown);
		memberPhone = findViewById(R.id.memberPhone);
		memberPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		memberName = findViewById(R.id.memberName);
		faceRecogStr = findViewById(R.id.faceRecogStr);
		facePic = findViewById(R.id.facePic);
		recognize = findViewById(R.id.recognize);
		finish = findViewById(R.id.finish);
		memberBirth = findViewById(R.id.memberBirth);
		memberBirth.setOnClickListener(this);
		recognize.setOnClickListener(this);
		finish.setOnClickListener(this);
		startCountDown();
		showNormalActionBar(getString(R.string.label_top_bar_add_member));
		KeyBoardUtils.ShowKeyboard(memberPhone);
	}

	@Override
	public void onGetMemberCardNo(String code) {
		fields.setCardNo(code);
		getPresenter().addMember(fields);
	}

	@Override
	public void getMemberCardNoFail(Throwable throwable) {
		hideDialog();
		showNotifyDialog(R.string.dialog_member_register_fail_get_member_card_number, R.drawable.fail);
	}

	private void addMember(){
		if (TextUtils.isEmpty(memberPhone.getText())
		|| memberPhone.getText().length() != 11){
			showToast(R.string.tip_member_phone_wrong);
		}else if (TextUtils.isEmpty(memberName.getText())){
			showToast(R.string.tip_member_name_can_not_null);
		}else {
			showLoading(R.string.dialog_pls_wait);
			if (fields == null){
				fields = new AddOrChangeMemberFields();
			}
			fields.setPhoneNumber(memberPhone.getText().toString());
			fields.setUsername(memberName.getText().toString());
			if (birthPick){
				fields.setBirhDate(memberBirth.getText().toString());
			}
			getPresenter().generateCardNo();
		}
	}

	@Override
	public void addMemberSuccess(Integer id) {
		if (id == null){
			showNotifyDialog(R.string.dialog_member_register_add_member_fail, R.drawable.fail);
			return;
		}
		if (faceBitmap != null){
			getPresenter().uploadMemberFace(faceBitmap, id);
		}else {
			hideDialog();
			finish();
		}
		DataCache.addData(ActivityOperationField.ADD_MEMBER, fields);
	}

	@Override
	public void addMemberFail(Throwable throwable) {
		hideDialog();
		showNotifyDialog(throwable.getMessage(), R.drawable.fail);
		logger.info(throwable.getStackTrace());
	}

	@Override
	public void uploadMemberFaceSuccess(String res) {
		hideDialog();
		finish();
	}

	@Override
	public void uploadMemberFaceFail(Throwable throwable) {
		hideDialog();
		finish();
		ToastUtils.showToast(MyApplication.getContext(), R.string.tip_upload_face_feature_fail);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.memberBirth:
				showDatePicker();
				break;
			case R.id.recognize:
				startFaceCpature();
				break;
			case R.id.finish:
				addMember();
				break;
		}
	}

	private void startFaceCpature(){
//		takePhoto();
		startActivityForResult(new Intent(this, FaceCaptureByUvcActivity.class), TAKE_PHOTO_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == FaceCapture2Activity.SUCCESS_GET_FACE){
			showLoading(R.string.label_register_member_face_processing);
			new Thread(()->{
				try {
					faceBitmap = (Bitmap)DataCache.get(ActivityOperationField.FACE_PIC_INFO);
					runOnUiThread(()->{
						facePic.setImageBitmap((Bitmap)DataCache.get(ActivityOperationField.FACE_PIC_INFO));
						facePic.setVisibility(View.VISIBLE);
						recognize.setText(R.string.label_register_member_retry_face_recognize);
					});
				} catch (Exception e) {
					e.printStackTrace();
					showToast(R.string.tip_face_recog_fail);
				}finally {
					hideDialog();
				}
			}).start();
		}else {
			showToast(R.string.tip_face_recog_fail);
			faceBitmap = null;
			facePic.setVisibility(View.INVISIBLE);
		}
	}

	private void showDatePicker(){
		KeyBoardUtils.HideKeyboard(memberName);
		KeyBoardUtils.HideKeyboard(memberPhone);
		TimePickerView pvTime = new TimePickerBuilder(this, (date, v) -> {
			SimpleDateFormat format = new SimpleDateFormat(getString(R.string.toolibrary_date_format_year_month_day), Locale.CHINA);
			memberBirth.setText(format.format(date));
			birthPick = true;
		})
				//年，月，日，时，分，秒
				.setType(new boolean[]{true, true, true, false, false, false})
//				.setLayoutRes()
				.setLineSpacingMultiplier(2f)
				.build();
		pvTime.show();
	}

	private void setCountDownStr(long countTime){
		SpannableString spannableString = new SpannableString("(" + countTime + "s" +")");
		spannableString.setSpan(new ForegroundColorSpan(
						getResources().getColor(R.color.toollibrary_red, getTheme())
				),
				1, ("("+countTime).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		countDown.setText(spannableString);
	}

	@Override
	protected void onCountDown(long countTime) {
		super.onCountDown(countTime);
		runOnUiThread(()-> setCountDownStr(countTime));
	}

	@Override
	public void restartCountDown() {
		super.restartCountDown();
		runOnUiThread(()-> setCountDownStr(getTotalCount()));
	}

	@Override
	protected void onCountFinish() {
		super.onCountFinish();
	}

	@Override
	public MemberContact.MemberModel initM() {
		return new MemberContact.MemberModel() {
		};
	}

	@Override
	public MemberContact.MemberPresent initP() {
		return new MemberContact.MemberPresent() {
		};
	}
}
