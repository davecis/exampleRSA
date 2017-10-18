package util;

import java.math.BigInteger;
import java.util.*;
import java.io.*;


public class RSA {

    int tamPrimo;
    BigInteger n, q, p;
    BigInteger fi;
    BigInteger k, in, ta;
    //n y d llave publica 
    //n y e llave privada
    BigInteger e, d;

   
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
    }
    
    public void generaPrimos()
    {
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0);
    }
    
    public void generaClaves()
    {
        // n = p * q
        n = p.multiply(q);
        
        
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        do e = new BigInteger(2 * tamPrimo, new Random());
            while((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
        
        d = e.modInverse(fi);
    }
    
    public void factorOpacidad(){
        do k = new BigInteger(tamPrimo, new Random());
            while(k.gcd(n).compareTo(BigInteger.valueOf(1)) != 0);
        System.err.println("factor de enmascaramiento: " + k);
        in = k.modInverse(n);
        System.err.println("Inverso de factor: " + in);
       
    }
    
    
    public BigInteger[] cifrar(String mensaje)
    {
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i=0; i<bigdigitos.length; i++)
            cifrado[i] = bigdigitos[i].modPow(e,n);
        
        return(cifrado);
    }
    
    public BigInteger[] firma(BigInteger[] firma)
    {
        int i;
        BigInteger[] cifrado = new BigInteger[firma.length];
        for(i=0;i<firma.length; i++)
            cifrado[i] = firma[i].modPow(d,n);
        
        BigInteger[] s = new BigInteger[cifrado.length];
        
        for(i=0;i<cifrado.length; i++)
            s[i] = (cifrado[i].multiply(in)).mod(n);
        return(s);
    }
    
    public BigInteger[] firmaCiega (String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        BigInteger[] firma = new BigInteger[bigdigitos.length];
        
        for(i=0; i<bigdigitos.length; i++)
            firma[i] = (bigdigitos[i].multiply(k)).modPow(e,n);
        
        return firma;
    }
    
    
    public String descifrar(BigInteger[] cifrado) {
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        for(int i=0; i<descifrado.length; i++)
            descifrado[i] = cifrado[i].modPow(d,n);
        
        char[] charArray = new char[descifrado.length];
        
        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (descifrado[i].intValue());
        
        return(new String(charArray));
    }
    
    public BigInteger getp() {return(p);}
    public BigInteger getq() {return(q);}
    public BigInteger getfit() {return(fi);}
    public BigInteger getn() {return(n);}
    public BigInteger gete() {return(e);}
    public BigInteger getd() {return(d);}
}