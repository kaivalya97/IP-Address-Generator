import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

/*Dynamic IP Address Generator by Group 26: Aaditya Shah, Kaivalya Shah, Mohit Vachhani*/

class Node<E>	//Node class with Dynamic data type for linked list
{
	E data;	//Dynamic data
	Node<E> next;	//Next node in linked list
	public Node(E datain)	//Constructor with data as argument
	{
		data = datain;
		next = null;
	}
	
	public void setData(E datain)
	{
		data = datain;
	}
	
	public Node<E> getNext()	//Returns next node in linked list
	{
		return next;
	}
	
	public void setNext(Node<E> nodein)	//Sets next node in linked list
	{
		next = nodein;
	}
	
	public E getData()	//Returns data stored in node
	{
		return data;
	}
}