/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_liz;

/**
 *
 * @author Josué Galván
 */
public class Procesos {
    int i, j, k;
    
    public double [] obtenerValores_Fxf (int filas_alimentacion, int columnas_alimentacion, String [][] info_alimentacion, int valor_alimentacion) {
        double Fxf [] = new double [filas_alimentacion];
        for (j = 0; j < filas_alimentacion; j++)
            for (i = 0; i < columnas_alimentacion; i++)
                if (i == 1)
                    Fxf [j] = Double.parseDouble(info_alimentacion [j][i]) * valor_alimentacion;
        
        return Fxf;
    }
    
    public double [] obtenerValores_Sxs (int filas_solvente, int columnas_solvente, String [][] info_solvente, int valor_solvente) {
        double Sxs [] = new double [filas_solvente];
        for (j = 0; j < filas_solvente; j++)
            for (i = 0; i < columnas_solvente; i++)
                if (i == 1)
                    Sxs [j] = Double.parseDouble(info_solvente [j][i]) * valor_solvente;
        
        return Sxs;
    }
    
    public double [] obtenerValores_Fxf0 (double [] Fxf, int filas_alimentacion, int num_etapas) {
        double Fxf0 [] = new double [filas_alimentacion];
        for (i = 0; i < filas_alimentacion; i++)
            if (i > 0)
                Fxf0 [i] = Fxf[i] * 0;
            else
                Fxf0 [i] = RedoNumero(Fxf[i]/num_etapas);
                
        return Fxf0;
    }
    
    public double [] obtenerValores_Sxs0 (double [] Sxs, int filas_solvente, int num_etapas) {
        double Sxs0 [] = new double [filas_solvente];
        for (i = 0; i < filas_solvente; i++)
            Sxs0 [i] = Sxs[i] * 1;
                
        return Sxs0;
    }
    
    public double [] obtenerValores_Einter (double [] Fxf0, int num_etapas, int valor_solvente) {
        double E_inter [] = new double [num_etapas];
        for (i = 0; i < num_etapas; i++)
            E_inter [i] = RedoNumero(valor_solvente + (Fxf0[0] * Math.abs(i - num_etapas)));
        
        return E_inter;
    }
    
    public double [][] obtenerValores_XEinter (int num_componentes_alimentacion, int num_componentes_solventes, int num_etapas, double [] E_inter, 
            double [] Sxs0, int valor_solvente) {
        int total_componentes = num_componentes_alimentacion + num_componentes_solventes, k = 0;
        double XE_inter [][] = new double [num_etapas][total_componentes];
        double temp [] = new double [total_componentes - 1];
        
        for (j = 0; j < temp.length; j++)
            if (j >= (temp.length - Sxs0.length)) {
                temp [j] = Sxs0[k];
                k++;
            }
        
        for (i = 0; i < num_etapas; i++)
            for (j = 1; j < total_componentes; j++)
                XE_inter [i][j] = temp [j - 1];
        
        for (i = 0; i < num_etapas; i++)
            for (j = 0; j < total_componentes; j++)
                if (j > 0)
                    XE_inter [i][j] = RedoNumero(XE_inter[i][j] / E_inter[i]);
                else
                    XE_inter [i][j] = RedoNumero((E_inter[i] - valor_solvente) / E_inter[i]);
         
        return XE_inter;
    }
    
    public double [] obtenerValores_Rinter (double [] Fxf0, int num_etapas, int valor_alimentacion) {
        double R_inter [] = new double [num_etapas];
        for (i = 0; i < num_etapas; i++)
            R_inter [i] = RedoNumero(valor_alimentacion - (Fxf0[0] * Math.abs(i + 1)));
        
        return R_inter;
    }
    
