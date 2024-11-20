/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;
import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author irfan
 */
public class Lab5Q1 {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students:");
        int n = scanner.nextInt();
        int[] score=new int[n];
        int[] changes = new int[c];
        int max =0;
        int min=101;
        int total=0;
        for(int i = 0;i<n;i++){
            
            score[i]= random.nextInt(101);
            total+=score[i];
            System.out.print(score[i]+" ");
            if(score[i]>max){
                max = score[i];
            }
            else if(score[i]<min){
                min = score[i];
            }
        }
        System.out.println("\nhighest score: "+max);
        System.out.println("lowest score: "+min);
        System.out.println("average: "+(total/n));
        
    }
}
