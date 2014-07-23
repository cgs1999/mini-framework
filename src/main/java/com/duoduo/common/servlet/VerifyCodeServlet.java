package com.duoduo.common.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码生成器
 * @author chengesheng@gmail.com
 * @date 2014-6-3 下午2:09:49
 * @version 1.0.0
 */
public class VerifyCodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 取消验证码中容易混淆的字符，如1和l、0和o和O
	// private static String extEnt =
	// "0123456789zxcvbnmaZXCVBNMASDFGHJKLQWERTYUIOPsdfghjklqwertyuiop";
	private static String extEnt = "23456789zxcvbnmaZXCVBNMASDFGHJKLQWERTYUIPsdfghjkqwertyuip";

	// 附件描述
	private String sessionKey = "verifyCode";

	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		// 获取servlet配置的"sessionKey"
		String key = getInitParameter("sessionKey");
		if (key != null && !"".equals(key)) {
			sessionKey = key;
		}
	}

	/**
	 * DOGET生成随机验证码
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		OutputStream out = null;
		try {
			// 设置页面不缓存
			res.setHeader("Pragma", "No-cache");
			res.setHeader("Cache-Control", "no-cache");
			res.setDateHeader("Expires", 0);
			// 设置图片的长宽
			int width = 120, height = 35;
			// 创建内存图像
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			// 创建随机类的实例
			Random random = new Random();
			// 设定图像背景色
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			// 设置随机字体及大小
			g.setFont(new Font("Times New Roman", Font.PLAIN, 38));
			// 在图片背景上增加噪点
			g.setColor(new Color(255, 255, 255));

			g.drawRect(0, 0, width - 1, height - 1);

			// 设置字体的随机颜色
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			char[] cRes = new char[4];
			for (int i = 0; i < cRes.length; i++) {
				cRes[i] = extEnt.charAt(random.nextInt(extEnt.length()));
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(String.valueOf(cRes[i]), 13 * i + 30, 31);
			}
			List<Object> randomList = new ArrayList<Object>();
			randomList.add(String.valueOf(cRes).toLowerCase());
			// randomList.add("xxxx");
			randomList.add(new Date(System.currentTimeMillis()));
			req.getSession().setAttribute(this.sessionKey, String.valueOf(cRes).toLowerCase());
			// arg2.getSession().setAttribute("sessionRandom", "xxxx");
			g.dispose();
			out = res.getOutputStream();
			ImageIO.write(image, "JPEG", out);
		} catch (Exception e) {
		} finally {
			if (out != null) try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
