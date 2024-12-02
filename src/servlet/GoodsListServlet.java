package servlet;

import model.Page;
import model.Type;
import service.GoodsService;
import service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Goods_List",urlPatterns = "/goods_list")
public class GoodsListServlet extends HttpServlet {
    private GoodsService gService=new GoodsService();
    private TypeService tService=new TypeService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id=0;
        //若typeid不为空，则获取typeid
        if(request.getParameter("typeid")!=null){
            id=Integer.parseInt(request.getParameter("typeid"));
        }
        //默认pageNumber为1，若获取到的pageNumber不为空，给pageNumber赋值
        int pageNumber=1;
        if(request.getParameter("pageNumber")!=null){
            try{
                pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
            }catch (Exception e){
                e.printStackTrace(); // 打印日志，便于调试
            }
        }
        Type t=null;
        if(id!=0){
            t= tService.selectTypeNameByID(id);
        }
        request.setAttribute("t",t);
        if(pageNumber<=0)
            pageNumber=1;
        //根据id获取商品，并对获取到的商品集合进行分页
        Page p= null;
        try {
            p = gService.selectPageByTypeID(id,pageNumber);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(p.getTotalPage()==0){
            p.setTotalPage(1);
            p.setPageNumber(1);
        }else{//根据查询到的商品数量进行分页
            if(pageNumber>=p.getTotalPage()+1){
                try {
                    p=gService.selectPageByTypeID(id,p.getTotalPage());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        request.setAttribute("p",p);
        request.setAttribute("id",String.valueOf(id));
        request.getRequestDispatcher("/goods_list.jsp").forward(request,response);
    }
}
