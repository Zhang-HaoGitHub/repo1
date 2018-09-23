package com.itheima.controller;

import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    /**
     * 查询所有商品信息
     * @param request
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request){
        List<Items> itemsList = itemsService.findAll();
        request.setAttribute("itemsList",itemsList);
        return "itemsList";
    }

    /**
     * 跳转到修改商品界面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editItems.action")
    public String editItems(HttpServletRequest request, int id){
        Items items = itemsService.findById(id);
        request.setAttribute("items",items);
        return "editItems";
    }

    /**
     * 上传图片，修改商品信息
     * @param uploadFile
     * @param request
     * @return
     * @throws Exception
     */
//    @RequestMapping("/updateItems.action")
    public String updateItems(MultipartFile uploadFile, HttpServletRequest request) throws Exception {
        Items items = new Items();
        String idstr = request.getParameter("id");
        int id=Integer.parseInt(idstr);
        items.setId(id);

        String name = request.getParameter("name");
        items.setName(name);

        String pricestr = request.getParameter("price");
        float price =Float.parseFloat(pricestr);
        items.setPrice(price);

        //定义文件名
        String filename="";
        //获取原始文件名
        if(uploadFile!=null) {
            String uploadFilname = uploadFile.getOriginalFilename();
            //截取文件扩展名
            String extendName = uploadFilname.substring(uploadFilname.lastIndexOf(".") + 1, uploadFilname.length());
            //给文件名加上随机数，防止重复
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            //判断是否输入了文件名
            filename=uuid+"_"+uploadFilname;
            //获取文件路径
            ServletContext context = request.getSession().getServletContext();
            String path = context.getRealPath("/images");
            //4.判断路径是否存在
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //5.使用 MulitpartFile 接口中方法，把上传的文件写到指定位置
            uploadFile.transferTo(new File(file, filename));
        }
        items.setPic(filename);

        String createtime = request.getParameter("createtime");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse(createtime);
        items.setCreatetime(createtime);

        String detail = request.getParameter("detail");
        items.setDetail(detail);

        int row = itemsService.updateItems(items);
        if(row>0){
            //修改成功
            //回到商品信息展示页面
            return "redirect:/items/findAll";
        }else{
            //修改失败
            request.setAttribute("items",items);
            return "editItems";
        }
    }

    /**
     * 文件上传
     * @param items
     * @param uploadFile
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateItems.action")
    public ModelAndView fileUpload(Items items, MultipartFile uploadFile, HttpServletRequest request) throws Exception{
        ModelAndView mv=new ModelAndView();
        //判断是否有上传文件
        if(uploadFile!=null) {
            //获取原始文件名
            String uploadFilname = uploadFile.getOriginalFilename();
            //截取文件扩展名
            String extendName = uploadFilname.substring(uploadFilname.lastIndexOf(".") + 1, uploadFilname.length());
            //给文件名加上随机数，防止重复
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            //得到新生成的文件名
            String filename = uuid + "_" + uploadFilname;
            System.out.println(filename);
            //获取文件路径
            ServletContext context = request.getSession().getServletContext();
            String path = context.getRealPath("/images");

            //4.判断路径是否存在
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            //5.使用 MulitpartFile 接口中方法，把上传的文件写到指定位置
            uploadFile.transferTo(new File(file, filename));
            //保存文件名到items对象中
            items.setPic(filename);
        }
        int row = itemsService.updateItems(items);
        if(row>0){
            //修改成功
            List<Items> itemsList = itemsService.findAll();
            mv.addObject("itemsList",itemsList);
            //回到商品信息展示页面
            mv.setViewName("itemsList");
        }else{
            //修改失败
            Items items1 = itemsService.findById(items.getId());
            mv.addObject("items",items1);
            mv.addObject("msg","修改失败");
            mv.setViewName("editItems");
        }
        return mv;
    }
}
