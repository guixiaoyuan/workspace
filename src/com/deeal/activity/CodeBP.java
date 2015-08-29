package com.deeal.activity;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;

public class CodeBP {

	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9',

			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static CodeBP bpUtil;

	public static CodeBP getInstance() {
		if (bpUtil == null)
			bpUtil = new CodeBP();
		return bpUtil;
	}

	// width="150" height="60"
	// base_padding_left="12"
	// range_padding_left="10"
	// base_padding_top="25"
	// range_padding_top="20"
	// codeLength="4"
	// point_number="30"
	// font_size="30"

	// default settings
	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final int DEFAULT_FONT_SIZE = 30;
	private static final int DEFAULT_POINT_NUMBER = 30;
	private static final int BASE_PADDING_LEFT = 15, RANGE_PADDING_LEFT = 15,
			BASE_PADDING_TOP = 25, RANGE_PADDING_TOP = 20;
	private static final int DEFAULT_WIDTH = 150, DEFAULT_HEIGHT = 60;

	// settings decided by the layout xml
	// canvas width and height
	private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

	// random word space and pading_top
	private int base_padding_left = BASE_PADDING_LEFT,
			range_padding_left = RANGE_PADDING_LEFT,
			base_padding_top = BASE_PADDING_TOP,
			range_padding_top = RANGE_PADDING_TOP;

	// number of chars, lines; font size
	private int codeLength = DEFAULT_CODE_LENGTH,
			line_number = DEFAULT_POINT_NUMBER, font_size = DEFAULT_FONT_SIZE;

	// variables
	private String code;
	private int padding_left, padding_top;
	private Random random = new Random();

	public Bitmap createBitmap() {
		padding_left = 0;

		Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(bp);

		code = createCode();

		c.drawColor(Color.WHITE);
		Paint paint = new Paint();
		paint.setTextSize(font_size);

		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
		}

		for (int i = 0; i < line_number; i++) {
			drawLine(c, paint);
		}

		c.save(Canvas.ALL_SAVE_FLAG);// ����
		c.restore();//
		return bp;
	}

	public String getCode() {
		return code;
	}

	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		paint.setStrokeWidth(2);
		paint.setColor(color);
		canvas.drawPoint(startX, startY, paint);
	}

	private int randomColor() {
		return randomColor(1);
	}

	private int randomColor(int rate) {
		int red = 150;
		int green = 150;
		int blue = 150;
		return Color.rgb(red, green, blue);
	}

	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean()); // trueΪ���壬falseΪ�Ǵ���
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX); // float���Ͳ������ʾ��б��������б
		// paint.setUnderlineText(true); //trueΪ�»��ߣ�falseΪ���»���
		// paint.setStrikeThruText(true); //trueΪɾ���ߣ�falseΪ��ɾ����
	}

	private void randomPadding() {
		padding_left += base_padding_left + random.nextInt(range_padding_left);
		padding_top = base_padding_top + random.nextInt(range_padding_top);
	}
}