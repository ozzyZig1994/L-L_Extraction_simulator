/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_liz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Josué Galván
 */
public class OperacionesBD {
    String linea, linea2[];
    int i, j, k;
    Procesos proceso = new Procesos();
    
    public String [] obtenerComponentes() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/componentesBD.txt"));
        String nombres_componentes [] = new String [10];
        linea = br.readLine();
        i = 0;
        while (linea != null) {
            linea2 = linea.split("\\|");
            nombres_componentes[i] = linea2[0];
            i++;
            linea = br.readLine();
        }
        br.close();
        
        return nombres_componentes;
    }
    
    public int [] obtenerIDComponentes(String [] componentes_elegidos) throws IOException {
        int id_componentes [] = new int [componentes_elegidos.length];
        j = 0;
        for (i = 0; i < componentes_elegidos.length; i++) {
            BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/componentesBD.txt"));
            linea = br.readLine();
            while (linea != null) {
                linea2 = linea.split("\\|");
                if (componentes_elegidos [i].equals(linea2[0]))
                    id_componentes [i] = j;
                else
                    j++;
                linea = br.readLine();
            }
            j = 0;
            br.close();
        }
        
        return id_componentes;
    }
    
    public double [][] obtener_matriz_A () throws FileNotFoundException, IOException {
        double A [][] = new double [10][10];
        BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/base-de-datos-A.txt"));
        linea = br.readLine();
        for (i = 0; i < 10; i++) {
            linea2 = linea.split(" ");
            for (j = 0; j < 10; j++)
                A [i][j] = Double.parseDouble(linea2[j]);
            linea = br.readLine();
        }
        br.close();
            
        return A;
    }
    
    public double [][] obtener_matriz_B () throws FileNotFoundException, IOException {
        double B [][] = new double [10][10];
        BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/base-de-datos-B.txt"));
        linea = br.readLine();
        for (i = 0; i < 10; i++) {
            linea2 = linea.split(" ");
            for (j = 0; j < 10; j++)
                B [i][j] = Double.parseDouble(linea2[j]);
            linea = br.readLine();
        }
        br.close();
            
        return B;
    }
    
    public double [][] obtener_matriz_C () throws FileNotFoundException, IOException {
        double C [][] = new double [10][10];
        BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/base-de-datos-C.txt"));
        linea = br.readLine();
        for (i = 0; i < 10; i++) {
            linea2 = linea.split(" ");
            for (j = 0; j < 10; j++)
                C [i][j] = Double.parseDouble(linea2[j]);
            linea = br.readLine();
        }
        br.close();
            
        return C;
    }
    
    public double [] alfa (String [] componentes_elegidos ) throws FileNotFoundException, IOException {
        double alfa [] = new double [componentes_elegidos.length];
        for (i = 0; i < componentes_elegidos.length; i++) {
            BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/componentesBD.txt"));
            linea = br.readLine();
            while (linea != null) {
                linea2 = linea.split("\\|");
                if (componentes_elegidos [i].equals(linea2 [0]))
                    alfa [i] = Double.parseDouble(linea2 [1]);
                linea = br.readLine();
            }
            br.close();
        }
        
        return alfa;
    }
    
    public double [] barryAllen_alimentacion (int num_etapas, int [] id_componente, int filas_alimentacion, double valor_temperatura_alimentacion, double [] presion_critica) throws FileNotFoundException, IOException {
        double barryAllen_alimentacion [] = new double [filas_alimentacion];
        double temp [] = new double [filas_alimentacion];
        
        for (i = 0; i < temp.length; i++)
            temp [i] = id_componente [i];
        
        j = 0;
        for (i = 0; i < barryAllen_alimentacion.length; i++) {
            BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/antonio.txt"));
            linea = br.readLine();
            while (linea != null) {
                linea2 = linea.split("\\|");
                if (temp [i] == j) {
                    barryAllen_alimentacion [i] = -Double.parseDouble(linea2 [1]);
                    barryAllen_alimentacion [i] /= Double.parseDouble(linea2 [2]) + valor_temperatura_alimentacion;
                    barryAllen_alimentacion [i] += Double.parseDouble(linea2 [0]);
                    barryAllen_alimentacion [i] = proceso.RedoNumero(Math.exp(barryAllen_alimentacion [i]) * presion_critica [j]);
                }
                j++;
                linea = br.readLine();
            }
            j = 0;
            br.close();
        }
        
        return barryAllen_alimentacion;
    }
    
    public double [] barryAllen_solvente (int num_etapas, int [] id_componente, int filas_solvente, double valor_temperatura_solvente, double [] presion_critica) throws FileNotFoundException, IOException {
        double barryAllen_solvente [] = new double [filas_solvente];
        double temp [] = new double [filas_solvente];
        int filas_alimentacion = id_componente.length - filas_solvente;
        
        for (i = 0; i < temp.length; i++)
            temp [i] = id_componente [i + filas_alimentacion];
        
        j = 0;
        for (i = 0; i < barryAllen_solvente.length; i++) {
            BufferedReader br = new BufferedReader(new FileReader("src/proyecto_liz/antonio.txt"));
            linea = br.readLine();
            while (linea != null) {
                linea2 = linea.split("\\|");
                if (temp [i] == j) {
                    barryAllen_solvente [i] = -Double.parseDouble(linea2 [1]);
                    barryAllen_solvente [i] /= Double.parseDouble(linea2 [2]) + valor_temperatura_solvente;
                    barryAllen_solvente [i] += Double.parseDouble(linea2 [0]);
                    barryAllen_solvente [i] = proceso.RedoNumero(Math.exp(barryAllen_solvente [i]) * presion_critica [i]);
                }
                j++;
                linea = br.readLine();
            }
            j = 0;
            br.close();
        }
        
        return barryAllen_solvente;
    }
}