    public double [][] obtenerValores_XRinter (int num_componentes_alimentacion, int num_componentes_solventes, int num_etapas, double [] R_inter, 
            double [] Fxf, int valor_alimentacion, double [] Fxf0) {
        int total_componentes = num_componentes_alimentacion + num_componentes_solventes, k = 1;
        double XR_inter [][] = new double [num_etapas][total_componentes];
        double temp [] = new double [total_componentes - 1];
        
        for (j = 0; j < temp.length; j++)
            if (j < Fxf.length - 1) {
                temp [j] = Fxf[k];
                k++;
            }
        
        for (i = 0; i < num_etapas; i++)
            for (j = 1; j < total_componentes; j++)
                XR_inter [i][j] = temp [j - 1];
        
        for (i = 0; i < num_etapas; i++)
            for (j = 0; j < total_componentes; j++)
                if (j < 1)
                    XR_inter [i][j] = RedoNumero((Fxf[0] - (Fxf0[0] * (i + 1))) / R_inter[i]);
                else
                    XR_inter [i][j] = RedoNumero(XR_inter [i][j] / R_inter[i]);
        
        return XR_inter;
    }
    
    public double [][] obtener_Tao (double [][] A, double [][] B, double [][] C, int [] id_componentes, double temperatura_operacion) {
        double Tao [][] = new double[id_componentes.length][id_componentes.length];
        for (i = 0; i < id_componentes.length; i++)
            for (j = 0; j < id_componentes.length; j++)
                if (id_componentes[i] != id_componentes[j])
                    Tao [i][j] = RedoNumero(A[id_componentes[i]][id_componentes[j]] + (B[id_componentes[i]][id_componentes[j]] / temperatura_operacion) - (C[id_componentes[i]][id_componentes[j]] * Math.log(temperatura_operacion)));
        
        return Tao;
    }
    
    public double RedoNumero (double p_valorN) {
        p_valorN *= Math.pow(10, 4);
        p_valorN += 0.5;
        p_valorN = (int) p_valorN;
        p_valorN /= Math.pow(10, 4);
        
        return p_valorN;
    }
    
    public double [][] obtener_G (double [] alfa, double [][] Tao, int total_componentes) {
        double G [][] = new double[total_componentes][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < total_componentes; j++)
                G [i][j] = RedoNumero(Math.exp(alfa [i] * Tao [i][j]));
        
        return G;
    }
    
    public double [][] obtener_GammaR (double [][] Tao, double [][] G, double [][] XR_inter, int total_componentes, int num_etapas) {
        double gammaR [][] = new double [num_etapas][total_componentes];
        double sumatoria1 = 0;
        double sumatoria2 = 0;
        k = 0;
        
        for (i = 0; i < total_componentes; i++) {
            for (j = 0; j < num_etapas; j++) {
                while (k < total_componentes) {
                    sumatoria1 += G [k][i] * XR_inter [j][i] * Tao [k][i];
                    k++;
                }
                k = 0;
                
                while (k < total_componentes) {
                    sumatoria2 += G [k][i] * XR_inter [j][k];
                    k++;
                }
                
                gammaR [j][i] = RedoNumero(sumatoria1 / sumatoria2);
            }
        }
                    
        return gammaR; 
    }
    
    public double [][] obtener_GammaE (double [][] Tao, double [][] G, double [][] XE_inter, int total_componentes, int num_etapas) {
        double gammaE [][] = new double [num_etapas][total_componentes];
        double sumatoria1 = 0;
        double sumatoria2 = 0;
        k = 0;
        
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++) {
                while (k < total_componentes) {
                    sumatoria1 += G [k][i] * XE_inter [j][i] * Tao [k][i];
                    k++;
                }
                k = 0;
                
                while (k < total_componentes) {
                    sumatoria2 += G [k][i] * XE_inter [j][k];
                    k++;
                }
                
                gammaE [j][i] = RedoNumero(sumatoria1 / sumatoria2);
            }
        
