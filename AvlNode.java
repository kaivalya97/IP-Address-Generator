import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

/*Dynamic IP Address Generator by Group 26: Aaditya Shah, Kaivalya Shah, Mohit Vachhani*/

class AvlNode
{
  public AvlNode left;
  public AvlNode right;
  public AvlNode parent;
  public int key;
  public String username;
  public int balance;
  
  public AvlNode(int k, String name)
  {
    left = right = parent = null;
    balance = 0;
    key = k;
	username = name;
  }
}