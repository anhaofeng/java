package com.ahf.exam.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@Controller
public class IndexController {
    @RequestMapping("/login")
    public String root(){
        return "login";
    }
    public Color getRandomColor(int fc, int bc){
        Random random = new Random();
        Color randomColor = null;
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        //设置个0-255之间的随机颜色值
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        randomColor = new Color(r,g,b);
        return randomColor;//返回具有指定红色、绿色和蓝色值的不透明的 sRGB 颜色
    }
    @GetMapping(value = "/createCode")
    public  void  createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //禁止页面缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");		//设置响应正文的MIME类型为图片


        int width=120, height=40;
        /**创建一个位于缓存中的图像，宽度60，高度20 */
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();      		//获取用于处理图形上下文的对象，相当于画笔
        Random random = new Random();        		//创建生成随机数的对象
        g.setColor(getRandomColor(200,250));  	  	//设置图像的背景色
        g.fillRect(0, 0, width, height);            //画一个矩形	，坐标（0，0），宽度60，高度20
        g.setFont(new Font("Times New Roman",Font.PLAIN,32)); 	//设定字体格式
        g.setColor(getRandomColor(160,200));
        for(int i=0;i<130;i++){                     //产生130条随机干扰线
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);          	//在图象的坐标（x,y）和坐标（x+x1,y+y1）之间画干扰线
        }
        BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        Graphics2D g2d = (Graphics2D) g;	 	//通过Graphics类的对象创建一个Graphics2D类的对象
        g2d.setStroke(bs);					//改变线条的粗细
        g.setColor(Color.GRAY);				//设置当前颜色为预定义颜色中的灰色
        int lineNumber=8;					//指定端点的个数
        int[] xPoints=new int[lineNumber]; 		//定义保存x轴坐标的数组
        int[] yPoints=new int[lineNumber]; 	 	//定义保存x轴坐标的数组
        //通过循环为x轴坐标和y轴坐标的数组赋值
        for(int j=0;j<lineNumber;j++){
            xPoints[j]=random.nextInt(width - 1);
            yPoints[j]=random.nextInt(height - 1);
        }	  						//绘制折线
        g.drawPolyline(xPoints, yPoints,lineNumber);

        String strCode="";
        for (int i=0;i<4;i++){
            String strNumber=String.valueOf(random.nextInt(10)); //把随机数转换成String字符串
            strCode=strCode+strNumber;
            //设置字体的颜色
            g.setColor(new Color(15+random.nextInt(120),15+random.nextInt(120),15+random.nextInt(120)));
            g.drawString(strNumber,30*i+6,30);       //将验证码依次画到图像上,坐标（x=13*i+6,y=16）
        }
        request.getSession().setAttribute("Code",strCode);       	//把验证码保存到Session中
        g.dispose();  //释放此图像的上下文以及它使用的所有系统资源
        ImageIO.write(image, "JPEG", response.getOutputStream()); 	//输出JPEG格式的图像
        response.getOutputStream().flush();                     	//刷新输出流
        response.getOutputStream().close();                    		//关闭输出流

    }
    @PostMapping(value = "/validateCode")
    public  void  validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session =request.getSession();
        String reg_code= (String) session.getAttribute("Code");
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String res_name=request.getParameter("res_name");
        PrintWriter out=response.getWriter();

        if(res_name.equals(reg_code)){
            out.print("");

        }else{
            out.print("验证码错误");

        }

    }
    @GetMapping("/notRole")
    @ResponseBody
    String notRole(){
        return "notRole";
    }
}
