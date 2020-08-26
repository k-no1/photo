package kjh.common.controller;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kjh.asp.product.service.ProductSvc;
import kjh.common.dto.ObjectMap;
import kjh.common.dto.Product;
import kjh.common.dto.ProductSearch;
import kjh.common.util.LogUtils;



@Controller
@RequestMapping(value="/")
public class RootCtl {
    
    private static final Logger logger = LoggerFactory.getLogger(RootCtl.class);
    DecimalFormat fmt                  = new DecimalFormat("0.###");

    @Autowired ProductSvc              productSvc;
    @Value("${spring.staticFileRootPath}") private String staticFileRootPath;
    @Value("${spring.staticFilePath}")     private String staticFilePath;
    @Value("${spring.staticFileTempPath}") private String staticFileTempPath;
    @Value("${operation.mode}")            private String operationMode;
    
    
    /**
        첫 페이지
     * @param request
     * @param response
     * @param session
     * @param search
     * @param ListChkSelection
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/")
    public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response, HttpSession session
                                       ,@ModelAttribute ProductSearch search, @CookieValue("ListChkSelection") Integer ListChkSelection
                                       ) throws Exception  
    {

        ModelAndView mv = new ModelAndView();        
        ObjectMap map   = new ObjectMap();        
        Product model   = new Product();

        try {
            logger.info( "{} start Page", LogUtils.toHeader(request) ) ;
        
        } catch (Exception e) {
            logger.error( "{}", LogUtils.toHeader(request), e ) ;
            //throw new Exception(e.getMessage(),e);
        }

        mv.addObject("search", search);
        mv.setViewName("/index");
        
        return mv;
    }
}
