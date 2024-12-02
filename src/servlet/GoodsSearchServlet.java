package servlet;

import model.Page;
import service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet(name = "Goods_search", urlPatterns = "/goods_search")
public class GoodsSearchServlet extends HttpServlet {
    private GoodsService gService = new GoodsService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取搜索框中输入的关键字
        String keyword = request.getParameter("keyword");
        //默认pageNumber为1,如获取到的pageNumber不为空，则该pageNumber赋值
        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            try {
                pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            } catch (Exception e) {
            }
        }
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        //根据id获取商品，并对获取到的商品集合进行分页
        Page p = null;
        try {
            p = gService.getSearchGoodsPage(keyword, pageNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(p.getTotalPage()==0){
            p.setTotalPage(1);
            p.setPageNumber(1);
        }else{//根据查询到的商品数量进行分页
            if(pageNumber>=p.getTotalPage()+1){
                try {
                    p=gService.getSearchGoodsPage(keyword,pageNumber);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        request.setAttribute("p",p);
        request.setAttribute("keyword", URLEncoder.encode(keyword,"utf-8"));
        request.getRequestDispatcher("/goods_search.jsp").forward(request,response);
    }
}