        return gammaE; 
    }
    
    public double [][] obtener_Kinter (double [][] gammaE, double [][] gammaR, int total_componentes, int num_etapas) {
        double Kinter [][] = new double [num_etapas][total_componentes];
        for (i = 0; i < Kinter.length; i++)
            for (j = 0; j < Kinter[0].length; j++)
                Kinter [i][j] = RedoNumero(Math.abs(gammaR [i][j] / (gammaE [i][j])));
        
        return Kinter; 
    }
    
    public double [] obtener_Aj (double [] E_inter, int valor_alimentacion, int num_etapas) {
        double [] Aj = new double [num_etapas];
        for (j = 1; j < num_etapas; j++)
            Aj [j] = RedoNumero(E_inter [j] + (valor_alimentacion - E_inter [0]));
        
        return Aj;
    }
    
    public double [][] obtener_Bj (int num_etapas, int total_componentes, double [] Aj, double [][] Kinter, double [] E_inter) {
        double Bj [][] = new double [num_etapas][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j+1 < num_etapas)
                    Bj [j][i] = RedoNumero(-Aj [j+1] - (E_inter [j] * Kinter [j][i]));
                else
                    Bj [j][i] = RedoNumero(-Aj [j] - (E_inter [j] * Kinter [j][i]));
        
        return Bj;
    }
    
    public double [][] obtener_Cj (int num_etapas, int total_componentes, double [] E_inter, double [][] Kinter) {
        double [][] Cj = new double [num_etapas][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j+1 < num_etapas)
                    Cj [j][i] = RedoNumero(E_inter [j+1] * Kinter [j+1][i]);
                else
                    Cj [j][i] = 0;
        
        return Cj;
    }
    
    public double [][] obtener_Dj (int num_etapas, int total_componentes, double [] Fxf, double [] Sxs) {
        double [][] Dj = new double [num_etapas][total_componentes];
        k = 0;
        
        for (i = 0; i < num_etapas; i++)
            for (j = 0; j < total_componentes; j++)
                if (i == 0 && j < Fxf.length)
                    Dj [i][j] = -Fxf [j];
        
        for (i = num_etapas-1; i < num_etapas; i++)
            for (j = Fxf.length; j < total_componentes; j++) {
                Dj [i][j] = -Sxs [k];
                k ++;
            }
        
        return Dj;
    }
    
    public double [][] obtener_Pj (int num_etapas, int total_componentes, double [] Aj, double [][]  Bj, double [][] Cj) {
        double Pj [][] = new double [num_etapas][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j > 0)
                    Pj [j][i] = RedoNumero(Cj [j][i] / (Bj [j][i] - (Aj [j] * Pj [j-1][i])));
                else
                    Pj [j][i] = RedoNumero(Cj [j][i] / Bj [j][i]);
                
        return Pj;
    }
    
    public double [][] obtener_Qj (int num_etapas, int total_componentes, double [] Aj, double [][] Bj, double [][] Dj, double [][] Pj) {
        double Qj [][] = new double [num_etapas][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j > 0)
                    Qj [j][i] = Math.abs(RedoNumero((Dj [j][i] - (Aj[j] * Qj [j-1][i])) / (Bj [j][i] - (Aj [j] * Pj [j-1][i]))));
                else
                    Qj [j][i] = Math.abs(RedoNumero(Dj [j][i] / Bj [j][i]));
        
        return Qj;
    }
    
    public double [][] obtener_XR_reales (int num_etapas, int total_componentes, double [][] Qj, double [][] Pj, double [][] XR_inter) {
        double XR_reales [][] = new double [num_etapas][total_componentes];
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j == XR_reales.length-1)
                    XR_reales [j][i] = RedoNumero(Math.abs(Qj [j][i]));
        
        for (i = 0; i < total_componentes; i++)
            for (j = 0; j < num_etapas; j++)
                if (j+1 < num_etapas)
                    XR_reales [j][i] = RedoNumero(Math.abs((Qj [j][i] - (Pj [j][i] * XR_inter[j+1][i])) + 0.0001));
        
        return XR_reales;
    }
    
    public double [][] obtener_normalizacion (double [][] XR_reales, int num_etapas, int total_componentes) {
        double normalizacion [][] = new double [num_etapas][total_componentes];
        double sumatoriaFila = 0;
        
        for (i = 0; i < num_etapas; i++) {
            for (j = 0; j < total_componentes; j++)
                sumatoriaFila += XR_reales [i][j];
            
            for (k = 0; k < total_componentes; k++)
                normalizacion [i][k] = RedoNumero(XR_reales [i][k] / sumatoriaFila);
            
            sumatoriaFila = 0;
        }
            
        return normalizacion;
    }
    
    public double obtener_CR1 (double [][] XR_inter, double [][] XR_reales) {
        double sumatoriaXR_inter = 0, sumatoriaXR_reales = 0, CR1;
        for (j = 0; j < XR_reales[0].length; j++) {
            for (i = 0; i < XR_reales.length; i++) {
                sumatoriaXR_inter += Math.abs(RedoNumero(XR_inter [i][j]));
                sumatoriaXR_reales += Math.abs(RedoNumero(XR_reales [i][j]));
            }
        }
        
        CR1 = Math.abs(RedoNumero(sumatoriaXR_inter - sumatoriaXR_reales));
        
        return CR1;
    }
    
    public double [][] obtener_equilibrio (double [][] Kinter, double [][] XR_reales_nueva) {
        double equilibrio [][] = new double [Kinter.length][Kinter[0].length];
        for (i = 0; i < equilibrio.length; i++)
            for (j = 0; j < equilibrio[0].length; j++)
                equilibrio [i][j] = RedoNumero(Kinter [i][j] * XR_reales_nueva [i][j]);
        
        return equilibrio;
    }
    
    public double [] suma_de_flujos (double [][] equilibrio, double [] E_inter) {
        double suma_de_flujos [] = new double [E_inter.length];
        double sumatoria = 0;
        for (i = 0; i < equilibrio.length; i++) {
            for (j = 0; j < equilibrio[0].length; j++)
                sumatoria += equilibrio [i][j];
            
            suma_de_flujos [i] = RedoNumero(E_inter[i] * sumatoria);
            sumatoria = 0;
        }
        
        return suma_de_flujos;
    }
    
    public double obtener_CR2 (double [] E_inter, double [] suma_de_flujos) {
        double CR2 = 0;
        for (i = 0; i < E_inter.length; i++)
            CR2 += RedoNumero((suma_de_flujos [i] - E_inter [i]) / suma_de_flujos [i]);
        
        return CR2;
    }
    
    public double [] obtener_Rj (int num_etapas, double [] suma_flujos, int valor_alimentacion, int valor_solvente) {
        double Rj [] = new double [num_etapas];
        for (i = 0; i < Rj.length; i++) {
            if (i == Rj.length-1)
                Rj [i] = RedoNumero(valor_solvente + valor_alimentacion - suma_flujos [0]);
            else
                Rj [i] = RedoNumero(suma_flujos [i+1] + valor_alimentacion + valor_solvente - suma_flujos [0]);
        }
        
        return Rj;
    }
    
    public double [] obtener_calor (int num_etapas) {
        double calor [] = new double [num_etapas];
        for (i = 0; i < calor.length; i++)
            if (i == 0)
                calor [i] = 71061 * -1;
            else
                calor [i] = (calor [i-1] + (71061 / num_etapas)) - 1; 
        
        return calor;
    }
    
    public double [] obtener_Kequilibrio_alimentacion (int filas_alimentacion, double [] barryAllen_alimentacion, double valor_presion_alimentacion) {
        double [] Kequilibrio_alimentacion = new double [filas_alimentacion];
        for (i = 0; i < Kequilibrio_alimentacion.length; i++)
            Kequilibrio_alimentacion [i] = RedoNumero(barryAllen_alimentacion [i] / valor_presion_alimentacion);
        
        return Kequilibrio_alimentacion;
    }
    
    public double [] obtener_Kequilibrio_solvente (int filas_solvente, double [] barryAllen_solvente, double valor_presion_solvente) {
        double [] Kequilibrio_solvente = new double [filas_solvente];
        for (i = 0; i < Kequilibrio_solvente.length; i++)
            Kequilibrio_solvente [i] = RedoNumero(barryAllen_solvente [i] / valor_presion_solvente);
        
        return Kequilibrio_solvente;
    }
    
    public double obtener_fraccionVapor_alimentacion (int filas_alimentacion, double [] Kequilibrio_alimentacion, String [][] info_alimentacion) {
        double fraccionVapor_alimentacion, fraccionVapor_alimentacion2;
        double temp [] = new double [filas_alimentacion];
        double prueba, prueba2, error;
        
        for (j = 0; j < info_alimentacion.length; j++)
            for (i = 0; i < info_alimentacion[0].length; i++)
                if (i == 1)
                    temp [j] = Double.parseDouble(info_alimentacion [j][i]);
        
        j = 0;
        prueba = RedoNumero(Math.random());
        do {
            fraccionVapor_alimentacion = 0;
            fraccionVapor_alimentacion2 = 0;
            for (i = 0; i < temp.length; i++)
                fraccionVapor_alimentacion += RedoNumero((RedoNumero(temp [i] * (1 - Kequilibrio_alimentacion [i]))) / (RedoNumero(1 + (prueba * (Kequilibrio_alimentacion [i] - 1)))));
            
            for (i = 0; i < temp.length; i++)
                fraccionVapor_alimentacion2 += RedoNumero((RedoNumero(temp [i] * Math.pow((1 - Kequilibrio_alimentacion [i]), 2))) / (RedoNumero( Math.pow(1 + (prueba * (Kequilibrio_alimentacion [i] - 1)), 2))));
            
            prueba2 = RedoNumero(prueba - (fraccionVapor_alimentacion / fraccionVapor_alimentacion2));
            error = RedoNumero(Math.abs((prueba - prueba2) / prueba2));
            if (error <= 0.1)
                prueba = prueba2;
        } while (error <= 0.1);
        
        return prueba;
    }
    
    public double obtener_fraccionVapor_solvente (int filas_solvente, double [] Kequilibrio_solvente, String [][] info_solvente) {
        double fraccionVapor_solvente, fraccionVapor_solvente2;
        double temp [] = new double [filas_solvente];
        double prueba, prueba2, error;
        
        for (j = 0; j < info_solvente.length; j++)
            for (i = 0; i < info_solvente[0].length; i++)
                if (i == 1)
                    temp [j] = Double.parseDouble(info_solvente [j][i]);
        
        j = 0;
        prueba = RedoNumero(Math.random());
        do {
            fraccionVapor_solvente = 0;
            fraccionVapor_solvente2 = 0;
            for (i = 0; i < temp.length; i++)
                fraccionVapor_solvente += RedoNumero((RedoNumero(temp [i] * (1 - Kequilibrio_solvente [i]))) / (RedoNumero(1 + (prueba * (Kequilibrio_solvente [i] - 1)))));
            
            for (i = 0; i < temp.length; i++)
                fraccionVapor_solvente2 += RedoNumero((RedoNumero(temp [i] * Math.pow((1 - Kequilibrio_solvente [i]), 2))) / (RedoNumero( Math.pow(1 + (prueba * (Kequilibrio_solvente [i] - 1)), 2))));
            
            prueba2 = RedoNumero(prueba - (fraccionVapor_solvente / fraccionVapor_solvente2));
            error = RedoNumero(Math.abs((prueba - prueba2) / prueba2));
            if (error <= 0.1)
                prueba = prueba2;
        } while (error <= 0.1);
        
        return prueba;
    }
}