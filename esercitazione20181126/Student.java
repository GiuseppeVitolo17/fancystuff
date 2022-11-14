/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop2018.esercitazione20181126;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Gennaro
 */
public class Student implements Serializable{
    private String nome;
    private String matricola;
    private int esamiSostenuti;
    private double votoMedio;

    public Student(String nome, String matricola, int esamiSostenuti, double votoMedio) {
        this.nome = nome;
        this.matricola = matricola;
        this.esamiSostenuti = esamiSostenuti;
        this.votoMedio = votoMedio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public int getEsamiSostenuti() {
        return esamiSostenuti;
    }

    public void setEsamiSostenuti(int esamiSostenuti) {
        this.esamiSostenuti = esamiSostenuti;
    }

    public double getVotoMedio() {
        return votoMedio;
    }

    public void setVotoMedio(double votoMedio) {
        this.votoMedio = votoMedio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.matricola);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.matricola, other.matricola)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "nome=" + nome + ", matricola=" + matricola + ", esamiSostenuti=" + esamiSostenuti + ", votoMedio=" + votoMedio + '}';
    }
    
    
}
