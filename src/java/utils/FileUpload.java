package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class FileUpload implements Serializable {

    private Part fichero;
    private String nombre;

    public FileUpload(Part fichero, String nombre) {
        this.fichero = fichero;
        this.nombre = nombre;
    }

    public FileUpload() {
        nombre = "";
        fichero = null;
    }

    /**
     * Sube un fichero al servidor
     *
     * @param file
     * @return devolvemos cadena vacia para no redirigir a otra pagina
     * @throws IOException errir lectura/escritura
     */
    public boolean upload(Part file) throws IOException {
        String img = getFilename(file);
        if (img.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)")) {
            System.out.println("TIPO DE IMAGEN CORRECTA ");
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = facesContext.getExternalContext();
            //Stream de entrada
            InputStream inputStream = file.getInputStream();
            //Fichero de salida
            FileOutputStream outputStream;
            //construimos el fichero en la ruta del servidor /resources/images/
            outputStream = new FileOutputStream(externalContext.getRealPath("/") + "resources/images/" + getFilename(file));

            //Leemos el buffer de entrada y escribimos en nustro fichero.
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (true) {
                bytesRead = inputStream.read(buffer);
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                } else {
                    break;
                }
            }
            outputStream.close();
            inputStream.close();
            setNombre(getFilename(file));
            return true;
        } else {
            System.out.println("TIPO DE IMAGEN INCORRECTA ");
            return false;
        }
    }

    /**
     * Sube un fichero al servidor
     *
     * @param n nombre del fichero
     * @return devolvemos cadena vacia para no redirigir a otra pagina
     * @throws IOException errir lectura/escritura
     */
    public String upload(String n) throws IOException {
        String extension = "";

        int i = getFilename(fichero).lastIndexOf('.');
        if (i > 0) {
            extension = getFilename(fichero).substring(i + 1);
        }

        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        //Stream de entrada
        InputStream inputStream = fichero.getInputStream();
        //Fichero de salida
        FileOutputStream outputStream;
        //construimos el fichero en la ruta del servidor /resources/images/
        outputStream = new FileOutputStream(externalContext.getRealPath("/") + "resources/images/" + n + "." + extension);

        //Leemos el buffer de entrada y escribimos en nustro fichero.
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        setNombre(n);

        return "";
    }

    /**
     *
     * @param part
     * @return
     */
   public String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public Part getFichero() {
        return fichero;
    }

    public void setFichero(Part fichero) {
        this.fichero = fichero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public void validaImg(FacesContext context, UIComponent inputDni, Object value) {
//        String img = (String) value;
//        if (!img.matches("\"/(\\.|\\/)(gif|jpe?g|png)$/\"")) {
//            FacesMessage message = new FacesMessage("Tipo de imagen inválido");
//            throw new ValidatorException(message);
//        }
//    }

}
