/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop2018.esercitazione20181126;

import java.util.*;

/**
 *
 * @author Gennaro
 */
public class StudentQueue implements Iterable<Student>{
    
    private final Student q[];
    private int elementsInside=0;
    private int head=0;
    private int tail=0;
    private long count=0;
    private boolean forward = true;

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        if(this.forward != forward){
            this.forward = forward;
            count++;
        }
    }
    
    public StudentQueue(int maxSize){
        q = new Student[maxSize];
    }
    
    
    public final int size(){
        return elementsInside;
    }
    
    public final boolean isEmpty(){
        return elementsInside == 0;
    }
    
    public final boolean isFull(){
        return elementsInside == q.length;
    }
    
    public boolean add(Student s){
        if(isFull())
            return false;
        q[tail] = s;
        tail = (tail+1) % q.length;
        count++; elementsInside++;
        return true;
    }
    
    public Student remove(){
        if(isEmpty())
            return null;
        Student s = q[head];
        q[head] = null;
        head = (head+1) % q.length;
        count++; elementsInside--;
        return s;
    }
    
    

    @Override
    public Iterator<Student> iterator() {
        return new StudentQueueIterator();
    }
    
    
    private class StudentQueueIterator implements Iterator<Student>{
        private int currentPosition;
        private int visited=0;
        private final int elementsInside;
        private final long initialCount;
                
        private StudentQueueIterator(){
            if(forward)
                currentPosition = head;
            else
                currentPosition = (tail-1+q.length) % q.length;
            elementsInside = size();
            initialCount = count;
        }
        
        @Override
        public boolean hasNext() {
            if(initialCount != count)
                throw new ConcurrentModificationException();
            return visited<elementsInside;
        }

        @Override
        public Student next() {
            if(initialCount != count)
                throw new ConcurrentModificationException();
            if(! hasNext())
                throw new NoSuchElementException();
            Student s = q[currentPosition];
            if(forward)
                currentPosition = (currentPosition+1) % q.length;
            else
                currentPosition = (currentPosition-1+q.length) % q.length;
            visited++;
            return s;
        }
        
        
    }
}
