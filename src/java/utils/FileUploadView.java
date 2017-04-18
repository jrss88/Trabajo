/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.model.UploadedFile;
 
@ManagedBean(name="fileUploadView")
public class FileUploadView {
     
    private UploadedFile file;
    String filePath="resources/images";
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        
        if(file != null) {
            FacesMessage message = new FacesMessage("Correcto", filePath+file.getFileName() + " ha sido subida");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            
        }
        
       
//            if (null!=uploadedPhoto) {
//                bytes = uploadedPhoto.getContents();
//                String filename = FilenameUtils.getName(uploadedPhoto.getFileName());
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath+filename)));
//                stream.write(bytes);
//                stream.close();
//            }
    }
}
