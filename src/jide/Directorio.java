/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author juanp
 */
public class Directorio {

    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos jac", "jac");
    JFileChooser selecFile = new JFileChooser();
    File file;
    String[] options = new String[]{"Guardar-continuar", "Descartar"};

    public String getTextFile(File file) {
        //Inicializa una cadena de texto vacía.
        String text = "";
        try {
            //Crea un objeto BufferedReader para leer el archivo. Se usa InputStreamReader para especificar la codificación UTF-8.
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            //Lee cada carácter del archivo uno por uno.
            while (true) {
                int b = entrada.read();
                //Si el valor de retorno es -1, se ha llegado al final del archivo y se sale del bucle.
                if (b != -1) //Si el valor de retorno no es -1, se concatena el carácter a la cadena de texto.
                {
                    text += (char) b;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            //Si el archivo no se encuentra, se imprime un mensaje de error y se devuelve null.
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
            return null;
        } catch (IOException ex) {
            //Si hay un error al leer el archivo, se imprime un mensaje de error y se devuelve null.
            System.out.println("Error al leer el archivo... " + ex.getMessage());
            return null;
        }
        //Se devuelve la cadena de texto que contiene el contenido del archivo.
        return text;
    }

    public boolean saveFile(File archivo, String text) {
        try {
            //Crea un objeto FileOutputStream para escribir en el archivo especificado.
            FileOutputStream output = new FileOutputStream(archivo);
            //Convierte la cadena de texto en un arreglo de bytes utilizando el método getBytes() de la clase String.
            byte[] bytesText = text.getBytes();
            //Escribe los bytes en el archivo utilizando el método write() del objeto FileOutputStream.
            output.write(bytesText);
        } catch (FileNotFoundException ex) {
            //Si el archivo no se encuentra, se imprime un mensaje de error y se devuelve false.
            System.out.println("Error de fileNotFoundException... " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            //Si hay un error al escribir en el archivo, se imprime un mensaje de error y se devuelve false.
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
            return false;
        }
        //Si el archivo se guarda correctamente, se devuelve true.
        return true;
    }

    public boolean guardarEditNuevo(File fileG, JFileChooser selecFileG, ide compF) {
        int x;
        // Verifica si el título del archivo actual es igual a "[#JACIDE]*".
        // Si es así, entonces no se ha guardado el archivo, de lo contrario se pregunta al usuario si desea guardar los cambios.
        if (compF.getTitle().equals("[#JACIDE]*")) {
            x = 0;
        } else {
            x = JOptionPane.showOptionDialog(compF, "El archivo actual está siendo editado, ¿desea guardar los cambios?",
                    "¿Descartar edición?", -1, 3, null, options, options[0]);
        }
        if (x == 0) {
            // Si el usuario elige guardar los cambios y el usuario ha seleccionado un archivo en el objeto JFileChooser,
            // entonces guarda el archivo en el objeto File especificado utilizando el método saveFile.
            if (selecFileG.getSelectedFile() != null) {
                boolean save = saveFile(fileG, compF.jTPCodigo.getText());
                if (save) {
                    compF.setTitle(fileG.getName());
                }

            } // Si no hay ningún archivo seleccionado en el objeto JFileChooser y el título del archivo actual es igual a "[#JACIDE]*",
            // entonces se pregunta al usuario si desea guardar el archivo.
            else if (compF.getTitle().equals("[#JACIDE]*")) {
                int y = JOptionPane.showOptionDialog(compF, "¿Desea guardar el archivo actual?",
                        "¿Descartar edición de archivo nuevo?", -1, 3, null, options, options[0]);
                if (y == 0) {
                    // Si el usuario elige guardar el archivo y el usuario ha seleccionado un archivo en el objeto JFileChooser,
                    // entonces guarda el archivo en el objeto File especificado utilizando el método guardarArch.
                    if (selecFileG.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION) {
                        fileG = selecFileG.getSelectedFile();
                        String fileGname = fileG.getName();

                        if (fileGname.endsWith(".jac")) {
                            if (!fileGname.split("[.]")[0].replace(" ", "").equals("")) {
                                if (!fileG.exists()) {
                                    guardarArch(fileG, compF);
                                } else {
                                    int z = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                                            + "sobreescribirlo?", "Sobreescribir archivo", 2);
                                    if (z == 0) {
                                        guardarArch(fileG, compF);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                                        "Nombre inválido", 2);
                                return false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(compF, "El archivo debe de tener la extensión '.jac'",
                                    "Extensión inválida", 2);
                            return false;
                        }
                    }
                } else {
                    return true;
                }
            } // Si el usuario elige guardar los cambios y el usuario no ha seleccionado un archivo en el objeto JFileChooser,
            // entonces se pregunta al usuario si desea sobrescribir el archivo existente.
            else {
                int z = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                        + "sobreescribirlo?", "Sobreescribir archivo", 2);
                if (z == 0) {
                    guardarArch(fileG, compF);
                }
            }

        }
        return true;
    }

    public boolean guardarEditAbrir(File fileG, JFileChooser selecFileG, ide compF) {
        int x;
        if (compF.getTitle().equals("[#JACIDE]*")) {
            x = 0;
        } else {
            x = JOptionPane.showOptionDialog(compF, "El archivo actual está siendo editado, ¿desea guardar los cambios?",
                    "¿Descartar edición?", -1, 3, null, options, options[0]);
        }
        if (x == 0) {
            if (selecFileG.getSelectedFile() != null) {
                boolean save = saveFile(fileG, compF.jTPCodigo.getText());
                if (save) {
                    compF.setTitle(fileG.getName());
                }
            } else if (compF.getTitle().equals("[#JACIDE]*")) {
                int y = JOptionPane.showOptionDialog(compF, "¿Desea guardar el archivo actual?",
                        "¿Descartar edición de archivo nuevo?", -1, 3, null, options, options[0]);
                if (y == 0) {
                    if (selecFileG.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION) {
                        fileG = selecFileG.getSelectedFile();
                        String fileGname = fileG.getName();
                        if (fileGname.endsWith(".jac")) {
                            if (!fileGname.split("[.]")[0].replace(" ", "").equals("")) {
                                if (!fileG.exists()) {
                                    guardarArch(fileG, compF);
                                } else {
                                    int z = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                                            + "sobreescribirlo?", "Sobreescribir archivo", 2);
                                    if (z == 0) {
                                        guardarArch(fileG, compF);
                                    } else {
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                                        "Nombre inválido", 2);
                                return false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(compF, "El archivo debe de tener la extensión '.jac'",
                                    "Extensión inválida", 2);
                            return false;
                        }
                    }
                } else {
                    compF.jTPCodigo.setText("");
                    compF.setTitle("[#JACIDE]");
                }
            } else {
                int z = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                        + "sobreescribirlo?", "Sobreescribir archivo", 2);
                if (z == 0) {
                    guardarArch(fileG, compF);
                }
            }
        } else {
            compF.jTPCodigo.setText("");
            compF.setTitle("[#JACIDE]");
        }
        return true;
    }

    public void guardarArch(File file, ide compF) {
        boolean save = saveFile(file, compF.jTPCodigo.getText());
        //Esta línea llama al método saveFile (no se muestra aquí) para guardar el contenido del archivo en compF.jTPCodigo.getText(). El resultado se guarda en una variable booleana llamada save.
        if (save) {
            compF.setTitle(file.getName());
        } //Si save es verdadero, entonces el título de la ventana principal se establece en el nombre del archivo guardado.
        else {
            JOptionPane.showMessageDialog(compF, "No se pudo guardar el archivo",
                    "Error desconocido", 2);
        }
        //Si save es falso, se muestra un cuadro de diálogo con un mensaje de error indicando que no se pudo guardar el archivo.
    }

    public void Nuevo(ide compF) {
        file = selecFile.getSelectedFile();
        // Esta línea asigna a la variable "file" el archivo seleccionado por el usuario a través de un objeto "JFileChooser" llamado "selecFile".
        if (compF.getTitle().contains("*")) { //Esta línea verifica si el título de la ventana del editor de texto contiene un asterisco "*", lo que indica que el archivo actual se ha editado pero no se ha guardado.
            if (guardarEditNuevo(file, selecFile, compF)) {//Si el archivo actual se ha editado y no se ha guardado, se llama a un método "guardarEditNuevo" que intenta guardar el archivo y 
//                devuelve un valor booleano que indica si la operación de guardado fue exitosa o no.
                compF.setTitle("[#JACIDE]");//Si el archivo actual se ha guardado o no se ha editado, la ventana del editor de texto se restablece a su título predeterminado "[#JACIDE]".
                compF.jTPCodigo.setText("");//Esta línea limpia el área de texto del editor de texto.
                selecFile = new JFileChooser();//Esta línea crea un nuevo objeto "JFileChooser" para permitir al usuario seleccionar un nuevo archivo.
                file = null;//Esta línea establece la variable "file" en "null" para indicar que no hay ningún archivo abierto actualmente en la aplicación.
            }
        } else {
            compF.setTitle("[#JACIDE]");
            compF.jTPCodigo.setText("");
            selecFile = new JFileChooser();
            file = null;
        }
    }

    public boolean Abrir(ide compF) {
        if (compF.getTitle().contains("*")) {
            if (guardarEditAbrir(file, selecFile, compF)) {
                selecFile = new JFileChooser();
                file = null;
            }
        }
        /*Si el título del objeto compF contiene un asterisco (*), significa que hay cambios sin guardar en el archivo actualmente abierto,
        entonces se pregunta si se desea guardar los cambios con el método guardarEditAbrir(), que recibe como parámetros los objetos file, 
        selecFile y compF. Si se decide guardar los cambios, se crea una nueva instancia de JFileChooser y se establece la variable file en null. */

        JFileChooser tSelecFile = new JFileChooser();
        tSelecFile.setFileFilter(filter);

        File tFile;
        if (tSelecFile.showDialog(compF, "Abrir") == JFileChooser.APPROVE_OPTION) {
            tFile = tSelecFile.getSelectedFile();
            String filename = tFile.getName();

            if (filename.endsWith(".jac")) {
                if (!filename.split("[.]")[0].replace(" ", "").equals("")) {
                    if (!tFile.exists()) {
                        JOptionPane.showMessageDialog(compF, "El archivo que sea desea abrir no existe en el directorio especificado",
                                "Archivo no encontrado", 2);
                    } else {
                        String t = getTextFile(tFile);
                        if (t != null) {
                            compF.jTPCodigo.setText(t);
                            compF.setTitle(tFile.getName());
                            compF.clearAllComp();
                            selecFile = tSelecFile;
                            file = tFile;
                        } else {
                            JOptionPane.showMessageDialog(compF, "Error al leer el archivo",
                                    "Error desconocido", 2);
                            return false;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                            "Nombre inválido", 2);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(compF, "El archivo debe de tener la extensión '.jac'",
                        "Extensión inválida", 2);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /*Se crea una nueva instancia de JFileChooser en tSelecFile. Si se muestra el diálogo de abrir archivo y se selecciona un archivo,
    entonces se obtiene el archivo seleccionado con getSelectedFile(), se guarda en la variable tFile y se obtiene su nombre con getName()
    .Se verifica si el archivo tiene extensión ".jac" y si el nombre del archivo (sin la extensión) no es vacío. Si el archivo no existe, 
    se muestra un mensaje de error, de lo contrario se lee el archivo con el método getTextFile(). Si se pudo leer el archivo correctamente,
    se establece el texto del objeto jTPCodigo del objeto compF con el contenido del archivo, se establece el título del objeto compF con el 
    nombre del archivo y se limpian todos los componentes de la interfaz con clearAllComp(). Luego se establece selecFile en tSelecFile y file
    en tFile. Si no se cumple alguna de las verificaciones anteriores, se muestra un mensaje de error y se retorna false. Si no se selecciona 
    ningún archivo, también se retorna false.*/

    public boolean Importar(ide compF) {

        if (compF.getTitle().contains("*")) {
            if (guardarEditAbrir(file, selecFile, compF)) {
                selecFile = new JFileChooser();
                file = null;
            }
        }
        JFileChooser tSelecFile = new JFileChooser();
        File tFile;
        if (tSelecFile.showDialog(compF, "Abrir") == JFileChooser.APPROVE_OPTION) {
            tFile = tSelecFile.getSelectedFile();
            String filename = tFile.getName();
            if (!filename.split("[.]")[0].replace(" ", "").equals("")) {
                if (!tFile.exists()) {
                    JOptionPane.showMessageDialog(compF, "El archivo que sea desea abrir no existe en el directorio especificado",
                            "Archivo no encontrado", 2);
                } else {
                    String t = getTextFile(tFile);

                    if (t != null) {
                        compF.jTPCodigo.setText(t);
                        compF.setTitle(tFile.getName());
                        compF.clearAllComp();
                        selecFile = tSelecFile;
                        file = tFile;
                    } else {
                        JOptionPane.showMessageDialog(compF, "Error al leer el archivo",
                                "Error desconocido", 2);
                        return false;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                        "Nombre inválido", 2);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean Guardar(ide compF) {
        if (file != null) {
            guardarArch(file, compF);
        } else {
            JFileChooser tSelecFile = new JFileChooser();
            tSelecFile.setFileFilter(filter);
            File tFile;
            /*Si la variable "file" es diferente de "null", se llama al método "guardarArch" con "file" y "compF" como parámetros. 
            Esto significa que el archivo ya ha sido guardado previamente y se actualizará con los cambios realizados. Si la variable 
            "file" es igual a "null", significa que el archivo aún no ha sido guardado, por lo que se crea un nuevo objeto "JFileChooser"
            y se muestra el cuadro de diálogo "Guardar". Si el usuario selecciona un archivo y hace clic en "Guardar", se crea un objeto 
            "File" y se almacena en la variable "tFile".*/

            if (tSelecFile.showDialog(compF, "Guardar") == JFileChooser.APPROVE_OPTION) {
                tFile = tSelecFile.getSelectedFile();
                String filename = tFile.getName();

                if (filename.endsWith(".jac")) {
                    if (!filename.split("[.]")[0].replace(" ", "").equals("")) {
                        if (!tFile.exists()) {
                            guardarArch(tFile, compF);
                            file = tFile;
                            selecFile = tSelecFile;
                            /*Se obtiene el nombre del archivo y se comprueba que termine con la extensión ".jac". Si 
                            es así, se verifica que el nombre del archivo no esté vacío y que no exista ningún archivo 
                            en el directorio con el mismo nombre. Si se cumple, se llama al método "guardarArch" con "tFile" y "compF" como parámetros.*/
                        } else {
                            int x = JOptionPane.showConfirmDialog(compF, "Ya hay un archivo con este nombre, ¿desea "
                                    + "sobreescribirlo?", "Sobreescribir archivo", 2);
                            if (x == 0) {
                                guardarArch(tFile, compF);
                                file = tFile;
                                selecFile = tSelecFile;
                                /*Si el archivo ya existe en el directorio, se muestra un cuadro de diálogo que pregunta 
                                si se desea sobrescribir el archivo. Si el usuario hace clic en "Sí", se llama al método
                                "guardarArch" con "tFile" y "compF" como parámetros.*/
                            } else {
                                selecFile = new JFileChooser();
                                file = null;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                                "Nombre inválido", 2);
                        selecFile = new JFileChooser();
                        file = null;
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(compF, "El archivo debe de tener la extensión '.jac'",
                            "Extensión inválida", 2);
                    selecFile = new JFileChooser();
                    file = null;
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void guardarC(ide compF) {
        JFileChooser tSelecFile = new JFileChooser();
        //Se define un objeto JFileChooser para mostrar el cuadro de diálogo de selección de archivo
        tSelecFile.setFileFilter(filter);
        if (tSelecFile.showDialog(compF, "Guardar como") == JFileChooser.APPROVE_OPTION) {
            //Se muestra el cuadro de diálogo de selección de archivo y se verifica que el usuario haya seleccionado la opción "Guardar como":
            File tFile;
            tFile = tSelecFile.getSelectedFile();
            //Se obtiene el archivo seleccionado por el usuario:
            String filename = tFile.getName();

            if (filename.endsWith(".jac")) {
                if (!filename.split("[.]")[0].replace(" ", "").equals("")) {
                    //Se verifica que el nombre del archivo termine con la extensión ".jac" y que el nombre no esté compuesto solo por espacios en blanco:
                    guardarArch(tFile, compF);
                    file = tFile;
                    selecFile = tSelecFile;
                    //Si el nombre del archivo es válido, se llama al método guardarArch para guardar el contenido del editor en el archivo seleccionado y se actualiza el valor de las variables file y selecFile:
                } else {
                    JOptionPane.showMessageDialog(compF, "Escriba un nombre válido para el archivo",
                            "Nombre inválido", 2);
                }
            } else {
                JOptionPane.showMessageDialog(compF, "El archivo debe de tener la extensión '.jac'",
                        "Extensión inválida", 2);
            }
        }
    }

}
