package com.bidServ.bean;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;

import java.io.FileInputStream;
import java.io.InputStream;

public class AttachmentHelper {
    public AttachmentHelper() {
        super();
    }

    public static String uploadAttachment(String type, String id, String name, InputStream in) {
        String result = null;
        DbxRequestConfig config = new DbxRequestConfig("BidServ/1.0");
        DbxClientV1 client =
            new DbxClientV1(config, "xYfhbVqkGDYAAAAAAAAAo1lxw1_cjN2KUw42z1kGSyl56Bnugy05DQBXA4q8EdTu");

        try {
            String path = null;
            if("POST".equals(type)){
                path = "/post/"+id+"/"+name;
            }else if("BID".equals(type)){
                path = "/bid/"+id+"/"+name;
            }else if ("USER".equals(type)){
                path = "/user/"+id+"/"+name;
            }else if ("COMPANY".equals(type)){
                path = "/company/"+id+"/"+name;
            }
            DbxEntry.File uploadedFile =
                client.uploadFile(path, DbxWriteMode.add(), in.available(), in);
            result = client.createShareableUrl(uploadedFile.path);
            System.out.println("========" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void test(){
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
                DbxClientV1 client = new DbxClientV1(config, "xYfhbVqkGDYAAAAAAAAAo1lxw1_cjN2KUw42z1kGSyl56Bnugy05DQBXA4q8EdTu");
        
            try (InputStream in = new FileInputStream("D:\\MouseOverInfo.png")) {
                        //FileMetadata metadata = 
                      DbxEntry.File uploadedFile =      client.uploadFile("/post/MouseOverInfo.png",DbxWriteMode.add(),in.available(), in );
              //             .uploadAndFinish(in);
              //  metadata.getPathDisplay();
               String url =  client.createShareableUrl(uploadedFile.path);
                System.out.println("========"+url);
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
