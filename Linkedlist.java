import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

/*Dynamic IP Address Generator by Group 26: Aaditya Shah, Kaivalya Shah, Mohit Vachhani*/

class Linkedlist<E>	//Linked list class with dynamic data type
{
	Node<E> start;	//First node in linked list
	Node<E> end;	//Last node in linked list
	int size;	//Size of linked list

	public Linkedlist()	//Default constructor with no arguments
	{
		start = null;
		end = null;
		size=0;
	}

	public void add(E datain)	//Add node in linked list
	{
		if(size==0)	//Case in which there is no node in linked list
		{
			start = new Node<E>(datain);
			end = start;
			size++;
			return;
		}
		Node<E> ptr = new Node<E>(datain);
		if(size==1)
		{
			start.setNext(ptr);
			end = ptr;
			size++;
			return;
		}
		size++;
		end.setNext(ptr);
		end = ptr;
		return;
	}
	
	public E getFirst()	//Returns data stored in first node of linked list
	{
		return start.getData();
	}
	
	public E getLast()	//Returns data stored in last node of linked list
	{
		return end.getData();
	}
	
	public void set(int pos,E data)
	{
		Node<E> nptr = start;
		for(int i=0;i<size;i++)
		{
			if(i==pos)
				{
					nptr.setData(data);
					return;
				}
			nptr = nptr.getNext();
		}
		start.setData(data);
		return;
	}
	
	public E get(int pos)	//Returns data stored at position in linked list
	{
		Node<E> nptr = start;
		for(int i=0;i<size;i++)
		{
			if(i==pos)
				return nptr.getData();
			nptr = nptr.getNext();
		}
		return start.getData();
	}
	
	public int search(E data)
	{
		Node<E> nptr = start;
		int i=1;
		if(size==0) return -1;
		while(nptr!=null)
			{
				if(nptr.getData()==data) return i;
				i++;
				nptr = nptr.getNext();
			}
		return -1;
	}
	
	public int getSize()	//Returns size of linked list
	{
		return size;
	}
}