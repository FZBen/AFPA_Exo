/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appli01;

import ObjetsSimples.*;
import exceptions.DataExceptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import pwdUtils.PwdUtils;
import xchange.Xchange;

/**
 *
 * @author cda201
 */
public class appli02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String salt1 = PwdUtils.generateSalt(64).get();
        //System.out.println(salt1);
        
        String salt = "4rdLBuq6qflPehwAz49FpoaD4fRKoZmxYpjfKku3mTb1PgJfIJ3tN5AzieCnFVc82IIP6UbQ1imSshTkQD+Dmcuq1hNLpdlUJ07TzJtcVABktPeHsGB5liM9PMyXmFYhRY0T2FwDPs9csWz5AKYA6NS3UQjxB4yqyozVN9wdXnl0fcWrqFSo8MOsRgCoPAIX0cO/Id8jC2LI4lZZEkpNsU2lKWZ8a1ChUEMtke3jhaPBArO/iEg/K3YNKBIPph+q+oFPonM4hLA1WYnOC+ImZf9lriYVtckNKEVCJ9vYv7Y5FkyJ0K58WcDTrh8g112PJqpejypGynoXLOzJVODknCyaQylQnYfcTqNILeZbhVtnrgM9AkudlmVH89CfyC22SagV1nX5adetR0U/l0GoScYXT5vitOBe08wb8QFTUJnQjDd7EiA/o0BqvTDxN6h9XYpLz+Ip8Ei7gU8V1Rn1xTp3fH9HmPT5TKCJZB3I1grmuzOhG5Ww258n7Z2WR8BFm+vTZT/38vQcDVNpjGaxSkeZ9Jf3QHIo58/fU4IDg0DaXr1QmLjGVljTpUSWFqBynx+M6jk/rxtgq1ZHojX1eQ+S3uQz4ZbWbQhj3sl0oO6cSBhL0g4MgPgoodrMKs6fIoeoR6Kq50xmURVQ+deXHccVYAPhQk3CJEHQkMp4oiI=";
//        
        String password = "password01";
        String key = PwdUtils.hashPassword(password, salt).get();
//        
//        System.out.println("password01 : " +key + " nbChar : " + key.length());
//        
//        password = "password02";
//        key = PwdUtils.hashPassword(password, salt).get();
//        
//        System.out.println("password02 : " +key+ " nbChar : " + key.length());
//        
//        password = "password03";
//        key = PwdUtils.hashPassword(password, salt).get();
////        
//        System.out.println("password03 : " +key+ " nbChar : " + key.length());
        
        boolean pwdOK = PwdUtils.verifyPassword("password01", key, salt);
        System.out.println(pwdOK);
 
    }
    
}
