/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.test;

import edu.eci.pdsw.examples.beans.impl.*;
import edu.eci.pdsw.examples.beans.impl.CalculadorCuentaConIva;
import edu.eci.pdsw.examples.model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import verificador.impl.VerificadorEstandar;

/**
 * Diseño de pruebas:   
 * 
 * Clases de equivalencia:
 *  
 *      CE1: Comidas que ya traen el iva del 19% en el precio, además sin propina.
 *      Resultado esperado: Sumatoria de los precios de las comidas que ya traen el iva y sin propina.
 *          
 *      CE2: Comidas que ya traen el iva del 19% en su valor más la propina del 10% si el valor total supera los 15.000 pesos.
 *           Resultado esperado: Sumatoria del valor de las comidas más el 10% del valor si el precio supera los 15.000 pesos.
 *      
 *      CE3: Comidas que ya traen el iva del 19% en su valor sin la propina del 10% si el valor  no total supera los 15.000 pesos.
 *           Resultado esperado: Sumatoria del valor de las comidas si el precio supera los 15.000 pesos.
 * 
 *      CE4: Comidas a las que se le aplicará el iva del 19% (Sin bebidas azucaradas)
 *           Resultado esperado: Sumatoria del valor de las comidas más el iva del 19% sobre el  valor de cada plato.
 *      
 *      CE5: Comidas a las que se le aplicará el iva del 19% má bebidas a las que se le aplicará el iva del 29%.
 *           Resultado esperado: Sumatoria del valor de las comidas más el iva del 19% sobre cada plato más la 
 *                               sumatoria del valor de las bebiddas más el iva del 29% sobre cada bebida.
 * 
 * Condiciones de frontera:
 * 
 *      CF1: Cuentas debajo de 15.000 pesos en CalculadorCuentaConPropina.
 *           Clases de equivalencia relacionadas: CE2.
 *           Resultado esperado: Sumatoria del precio de las comidas sin propina.
 * 
 *      CF2: Cuentas encima de 15.000 pesos en CalculadorCuentaConPropina.
 *           Clases de equivalencia relacionadas: CE2.
 *           Resultado esperado: Sumatoria del precio de las comidas más 10% de propina.
 *      
 *      CF3: Cuentas encima de 15.000 pesos en CalculadorBasicoCuentas.
 *           Clases de equivalencia relacionadas: CE1 y CE2.
 *           Resultado esperado: Sumatoria del precio de las comidas sin propinas ni valores adicionales.
 *      
 *      CF4: Cuentas encima de 15.000 pesos en CalculadorCuentaConIva + VerificadorIVAEstandar.
 *           Clases de equivalencia relacionadas: CE3 y CE5.
 *           Resultado esperado: Sumatoria del precio de las comidas más 10% de propina...
 * 
 */
public class CalculadorCuentasTest {
    
    static CalculadorBasicoCuentas Basic;
    static ItemOrden plato1;
    static ItemOrden plato2;
    static ItemOrden bebida1conC;
    static ItemOrden bebida2sinC;
    static Orden ordenSinC;
    static Orden ordenConC;
    static Orden ordenCara;
    
    
    @Before
    public void setUp() {
        //Creación de ordenes, platos y bebidas
        ordenSinC= new Orden();
        ordenConC= new Orden();
        ordenCara= new Orden();
        Basic= new CalculadorBasicoCuentas();
        plato1=new Plato("Arroz",5000);
        plato2=new Plato("Carne",6000);
        bebida1conC= new Bebida("Gaseosa",2000,1500);
        bebida2sinC= new Bebida("Jugo", 3000, 600);
        
        //Agregando comidas y bebidas a Orden Sin Calorias $14000
        ordenSinC.agregarItemOrden(plato1);
        ordenSinC.agregarItemOrden(plato2);
        ordenSinC.agregarItemOrden(bebida2sinC);
        
        //Agregando comidas y bebidas a Orden Con Calorias $13000
        ordenConC.agregarItemOrden(plato1);
        ordenConC.agregarItemOrden(plato2);
        ordenConC.agregarItemOrden(bebida1conC);
        
        //Agregando comidas y bebidas a Orden Con Calorias $16000
        ordenCara.agregarItemOrden(plato1);
        ordenCara.agregarItemOrden(plato2);
        ordenCara.agregarItemOrden(bebida1conC);
        ordenCara.agregarItemOrden(bebida2sinC);
    }
    
    //Prueba CE1
    @Test
    public void cuentaConIvaSinPropinaCE1(){      
        CalculadorBasicoCuentas calcB= new CalculadorBasicoCuentas();
        assertTrue("Fallo prueba Cuenta con iva sin propina",calcB.calcularCosto(ordenSinC)==14000);       
        }
    
    //Prueba CE2
    @Test
    public void cuentaConIvaConPropinaCE2(){      
        CalculadorCuentaConPropina calcP= new CalculadorCuentaConPropina();
        assertTrue("Fallo prueba Cuenta con iva con propina",calcP.calcularCosto(ordenCara)==17600);       
        }
    
    //Prueba CE3
    @Test
    public void cuentaConIvaSinPropinaCE3(){      
        CalculadorCuentaConPropina calcP= new CalculadorCuentaConPropina();
        assertTrue("Fallo prueba Cuenta con iva sin propina CE3",calcP.calcularCosto(ordenConC)==13000);       
        } 
    
    //Prueba CE4
    @Test
    public void cuentaConIvaSinAzucarCE4(){      
        CalculadorCuentaConIva calcI= new CalculadorCuentaConIva();       
        assertTrue("Fallo prueba Cuenta con iva sin azucar CE4",calcI.calcularCosto(ordenSinC)==14000);   
    }
               
    //Prueba CE5.
    @Test
    public void cuentaConIvaConAzucarCE5(){      
        CalculadorCuentaConIva calcI= new CalculadorCuentaConIva();
        assertTrue("Fallo prueba Cuenta con iva con azucar CE5",calcI.calcularCosto(ordenConC)==13000);       
        }
    
}
